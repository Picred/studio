/*
Method 3: Lambda function (Java21)

    - Create a Thread using a lambda function
    - run start()
*/

import java.util.Random;

public class Thread3 
{
    public static void main(String[] args) 
    {
        Thread t = new Thread(() -> 
        {
            System.out.println("Thread3 start");
            Thread currentThread = Thread.currentThread(); 
            System.out.println("Thread3 running => " + currentThread.toString() + ": \n");
            System.out.println(currentThread.getName());
            for (int i=1; i<=10; i++) 
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
            System.out.println("Thread3 status: " + currentThread.getState());
        });
        
        t.start(); 
    }
}
