#include <stdio.h>
#include <stdlib.h>

#define CL_TARGET_OPENCL_VERSION 120
#include "ocl_boiler.h"

cl_event vecinit(cl_command_queue que, cl_kernel vecinit_k,
	size_t vecinit_lws_multiple, int cli_lws,
	cl_int nels, cl_mem d_array)
{
	cl_event vecinit_evt;
	cl_int err;

	const size_t lws[] = { cli_lws ? cli_lws : vecinit_lws_multiple };
	const size_t gws[] = { round_mul_up(nels, lws[0]) };
	printf("%u | %zu = %zu\n", nels, lws[0], gws[0]);

	err = clSetKernelArg(vecinit_k, 0, sizeof(d_array), &d_array);
	ocl_check(err, "set arg 0 to vecinit_k");
	err = clSetKernelArg(vecinit_k, 1, sizeof(nels), &nels);
	ocl_check(err, "set arg 1 to vecinit_k");

	err = clEnqueueNDRangeKernel(que, vecinit_k,
		1, NULL, gws, (cli_lws ? lws : NULL),
		0, NULL, &vecinit_evt);
	ocl_check(err, "vecinit kernel enqueue failed\n");

	return vecinit_evt;
}

int verify(int nels, int const *array)
{
	for (int i = 0; i < nels; ++i) {
		if (array[i] != i) {
			fprintf(stderr, "%d: %d != %d\n",
				i, array[i], i);
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
	cl_program prog = create_program("vecinit_align.ocl", ctx, d);

	cl_int err;

	/* Memory management */
	cl_mem d_array = clCreateBuffer(ctx, CL_MEM_WRITE_ONLY | CL_MEM_ALLOC_HOST_PTR,
		memsize, NULL, &err);
	ocl_check(err, "d_array creation failed\n");

	/* Get kernel(s) that we want to run */
	cl_kernel vecinit_k = clCreateKernel(prog, "vecinit", &err);
	ocl_check(err, "vecinit kernel creation failed\n");

	size_t vecinit_lws_multiple;
	err = clGetKernelWorkGroupInfo(vecinit_k, d, CL_KERNEL_PREFERRED_WORK_GROUP_SIZE_MULTIPLE,
		sizeof(vecinit_lws_multiple), &vecinit_lws_multiple, NULL);
	ocl_check(err, "vecinit preferred work-group size multiple");

	cl_event vecinit_evt = vecinit(que, vecinit_k, vecinit_lws_multiple,
		cli_lws, nels, d_array);

	cl_event map_evt;

	// Flag                           ~ map        + unmap
	// CL_MAP_READ                    ~ ReadBuffer + (no)
	// CL_MAP_WRITE                   ~ ReadBuffer + WriteBuffer
	// CL_MAP_WRITE_INVALIDATE_REGION ~ (no)       + WriteBuffer
	h_array = clEnqueueMapBuffer(que, d_array, CL_TRUE,
		CL_MAP_READ,
		0, memsize,
		1, &vecinit_evt, &map_evt, &err);
	ocl_check(err, "map h_array\n");

	double vecinit_ms = runtime_ms(vecinit_evt);
	double map_ms = runtime_ms(map_evt);

	printf("init: %.2gms %.4gGB/s\n", vecinit_ms, memsize*1.0/runtime_ns(vecinit_evt));
	printf("map: %.2gms %.4gGB/s\n", map_ms, memsize*1.0/runtime_ns(map_evt) );

	ret = verify(nels, h_array);

	cl_event unmap_evt;
	err = clEnqueueUnmapMemObject(que, d_array, h_array,
		0, NULL, &unmap_evt);

	ocl_check(err, "unmap h_array\n");

	err = clWaitForEvents(1, &unmap_evt);
	ocl_check(err, "unmap finish\n");

	double unmap_ms = runtime_ms(unmap_evt);
	printf("unmap: %.2gms\n", unmap_ms);

	clReleaseKernel(vecinit_k);
	clReleaseMemObject(d_array);
	clReleaseProgram(prog);
	clReleaseCommandQueue(que);
	clReleaseContext(ctx);

	return ret;
}
