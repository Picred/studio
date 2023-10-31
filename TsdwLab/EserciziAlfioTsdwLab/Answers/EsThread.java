import java.util.Random;

public class EsThread 
{
    static Random rand = new Random();
    static volatile int x = rand.nextInt(11);
    static Object lock = new Object();

    // Thread n1
    static Thread t1 = new Thread(() -> 
    {
        while (true) 
        {
            int m = 0;
            try 
            {
                Thread.sleep(100);
                m = rand.nextInt(11);

                if (x == -1)
                    break;

                if (m == x) 
                {
                    System.out.println("Risposta corretta (m: " + m + ", x: " + x + ")");
                    x = -1;
                    break;
                }

                if (Math.abs(m - x) > 5) 
                {
                    System.out.println("Risposta molto sbagliata (m: " + m + ", x: " + x + ")");
                    synchronized (lock) 
                    {
                        try 
                        {
                            lock.wait();
                        } catch (InterruptedException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                } 
                else 
                {
                    System.out.println("Risposta sbagliata (m: " + m + ", x: " + x + ")");
                }
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    });

    // Thread n2
    static Thread t2 = new Thread(() -> 
    {
        while (true) 
        {
            try 
            {
                Thread.sleep(300);
                synchronized (lock) 
                {
                    lock.notify();
                }
                if (x == -1)
                    break;
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    });

    public static void main(String[] args) throws Exception
    {
        //Start
        t1.start();
        t2.start();

        //Join
        t1.join();
        t2.join();  
    }
}
