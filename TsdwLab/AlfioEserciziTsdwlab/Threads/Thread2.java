/*
Method 2: Implement Runnable

    - Implements a Runnable class (including run())
    - Create a Thread using implemented runnable
    - run start()
*/

import java.util.Random;

public class Thread2 implements Runnable
{
    Thread2()
    {
        System.out.println("Thread2 constructor");
    }

    @Override
    public void run() 
    {
        System.out.println("Thread2 start");
        Thread t = Thread.currentThread();
        System.out.println("Thread2 running => " + t.toString() + ": \n");
        System.out.println(t.getName());
        for(int i=1; i<=10; i++)
        {
            int rand = new Random().nextInt(10);
            try 
            {
                System.out.println("\t" + i + ") " + rand);
                Thread.sleep(rand);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        System.out.println("Thread2 status: " + t.getState());
    }

    public static void main(String[] args) 
    {
        Thread2 t2 = new Thread2();
        t2.run();   
    }   
}
