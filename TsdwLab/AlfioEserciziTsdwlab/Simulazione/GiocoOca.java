import java.util.Random;

public class GiocoOca {

    static volatile int turno = 0; // shared variable

    static volatile int winner = -1;

    static Object lock = new Object();
    
    static Thread t0 = new Thread(()->{
        try {
            int posizione = 0;
            final int id = 0;
            
            System.out.println("[T"+id+"]posizione: "+posizione);
            while(true) {

                Thread.sleep(500);

                if (turno == -1)
                    break;

                if (turno == id) {
                    int d = launch();
                    posizione += d;
                    System.out.println("\n[T"+id+"]lancio: "+d+", posizione: "+posizione);
                    
                    if (posizione >= 100) {
                        System.out.println("\n[T"+id+"]100 superato!");
                        synchronized(lock) {
                            winner = id;
                            turno = -1;
                            lock.notify();
                        }
                    } else {
                        synchronized(lock) {
                            turno = 1-id;
                            lock.notify();
                        }
                    }
                }

                else {
                    synchronized(lock) {
                        lock.wait();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    static Thread t1 = new Thread(()->{
        try {
            int posizione = 0;
            final int id = 1;
            
            System.out.println("[T"+id+"]posizione: "+posizione);
            while(true) {

                Thread.sleep(500);
                
                if (turno == -1)
                    break;

                if (turno == id) {
                    int d = launch();
                    posizione += d;
                    System.out.println("\n[T"+id+"]lancio: "+d+", posizione: "+posizione);
                    
                    if (posizione >= 100) {
                        System.out.println("\n[T"+id+"]100 superato!");
                        synchronized(lock) {
                            winner = id;
                            turno = -1;
                            lock.notify();
                        }
                    } else {
                        synchronized(lock) {
                            turno = 1-id;
                            lock.notify();
                        }
                    }
                }

                else {
                    synchronized(lock) {
                        lock.wait();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    static int launch() { return new Random().nextInt(6) + 1;}

    public static void main(String[] args) throws Exception {
        
        t0.start();
        t1.start();

        t0.join();
        t1.join();

        System.out.println("\nGioco dell'Oca terminato: vince t"+winner+"!!!");
    }
}
