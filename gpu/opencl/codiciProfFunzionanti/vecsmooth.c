#include <stdio.h>
#include <stdlib.h>

#define CL_TARGET_OPENCL_VERSION 120
#include "ocl_boiler.h"

cl_event vecinit(cl_command_queue que, cl_kernel vecinit_k,
	size_t vecinit_lws_multiple, int cli_lws,
	cl_int nels, cl_mem d_in)
{
	cl_event vecinit_evt;
	cl_int err;

	const size_t lws[] = { cli_lws ? cli_lws : vecinit_lws_multiple };
	const size_t gws[] = { round_mul_up(nels, lws[0]) };
	printf("%u | %zu = %zu\n", nels, lws[0], gws[0]);

	int arg = 0;
	err = clSetKernelArg(vecinit_k, arg, sizeof(d_in), &d_in);
	ocl_check(err, "set arg %d to vecinit_k", arg);
	++arg;
	err = clSetKernelArg(vecinit_k, arg, sizeof(nels), &nels);
	ocl_check(err, "set arg %d to vecinit_k", arg);

	err = clEnqueueNDRangeKernel(que, vecinit_k,
		1, NULL, gws, (cli_lws ? lws : NULL),
		0, NULL, &vecinit_evt);
	ocl_check(err, "vecinit kernel enqueue failed\n");

	return vecinit_evt;
}

cl_event vecsmooth(cl_command_queue que, cl_kernel vecsmooth_k,
	size_t vecsmooth_lws_multiple, int cli_lws,
	cl_int nels, cl_mem d_in, cl_mem d_out,
	cl_uint num_events_in_wait_list, const cl_event *event_wait_list)
{
	cl_event vecsmooth_evt;
	cl_int err;

	const size_t lws[] = { cli_lws ? cli_lws : vecsmooth_lws_multiple };
	const size_t gws[] = { round_mul_up(nels, lws[0]) };
	printf("%u | %zu = %zu\n", nels, lws[0], gws[0]);

	int arg = 0;
	err = clSetKernelArg(vecsmooth_k, arg, sizeof(d_in), &d_in);
	ocl_check(err, "set arg %d to vecsmooth_k", arg);
	++arg;
	err = clSetKernelArg(vecsmooth_k, arg, sizeof(d_out), &d_out);
	ocl_check(err, "set arg %d to vecsmooth_k", arg);
	++arg;
	err = clSetKernelArg(vecsmooth_k, arg, sizeof(nels), &nels);
	ocl_check(err, "set arg %d to vecsmooth_k", arg);

	err = clEnqueueNDRangeKernel(que, vecsmooth_k,
		1, NULL, gws, (cli_lws ? lws : NULL),
		num_events_in_wait_list, event_wait_list, &vecsmooth_evt);
	ocl_check(err, "vecsmooth kernel enqueue failed\n");

	return vecsmooth_evt;
}

int verify(int nels, int const *array)
{
	for (int i = 0; i < nels; ++i) {
		int expected = i;
		if (nels > 1 && i == nels -1)
			expected = i - 1;
		if (array[i] != expected) {
			fprintf(stderr, "%d: %d != %d\n",
				i, array[i], expected);
			return 1;
		}
	}
	return 0;
}

int main(int argc, char* argv[])
{
	if (argc != 2 && argc != 3) {
		fprintf(stderr, "%s nels [lws]\n", argv[0]);
		exit(2);
	}

	int nels = atoi(argv[1]);
	int cli_lws = argc == 3 ? atoi(argv[2]) : 0;
	int *h_array;
	int ret;

	if (nels <= 0) {
		fprintf(stderr, "%d <= 0\n", nels);
		exit(3);
	}
	if (cli_lws < 0) {
		fprintf(stderr, "%d < 0\n", cli_lws);
		exit(3);
	}

	const size_t memsize = sizeof(*h_array)*nels;

	/* Choose device */
	cl_platform_id p = select_platform();
	cl_device_id d = select_device(p);
	cl_context ctx = create_context(p, d);
	cl_command_queue que = create_queue(ctx, d);

	/* Compile device-side of the program */
	cl_program prog = create_program("vecsmooth.ocl", ctx, d);

	cl_int err;

	/* Memory management */
	cl_mem d_in = clCreateBuffer(ctx, CL_MEM_READ_WRITE | CL_MEM_HOST_NO_ACCESS,
		memsize, NULL, &err);
	ocl_check(err, "d_in creation failed\n");
	cl_mem d_out = clCreateBuffer(ctx, CL_MEM_WRITE_ONLY | CL_MEM_ALLOC_HOST_PTR,
		memsize, NULL, &err);
	ocl_check(err, "d_out creation failed\n");

	/* Get kernel(s) that we want to run */
	cl_kernel vecinit_k = clCreateKernel(prog, "vecinit", &err);
	ocl_check(err, "vecinit kernel creation failed\n");
	cl_kernel vecsmooth_k = clCreateKernel(prog, "vecsmooth", &err);
	ocl_check(err, "vecsmooth kernel creation failed\n");

	size_t vecinit_lws_multiple;
	err = clGetKernelWorkGroupInfo(vecinit_k, d, CL_KERNEL_PREFERRED_WORK_GROUP_SIZE_MULTIPLE,
		sizeof(vecinit_lws_multiple), &vecinit_lws_multiple, NULL);
	ocl_check(err, "vecinit preferred work-group size multiple");
	size_t vecsmooth_lws_multiple;
	err = clGetKernelWorkGroupInfo(vecsmooth_k, d, CL_KERNEL_PREFERRED_WORK_GROUP_SIZE_MULTIPLE,
		sizeof(vecsmooth_lws_multiple), &vecsmooth_lws_multiple, NULL);
	ocl_check(err, "vecsmooth preferred work-group size multiple");

	cl_event vecinit_evt = vecinit(que, vecinit_k, vecinit_lws_multiple,
		cli_lws, nels, d_in);

	cl_event vecsmooth_evt = vecsmooth(que, vecsmooth_k, vecsmooth_lws_multiple,
		cli_lws, nels, d_in, d_out, 1, &vecinit_evt);


	cl_event map_evt;

	h_array = clEnqueueMapBuffer(que, d_out, CL_TRUE,
		CL_MAP_READ,
		0, memsize,
		1, &vecsmooth_evt, &map_evt, &err);
	ocl_check(err, "map h_array\n");

	double vecinit_ms = runtime_ms(vecinit_evt);
	double vecsmooth_ms = runtime_ms(vecsmooth_evt);
	double map_ms = runtime_ms(map_evt);

	printf("init: %.2gms %.4gGB/s\n", vecinit_ms, memsize*1.0/runtime_ns(vecinit_evt));
	printf("smooth: %.2gms %.4gGB/s\n", vecsmooth_ms, 4*memsize*1.0/runtime_ns(vecsmooth_evt));
	printf("map: %.2gms %.4gGB/s\n", map_ms, memsize*1.0/runtime_ns(map_evt) );

	ret = verify(nels, h_array);

	cl_event unmap_evt;
	err = clEnqueueUnmapMemObject(que, d_out, h_array,
		0, NULL, &unmap_evt);

	ocl_check(err, "unmap h_array\n");

	err = clWaitForEvents(1, &unmap_evt);
	ocl_check(err, "unmap finish\n");

	double unmap_ms = runtime_ms(unmap_evt);
	printf("unmap: %.2gms\n", unmap_ms);

	clReleaseKernel(vecinit_k);
	clReleaseKernel(vecsmooth_k);
	clReleaseMemObject(d_out);
	clReleaseMemObject(d_in);
	clReleaseProgram(prog);
	clReleaseCommandQueue(que);
	clReleaseContext(ctx);

	return ret;
}
