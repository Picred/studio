#include <iostream>
#include <fstream>
#include <cmath>
#define C 0.61803

using namespace std;

template<class T>class HashTable{
    private:
        int* table;
        int size;
    public:
        HashTable(int size){
            this->size=size;
            table = new int[size]{0};
        }
        int hash(T key){
            double pos = (double)fmod(C*key, 1);
            return (int)(pos*size);
        }
        void insert(T key){
            table[hash(key)]++;
        }

        void print(ofstream& out){
            for(int i=0; i<size; i++){
                out << table[i] << " ";
            }
            out << endl;
        }
};

template<class T> void solve(ifstream& in, ofstream& out, int n, int m){
    HashTable<T> table = HashTable<T>(m);

    for(int i=0; i<n; i++){
        T key;
        in >> key;
        table.insert(key);
    }

    table.print(out);
}


int main(){
    ifstream in("input.txt");
    ofstream out("output.txt");

    string type;
    int n, m;

    for(int i=0; i<100; i++){
        in >> type >> m >> n;

        if(type == "int" || type == "bool")
            solve<int>(in,out,n,m);
        if(type == "double")
            solve<double>(in,out,n,m);
        if(type == "char")
            solve<char>(in,out,n,m);
    }

}