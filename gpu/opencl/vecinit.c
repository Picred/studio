/*vecinit.c*/
#include <stdio.h>
#include <stdlib.h>
#include "ocl_boiler.h"
#define CL_TARGET_OPENCL_VERSION 120 //versione di opencl si vuole usare per capire le API disponibili

void vecinit_k(int i, int *array){ //parte device
    array[i] = i;
}

cl_event vecinit(cl_command_que que, cl_kernel vecinit_k, int nels, cl_mem array){
	cl_event vecinit_evt;
    cl_int err;
    err = clSetKernelArg(vecinit_k, 0, sizeof(d_array), &d_array);
    ocl_check(err, "set arg 0 to vecinit_k\n");

    // chiamare kernelvecinit nel volte su *array
    const size_t gws[] = {nels}; //dimensione work group, global work size
    cl_int err = clEnqueueNDRangeKernel(que, vecinit_k, 1, NULL, gws, NULL, 0, NULL, &vecinit_k);
    // i parametri "1, NULL, gws, NULL " specificano la griglia di lancio
    // err rappresenta l'eventuale errore dell'accodamento del kernel
    ocl_check(err, "vecinit kernel enqueue failed\n");

    return vecinit_evt;

}

int verify(int nels, int const*array){
	for(int i=0; i<nels; ++i){
		if(array[i] != i){
			fprintf(stderr, "%d : %d != %d\n", i, array[i], i);
			return 1;
		}
    }
    return 0;
}

int main(int argc, char*argv[]){	
    if(argc != 2){
        fprintf(stderr, "%s nels\n", argv[0]);
        exit(2);
    }

    int nels = atoi(argv[1]);
    int* h_array; //host array

    if(nels <= 0){
        fprintf(stderr, "%d <=0\n", nels);
        exit(3);
    }

    const size_t memsize = sizeof(*h_array*nels);
    h_array = malloc(memsize); //servirà per fare la verifica.
    // il kernel sarà lanciato su device che avrà la sua ram. Si dovrà fare
    
    if(!h_array){
        fprintf(stderr, "malloc failed\n");
        exit(4);
    }

    // TODO: scegliere il device
    // TODO: compilare il codice device per il device scelto
    // i primi 2 todo sono laboriosi e difficili da scrivere, per cui usiamo un file già scritto ocl_boiler.h
    // dà delle funzioni per semplificare il lavoro con delle funzioni che esistono gia

    // selezione del device
    cl_platform_id p = select_platform(); // la piattaforma è: clinfo -l. 0 di default (la cpu) e 1 per il device
    cl_device_id d = select_device(p);
    cl_context ctx = create_context(p,d);
    cl_command_queue que = create_queue(ctx,d);
    // compilare la parte device del programma
    cl_program prog = create_program("vecinit.ocl", ctl, d); //codice device + contesto + device d

    // TODO: allocare la RAM su device (d_array), array su device
    // non esiste l'equivalente di un malloc(). Si crea un buffer nel ctx e si può spostare da device a device 
    // chiamate che creano oggetti 
    cl_int err;
    // memory management
    cl_mem d_array = clCreateBuffer(ctx,CL_MEM_WRITE_ONLY,memsize, NULL, &err); // il flag serve per indicare in che modo si vuole usare il buffer. ..READ_WRITE, ..READ_ONLY, ..WRITE_ONLY ecc. 

    //check d_array
    ocl_check(err, "d_array creation failed\n");

    // forniamo a host qualcosa per poter controllare il kernel (device)
    // get kernel that we want to run
    cl_kernel vecinit_k = clCreateKernel(prog,"vecinit.ocl", &err); // "alloca" su memoria globale
    ocl_check(err, "vecinit kernel creation failed\n");

    // TODO: lanciare il kernel vecinit su d_array
    cl_event vecinit_evt = vecinit(que, vecinit_k,nels,h_array);

    cl_event wait_list[] { vecinit_evt}; //se avessimo piu eventi da aspettare e andrebbe sostituito in *vecinit_evt nella fuznione
    cl_event read_evt;
    // TODO: serve, alla fine, copiare i dati da d_array a h_array
    err =  clEnqueueReadBuffer(que,d_array, CL_TRUE, 0, memsize, h_array, 1, *vecinit_evt, &read_evt); //da buffer opencl a un puntatore host. Legge contenuti buffer in un array allocato in how. err è un errore sull'accodamento. se il comando va bene, allora l'accodamento è andato bene ma non vuol dire che la copia sia effettivamente avvenuta.
    // la funzione non ritorna finche copia non è finita visto che ho CL_TRUE ed è bloccante. Alla fine della funzione gli elementi sono copiati su h_array.
    // se avessi invece CL_FALSE, allora si tratta di copia asincrona, quindi posso fare altre cose che non coinvolgono h_array, e prima di usare h_array dovrei sincronizzarmi. ma si vedrà dopo
    // clEnqueueWriteBuffer //scrive su un buffer da un puntatore a host, quindi da host.
    
    //altri parametri nella funzione sopra soon: waiting list per sincronizzare i comandi fra loro. essa è un parametro di 'man clEnqueueReadBuffer'
    // se il kernel non finisce, è inutile scaricare i dati, quindi un evento 


    // ogni evento ha associata anche l'info sui tempi di esecuzione in ocl_boiler.h
    // quando si crea la coda si possono specificare se si vogliono i tempi CL_QUEUE_PROFILING_ENALBLE, oppure creare piu code e mandare comandi diversi su code diverse 

    int ret = verify(nels,h_array);
    
    double vecinict_ms = runtime_ms(vecinit_evt);
    double read_ms = runtime_ms(read_evt);
    printf("init: %.2g ms\n", vecinit_ms);
    printf("read: %.2g ms\n", read_ms);

    //TODO: Liberare tutte le risorse usate su device
    clReleaseKernel(vecinit_k);
    clReleaseMemObject(d_array);
    clReleaseProgram(prog);
    clReleaseCommandQueue(que);
    clReleaseContext(ctx);


    free(h_array);

    return ret;
}