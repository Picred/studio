#include <iostream>
#include <fstream>
#include <climits>

using namespace std;

void solve(ifstream& in, ofstream& out, int n){
    int *p = new int[n+1]{0};
    char tmp;
    int dim1, dim2;

    for(int i=0; i<n; i++){
        in >> tmp >> dim1 >> tmp >> dim2 >> tmp;
        p[i] = dim1;
    }
    p[n] = dim2;

    n+=1;
    int m[n][n];

    for(int i=1; i<n; i++)
        m[i][i] = 0;
    
    for(int x=2; x<n; x++)
        for(int i=1; i<n-x+1; i++){
            int j = i+x-1;
            m[i][j] = INT_MAX;
            for(int k = i; k<j; k++){
                int q = m[i][k] + m[k+1][j] + (p[i-1] * p[k] * p[j]);
                if(q < m[i][j])
                    m[i][j] = q;
            }
        }

        out << m[1][n-1] << endl;
}

int main(){
    ifstream in("input.txt");
    ofstream out("output.txt");
    int n;
    for(int i=0; i<100; i++){
        in >> n;
        solve(in,out,n);
    }
}