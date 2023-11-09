/* Scrivere un programma in C che simuli un incontro di “tiro alla fune” tra due 2 thread “giocatori” tp[0], tp[1].

    E’ data una variabile globale intera posizione (con valore iniziale 0) condivisa da tutti i thread. 
    Sono date inoltre due variabili globali intere, vittorie_tp0 e vittorie_tp1.

    Ogni thread giocatore esegue un ciclo in cui:
    - genera un intero casuale recupero compreso tra 0 e 3
    - genera un intero casuale forza compreso tra 0 e 5
    - attende recupero secondi 

    - se tp[0]:
        - se posizione >= 10 riconosce la vittoria di tp[1] e: 
            - incrementa vittorie_tp1
            - setta posizione = 0
            - sveglia tp[1]
        -altrimenti: 
            - decrementa posizione di forza
            - se posizione <= -10 ha vinto, e si mette in attesa di tp[1]
            
    - se tp[1]:
        - se posizione <= -10 riconosce la vittoria di tp[0] e: 
            - incrementa vittorie_tp0
            - setta posizione = 0
            - sveglia tp[0]
        - altrimenti:
            - incrementa posizione di forza
            - se posizione >= 10 ha vinto, e si mette in attesa di tp[0]

    (Opzionale) quando uno dei giocatori ha raggiunto 10 vittorie interrompere il gioco, 
    entrambi i giocatori tp[0], tp[1] devono aver terminato la loro esecuzione, 
    e la funzione main() se ne deve accorgere scrivendo 
    sullo standard output il giocatore che ha totalizzato più vittorie.
 */

import java.util.Random;

public class TiroAllaFuneLambda {
    static volatile int posizione = 0;
    static int vittorie_tp0 = 0;
    static int vittorie_tp1 = 0;
    static Object lock = new Object();

    static Thread tp0 = new Thread(() -> {
        try {
            while (vittorie_tp0 < 10 && vittorie_tp1 < 10) {
                int recupero = new Random().nextInt(4);
                int forza = new Random().nextInt(6);
                Thread.sleep(recupero * 100);

                synchronized (lock) {
                    posizione -= forza;

                    if (posizione <= -10) {
                        vittorie_tp0++;
                        System.out.println("[tp0] Ha vinto tp0 --> (Vittorie tp0: " + vittorie_tp0 + ", Vittorie tp1: " + vittorie_tp1 + ")");
                        posizione = 0;
                        lock.notify();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    static Thread tp1 = new Thread(() -> {
        try {
            while (vittorie_tp0 < 10 && vittorie_tp1 < 10) {
                int recupero = new Random().nextInt(4);
                int forza = new Random().nextInt(6);
                Thread.sleep(recupero * 100);

                synchronized (lock) {
                    posizione += forza;

                    if (posizione >= 10) {
                        vittorie_tp1++;
                        System.out.println("[tp1] Ha vinto tp1 --> (Vittorie tp0: " + vittorie_tp0 + ", Vittorie tp1: " + vittorie_tp1 + ")");
                        posizione = 0;
                        lock.notify();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    public static void main(String[] args) throws Exception {
        tp0.start();
        tp1.start();

        tp0.join();
        tp1.join();

        System.out.println("\nPartita finita");
        if (vittorie_tp0 > vittorie_tp1)
            System.out.println("Il giocatore tp0 ha vinto!");
        else
            System.out.println("Il giocatore tp1 ha vinto!");
    }
}
