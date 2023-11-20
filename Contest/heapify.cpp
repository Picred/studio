#include <iostream>
#include <fstream>

using namespace std;

template<class T> class MaxHeap{
    private:
        T* heap;
        int heapsize;
        int fycalls;

        int left(int i) { return i*2;}
        int right(int i) { return (i*2)+1;}
        int parent(int i) { return i/2;}

        void heapify(int i){
            heapsize > 0 ? fycalls++ : fycalls += 0;
            int max = i;
            int l = left(i);
            int r = right(i);

            if(l<= heapsize && heap[l] > heap[max])
                max = l;
            if(r<= heapsize && heap[r] > heap[max])
                max = r;
            
            if(max != i){
                swap(heap[i], heap[max]);
                heapify(max);
            }
        }
    public:
        MaxHeap(int hsize, T* vett){
            heapsize = hsize;
            heap = vett;
            fycalls = 0;
        }

        void build(){
            for(int i=heapsize/2; i>0; i--)
                heapify(i);
        }

        void extract(){
            swap(heap[1], heap[heapsize]);
            heapsize--;
            heapify(1);
        }

        void extractAll(int size){
            for(int i=0; i<size; i++)
                extract();
        }

        void print(ofstream &out){
            out << fycalls << endl;
        }
};

template<class T> void solve(ifstream &in, ofstream &out, int n){
    T* vett = new T[n+1];

    for(int i=1; i<=n; i++)
        in >> vett[i];

    MaxHeap<T> heap = MaxHeap(n, vett);
    heap.build();
    heap.extractAll(n);
    heap.print(out);
}

int main(){
    ifstream in("input.txt");
    ofstream out("output.txt");

    string type;
    int n;

    for(int i=0; i<100; i++){
        in >> type >> n;

        if(type == "int" || type == "bool")
            solve<int>(in,out,n);
        if(type == "double")
            solve<double>(in,out,n);
        if(type == "char")
            solve<char>(in,out,n);
    }
}