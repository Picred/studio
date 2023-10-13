/*vecinit.c*/
#include <stdio.h>
#include <stdlib.h>

void vecinit_k(int i, int *array){
    array[i] = i;
}

void vecinit(int nels, int* array){
	for(int i=0; i<nels; ++i){ // il for diventa la sintassi per lanciare un kernel in opencl e cuda
		vecinit_k(i, array);
        //array[i] = i; //questo pezzo rappresenta il kernel
	}
}

/* eventuali
void verify_k(int i, int const *array, int*ret){
    if(array[i] != i)
        *ret = 1; // con questo posso capire se c'è stato almeno un errore, quindi posso controllare tutti 
    // gli elemento e cerco l'errore.
}

int verify2(int nels, int const *array){
    int ret = 0; //se ho 0 allora non ci sono errori, nessuno in parallelo scrive
    for(int i=0; iznels; ++i){
        verify_k(i, array, &ret);
    }
    return ret;
}


void verify(int nels, int const*array, int* ret){
	for(int i=0; i<nels; ++i){
		if(array[i] != i){
			ret;
		}
    }
    return 0;
}
*/

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
    int* array;

    if(nels <= 0){
        fprintf(stderr, "%d <=0\n", nels);
        exit(3);
    }

    array = malloc(sizeof(*array)*nels);
    if(!array){
        fprintf(stderr, "malloc failed\n");
        exit(4);
    }

    vecinit(nels,array);
    int ret = verify(nels,array);
    
    free(array);

    return ret;
}

//./vecinit $((16*1024*1024))