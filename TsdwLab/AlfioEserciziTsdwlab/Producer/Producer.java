/*  Thread Java

    Scrivere in Java un programma con due thread produttori P1 e P2 che
    condividono una variabile m intera, che va inizializzata con un numero
    casuale compreso tra 1 e 10.
    I thread eseguono un ciclo infinito, comportandosi rispettivamente come
    segue:

    P1 controlla il valore M di m:

    ● se M è compreso tra 1 e 5, P1 sveglia P2, genera un numero
    casuale compreso tra 1 e 10, lo memorizza in M e lo stampa a video

    ● se invece M è compreso tra 6 e 10, P1 si mette in attesa
    
    
    P2 controlla il valore di M:

    ● se M è compreso tra 6 e 10, P2 sveglia P1, genera un numero
    casuale compreso tra 1 e 10, lo memorizza in M e lo stampa a video

    ● se M è compreso tra 1 e 5, P2 si mette in attesa
    Tempo per la prova: 30 minuti 
*/



import java.util.Random;

public class Producer {
    
    private static int m = new Random().nextInt(10) + 1; 

    static Object lock = new Object();

    static Thread p1 = new Thread(() -> {
        while (true) {
            synchronized (lock) {
                try {
                    if (m >= 1 && m <= 5) {
                        m = new Random().nextInt(10) + 1;
                        System.out.println("P1 generated and set M to: " + m);
                        lock.notify(); // Sveglia P2
                    } else {
                        lock.wait(); // Mette P1 in attesa
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    static Thread p2 = new Thread(() -> {
        while (true) {
            synchronized (lock) {
                try {
                    if (m >= 6 && m <= 10) {
                        m = new Random().nextInt(10) + 1;
                        System.out.println("P2 generated and set M to: " + m);
                        lock.notify(); // Sveglia P1
                    } else {
                        lock.wait(); // Mette P2 in attesa
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    
    public static void main(String[] args) throws Exception{

        p1.start();
        p2.start();

        p1.join();
        p2.join();

        System.out.println("\nFinish");
    }
}
