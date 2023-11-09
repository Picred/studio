/* 
Esercizio 1: Socket (C o Java) -- Tempo a disposizione: 60 minuti.

A) Realizzare un server (in C o in Java) che si metta in ascolto sul port 3333, sul quale accetta richieste di connessione da un client. 
Sulla connessione stabilita, il server riceve una stringa, terminata dal carattere '\n' (si supponga che basti una sola operazione di lettura 
in ricezione da parte del server, per ricevere ciascuna stringa inviata dal client). 
Il server quindi scrive la stringa ricevuta sullo standard output e, chiusa la connessione, si rimette in attesa di eventuali richieste di connessione. 
Testare il server server_A usando telnet. 

B) Conservando la versione (A) del server, realizzarne un’ulteriore versione (B), tale che ciascuna stringa ricevuta dal server, oltre ad essere scritta 
dal server sulla propria standard output, sia inviata nuovamente al client come risposta (senza alcuna modifica). 
Testare il server server_B usando telnet.

C) Conservando la versione (B) del server, realizzarne un’ulteriore versione tale che il server implementi un metodo/funzione sommacifre(s) 
che accetta per argomento una stringa s e restituisce un intero;  per il momento, ai fini di questo quesito (C), si supponga che, per qualunque stringa 
argomento s, venga restituito l’intero 0. In questa versione, il server, ricevuta una stringa s la scrive sulla standard output, calcola x = sommacifre(s) 
e invia al client un messaggio in forma di stringa  del tipo n,x dove n è un id progressivo della richiesta fatta dal client, mentre x è l’output del 
metodo sommacifre(s). L’id, che fa parte dello stato del server, parte da 0 e viene incrementato ad ogni nuova richiesta da parte di un client fino a 
quando il server non viene riavviato. 
Testare il server server_C usando telnet. 

D) Modificare la funzione sommacifre(s) in modo che, riconosciute le cifre numeriche al suo interno, restituisca la somma delle singole cifre o 0 se non 
ve ne fosse alcuna. 

Esempio: sommacifre("c0a89s2a3hk") restituirà 22 (dato che 0+8+9+2+3 = 22).

E) (Opzionale) Realizzare un semplice client per testare i server creati ai punti precedenti.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{

    // Function to calculate the sum of digits in a string
    static int sommacifre(String buffer) 
    {
        int sumValue = 0;
        for (int i = 0; i < buffer.length(); i++) 
        {
            char c = buffer.charAt(i);
            if (c >= '0' && c <= '9')
                sumValue += Character.getNumericValue(c);
        }
        return sumValue;
    }

    public static void main(String[] args) 
    {
        if (args.length != 1) 
        {
            System.out.println("Usage: Server <port>");
            System.err.println("Error: Invalid arguments");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        System.out.println("[+]Server listening on port " + port + "\n");

        // Try-with-resources automatically closes resources when the try block exits
        try (
            ServerSocket serverSocket = new ServerSocket(port); 
            Socket socket = serverSocket.accept(); // Accept incoming client connection
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) 
        {
            String recvline = "";
            while ((recvline = in.readLine()) != null) 
            {
                System.out.println("Client: " + recvline);
                int sumValue = sommacifre(recvline);
                System.out.println("Outcome: " + sumValue + "\n");

                // Send the outcome back 
                out.println(sumValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("[+]Connection closed...");
    }
}
