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
	cl_mem d_array = clCreateBuffer(ctx, CL_MEM_WRITE_ONLY,
		memsize, NULL, &err);
	ocl_check(err, "d_array creation failed\n");

	/* Get kernel(s) that we want to run */
	cl_kernel vecinit_k = clCreateKernel(prog, "vecinit", &err);
	ocl_check(err, "vecinit kernel creation failed\n");

	cl_event vecinit_evt = vecinit(que, vecinit_k, nels, d_array);

	cl_event map_evt;

    h_array = clEnqueueMapBuffer(que, d_array, CL_TRUE, CL_MAP_READ, 0, memsize, 1, &vecinit_evt, &map_evt, &err); //rendere accessibile il buffer o almeno una regione all'host. Ritorna il ptr attraverso cui l'host può accedere al buffer.
    // ocl_check(err, "map failed");
    // CL_MAP_READ significa che la map serve per leggere il contenuto del buffer. 
    // CL_MAP_WRITE_INVAlIDATE_REGION significa che il contenuto del buffer non è più valido.

	double vecinit_ms = runtime_ms(vecinit_evt);
	double map_ms = runtime_ms(map_evt);

	printf("init: %.2g ms\n", vecinit_ms);
	printf("map: %.2g ms\n", map_ms);

    cl_event unmap_evt;
    err = clEnqueueUnmapMemObject(que, d_array, h_array, 0, NULL, &unmap_evt); // rende il buffer non accessibile all'host. Qui avviene anche la sincronizzazione
    
    ocl_check(err, "unmap h_array"); //accodamento fallito?

    // qui bisogna aspettare che la unmap sia conclusa prima di prendere il tempo di esecuzione dell'evento. Bisogna aspettare che l'evento sia concluso
    
    clFlush(que); // forza l'esecuzione di tutti i comandi accodati nella coda. Aspetta che tutti i comandi siano stati mandati al device. Quindi aspetta che il device INIZI l'ultimo comando
    // err = clFinish(que); //aspetta che tutti i comandi accodati siano conclusi (anche senza successo, quindi con un errore)
    // err = clWaitForEvents(1, &unmap_evt);
    ocl_check(err, "unmap finish"); //se qualche comando fallisce, lo scopriamo qua
    
    ocl_check(err, "queue finish");
    
     
    double unmap_ms = runtime_ms(unmap_evt);
    printf("unmap: %.2g ms\n", unmap_ms);

	// ret = verify(nels, h_array);
	clReleaseKernel(vecinit_k);
	clReleaseMemObject(d_array);
	clReleaseProgram(prog);
	clReleaseCommandQueue(que);
	clReleaseContext(ctx);

	// free(h_array); non si fa piu perche non abbiamo allocato noi ma bisogna fare l'operazione inversa alla map


	return ret;
}