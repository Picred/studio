/*
Method 1: Extends Thread

    - extends Thread class (see myThread)
    - implement run()
    - run start() 
*/

import java.util.Random;

public class Thread1 extends Thread
{
    Thread1()
    {
        System.out.println("Thread1 constructor");
    }

    public void run()
    {
        System.out.println("Thread1 start");
        Thread t = Thread.currentThread();
        System.out.println("Thread1 running => " + t.toString() + ": \n");
        System.out.println(t.getName());
        for(int i=1; i<=10; i++)
        {
            int rand = new Random().nextInt(10);
            try 
            {
                System.out.println("\t" + i + ") " + rand);
                sleep(rand);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        System.out.println("Thread1 status: " + t.getState());
    }

    public static void main(String[] args) 
    {
        Thread1 t = new Thread1();
        t.start();
    }
}
