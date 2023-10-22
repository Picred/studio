#include <stdio.h>
#include <stdlib.h>

#define CL_TARGET_OPENCL_VERSION 120
#include "ocl_boiler.h"

cl_event vecinit(cl_command_queue que, cl_kernel vecinit_k,
	int nels, cl_mem d_array)
{
	cl_event vecinit_evt;
	cl_int err;

	/* TODO chiamare kernel vecinit nels volte su *array */
	const size_t gws[] = { nels };

	err = clSetKernelArg(vecinit_k, 0, sizeof(d_array), &d_array);
	ocl_check(err, "set arg 0 to vecinit_k");

	err = clEnqueueNDRangeKernel(que, vecinit_k,
		1, NULL, gws, NULL,
		0, NULL, &vecinit_evt);
	ocl_check(err, "vecinit kernel enqueue failed\n");

	return vecinit_evt;
}

int verify(int nels, int const *array){
	for (int i = 0; i < nels; ++i) {
		if (array[i] != i) {
			fprintf(stderr, "%d: %d != %d\n",
				i, array[i], i);
			return 1;
		}
	}
	return 0;
}

int main(int argc, char* argv[]){
	if (argc != 2) {
		fprintf(stderr, "%s nels\n", argv[0]);
		exit(2);
	}

	int nels = atoi(argv[1]);
	int *h_array;
	int ret;

	if (nels <= 0) {
		fprintf(stderr, "%d <= 0\n", nels);
		exit(3);
	}

	const size_t memsize = sizeof(*h_array)*nels;

	h_array = malloc(memsize);
	if (!h_array) {
		fprintf(stderr, "malloc failed\n");
		exit(4);
	}

	/* Choose device */
	cl_platform_id p = select_platform();
	cl_device_id d = select_device(p);
	cl_context ctx = create_context(p, d);
	cl_command_queue que = create_queue(ctx, d);

	/* Compile device-side of the program */
	cl_program prog = create_program("vecinit.ocl", ctx, d);

	cl_int err;

	/* Memory management */
	cl_mem d_array = clCreateBuffer(ctx, CL_MEM_WRITE_ONLY, memsize, NULL, &err);
	ocl_check(err, "d_array creation failed\n");

	/* Get kernel(s) that we want to run */
	cl_kernel vecinit_k = clCreateKernel(prog, "vecinit", &err);
	ocl_check(err, "vecinit kernel creation failed\n");

	cl_event vecinit_evt = vecinit(que, vecinit_k, nels, d_array);

	cl_event read_evt;
	err = clEnqueueReadBuffer(que,
		d_array, CL_TRUE, 0, memsize, h_array,
		1, &vecinit_evt, &read_evt);

	double vecinit_ms = runtime_ms(vecinit_evt);
	double read_ms = runtime_ms(read_evt);

	printf("init: %.2g ms\n", vecinit_ms);
	printf("read: %.2g ms\n", read_ms);

	ret = verify(nels, h_array);

	clReleaseKernel(vecinit_k);
	clReleaseMemObject(d_array);
	clReleaseProgram(prog);
	clReleaseCommandQueue(que);
	clReleaseContext(ctx);

	free(h_array);

	return ret;
}
