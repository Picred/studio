#include <stdio.h>
#include <stdlib.h>

#define CL_TARGET_OPENCL_VERSION 120
#include "ocl_boiler.h"

cl_event vecinit(cl_command_queue que, cl_kernel vecinit_k,
	size_t vecinit_lws_multiple, int cli_lws,
	cl_int nels, cl_mem d_v1, cl_mem d_v2)
{
	cl_event vecinit_evt;
	cl_int err;

	const size_t lws[] = { cli_lws ? cli_lws : vecinit_lws_multiple };
	const size_t gws[] = { round_mul_up(nels, lws[0]) };
	printf("%u | %zu = %zu\n", nels, lws[0], gws[0]);

	int arg = 0;
	err = clSetKernelArg(vecinit_k, arg, sizeof(d_v1), &d_v1);
	ocl_check(err, "set arg %d to vecinit_k", arg);
	++arg;
	err = clSetKernelArg(vecinit_k, arg, sizeof(d_v2), &d_v2);
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

cl_event vecsum(cl_command_queue que, cl_kernel vecsum_k,
	size_t vecsum_lws_multiple, int cli_lws,
	cl_int nels, cl_int vec, cl_mem d_v1, cl_mem d_v2, cl_mem d_v3,
	cl_uint num_events_in_wait_list, const cl_event *event_wait_list)
{
	cl_event vecsum_evt;
	cl_int err;

	cl_int nwi = nels/vec;

	const size_t lws[] = { cli_lws ? cli_lws : vecsum_lws_multiple };
	const size_t gws[] = { round_mul_up(nwi, lws[0]) };
	printf("%u | %zu = %zu\n", nels, lws[0], gws[0]);

	int arg = 0;
	err = clSetKernelArg(vecsum_k, arg, sizeof(d_v1), &d_v1);
	ocl_check(err, "set arg %d to vecsum_k", arg);
	++arg;
	err = clSetKernelArg(vecsum_k, arg, sizeof(d_v2), &d_v2);
	ocl_check(err, "set arg %d to vecsum_k", arg);
	++arg;
	err = clSetKernelArg(vecsum_k, arg, sizeof(d_v3), &d_v3);
	ocl_check(err, "set arg %d to vecsum_k", arg);
	++arg;
	err = clSetKernelArg(vecsum_k, arg, sizeof(nwi), &nwi);
	ocl_check(err, "set arg %d to vecsum_k", arg);

	err = clEnqueueNDRangeKernel(que, vecsum_k,
		1, NULL, gws, (cli_lws ? lws : NULL),
		num_events_in_wait_list, event_wait_list, &vecsum_evt);
	ocl_check(err, "vecsum kernel enqueue failed\n");

	return vecsum_evt;
}

int verify(int nels, int const *array)
{
	for (int i = 0; i < nels; ++i) {
		if (array[i] != nels) {
			fprintf(stderr, "%d: %d != %d\n",
				i, array[i], nels);
			return 1;
		}
	}
	return 0;
}

int main(int argc, char* argv[])
{
	if (argc != 2 && argc != 3 && argc != 4) {
		fprintf(stderr, "%s nels [vec [lws]]\n", argv[0]);
		exit(2);
	}

	int nels = atoi(argv[1]);
	int vec = argc == 3 ? atoi(argv[2]) : 1;
	int cli_lws = argc == 4 ? atoi(argv[3]) : 0;
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
	if (vec != 1 && vec != 2 && vec != 4 && vec != 8 && vec != 16) {
		fprintf(stderr, "vec %d not in {1, 2, 4, 8, 16}\n", vec);
		exit(3);
	}
	// vec is a power of two, so divisibility can be check this way:
	// Es:
	// vec     -> 8 (0b1000)
	// vec-1   -> 7 (0b0111)
	// nels multiple of 8 if last three bits are 0
	if (vec > 1 && (nels & (vec-1))) {
		fprintf(stderr, "vec must divide nels\n");
		exit(3);
	}

	const size_t memsize = sizeof(*h_array)*nels;

	/* Choose device */
	cl_platform_id p = select_platform();
	cl_device_id d = select_device(p);
	cl_context ctx = create_context(p, d);
	cl_command_queue que = create_queue(ctx, d);

	/* Compile device-side of the program */
	cl_program prog = create_program("vecsum.ocl", ctx, d);

	cl_int err;

	/* Memory management */
	cl_mem d_v1 = clCreateBuffer(ctx, CL_MEM_READ_WRITE | CL_MEM_HOST_NO_ACCESS,
		memsize, NULL, &err);
	ocl_check(err, "d_v1 creation failed\n");
	cl_mem d_v2 = clCreateBuffer(ctx, CL_MEM_READ_WRITE | CL_MEM_HOST_NO_ACCESS,
		memsize, NULL, &err);
	ocl_check(err, "d_v2 creation failed\n");
	cl_mem d_v3 = clCreateBuffer(ctx, CL_MEM_WRITE_ONLY | CL_MEM_ALLOC_HOST_PTR,
		memsize, NULL, &err);
	ocl_check(err, "d_v3 creation failed\n");

	/* Get kernel(s) that we want to run */
	cl_kernel vecinit_k = clCreateKernel(prog, "vecinit", &err);
	ocl_check(err, "vecinit kernel creation failed\n");

	char vecsum_k_name[11] = {0};
	snprintf(vecsum_k_name, 11, "vecsum_v%d", vec);

	printf("vecsum kernel: %s\n", vecsum_k_name);

	cl_kernel vecsum_k = clCreateKernel(prog, vecsum_k_name, &err);
	ocl_check(err, "vecsum kernel creation failed\n");

	size_t vecinit_lws_multiple;
	err = clGetKernelWorkGroupInfo(vecinit_k, d, CL_KERNEL_PREFERRED_WORK_GROUP_SIZE_MULTIPLE,
		sizeof(vecinit_lws_multiple), &vecinit_lws_multiple, NULL);
	ocl_check(err, "vecinit preferred work-group size multiple");
	size_t vecsum_lws_multiple;
	err = clGetKernelWorkGroupInfo(vecsum_k, d, CL_KERNEL_PREFERRED_WORK_GROUP_SIZE_MULTIPLE,
		sizeof(vecsum_lws_multiple), &vecsum_lws_multiple, NULL);
	ocl_check(err, "vecsum preferred work-group size multiple");

	cl_event vecinit_evt = vecinit(que, vecinit_k, vecinit_lws_multiple,
		cli_lws, nels, d_v1, d_v2);

	cl_event vecsum_evt = vecsum(que, vecsum_k, vecsum_lws_multiple,
		cli_lws, nels, vec, d_v1, d_v2, d_v3, 1, &vecinit_evt);


	cl_event map_evt;

	h_array = clEnqueueMapBuffer(que, d_v3, CL_TRUE,
		CL_MAP_READ,
		0, memsize,
		1, &vecsum_evt, &map_evt, &err);
	ocl_check(err, "map h_array\n");

	double vecinit_ms = runtime_ms(vecinit_evt);
	double vecsum_ms = runtime_ms(vecsum_evt);
	double map_ms = runtime_ms(map_evt);

	printf("init: %.2gms %.4gGB/s\n", vecinit_ms, 2*memsize*1.0/runtime_ns(vecinit_evt));
	printf("sum: %.2gms %.4gGB/s\n", vecsum_ms, 3*memsize*1.0/runtime_ns(vecsum_evt));
	printf("map: %.2gms %.4gGB/s\n", map_ms, memsize*1.0/runtime_ns(map_evt) );

	ret = verify(nels, h_array);

	cl_event unmap_evt;
	err = clEnqueueUnmapMemObject(que, d_v3, h_array,
		0, NULL, &unmap_evt);

	ocl_check(err, "unmap h_array\n");

	err = clWaitForEvents(1, &unmap_evt);
	ocl_check(err, "unmap finish\n");

	double unmap_ms = runtime_ms(unmap_evt);
	printf("unmap: %.2gms\n", unmap_ms);

	clReleaseKernel(vecinit_k);
	clReleaseKernel(vecsum_k);
	clReleaseMemObject(d_v3);
	clReleaseMemObject(d_v2);
	clReleaseMemObject(d_v1);
	clReleaseProgram(prog);
	clReleaseCommandQueue(que);
	clReleaseContext(ctx);

	return ret;
}
