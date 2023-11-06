public class Main{
    public static void main(String[] args) {
        Shared shared = new Shared();
        Thread t1 = new MyThread(1, shared);
        Thread t2 = new MyThread(2, shared);
        Thread t3 = new MyThread(3, shared);

        t1.start();
        t2.start();
        t3.start();
        // check output, mai runnato
    }
}