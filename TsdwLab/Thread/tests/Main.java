import java.lang.Thread;
import java.util.*;


public class Main{
    public static void main(String[] args) throws InterruptedException {
        Shared shared = new Shared();

        Test t1 = new Test(0, shared);
        Test t2 = new Test(1, shared);
        t1.run();
        t2.run();
    }
    
}