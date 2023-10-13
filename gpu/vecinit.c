/*vecinit.c*/
#include <stdio.h>
#include <stdlib.h>

void vecinit(int nels, int* array){
	for(int i=0; i<nels; ++i){
		array[i] = i;
	}
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