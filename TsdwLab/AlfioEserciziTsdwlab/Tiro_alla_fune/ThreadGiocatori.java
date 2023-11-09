import java.util.Random;

class ThreadGiocatori extends Thread 
{
    int id;
    static volatile int posizione = 0;
    static int vittorie_tp0 = 0, vittorie_tp1 = 0;
    static Object lock = new Object();

    ThreadGiocatori(int _id) 
    {
        this.id = _id;
        System.out.println("Thread " + id + " created\n");
    }

    @Override
    public void run() 
    {
        while (true) 
        {
            if (vittorie_tp0 == 10 || vittorie_tp1 == 10)
                break;

            int recupero = new Random().nextInt(4); // Intervallo da 0 a 3
            int forza = new Random().nextInt(6);    // Intervallo da 0 a 5

            try 
            {
                Thread.sleep(recupero * 100);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }

            synchronized (lock) 
            {
                if (id == 0) 
                {
                    posizione -= forza;
                    if (posizione <= -10) 
                    {
                        System.out.println("[tp" + id + "] Ha vinto! (tp0: " + vittorie_tp0 + ", tp1: " + vittorie_tp1 + ")");
                        vittorie_tp0++;
                        posizione = 0;
                        lock.notify();
                    }
                } 
                else if (id == 1) 
                {
                    posizione += forza;
                    if (posizione >= 10) 
                    {
                        System.out.println("[tp" + id + "] Ha vinto! (tp0: " + vittorie_tp0 + ", tp1: " + vittorie_tp1 + ")");
                        vittorie_tp1++;
                        posizione = 0;
                        lock.notify();
                    }
                }
            }
        }
    }

}