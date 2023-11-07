
/* 
    tO = ThreadOdd (dispari)
    tE = ThreadEven (pari)
*/
public class Main{
    public static void main(String[] args) {
        Shared shared = new Shared();
        Thread tO = new MyThread("tO", shared);
        Thread tE = new MyThread("tE", shared);
        
    }
}