/*
    Tecniche di Programmazione Concorrente e Distribuita  24/05/2016

    1. Socket (C o Java)

    Scrivere un server che si metta in ascolto sul port 3333 in grado di rispondere ad un messaggio composto da una sola stringa str composta 
    da cifre numeriche (da 0 a 9) terminata dal carattere \n.

    Il server:
    Converte la stringa ricevuta str in un numero intero n.
    Il numero viene usato come input per il metodo “int cubo(int n)” che restituisce il cubo dell’intero n.
    Il server invia al client il valore restituito dal metodo cubo(). 

    Testare il server con un semplice client o con telnet.

    Tempo a disposizione: 35 minuti.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
    static final int portServer = 3333;

    static int cubo(int n) {return (int) Math.pow(n, 3);}

    public static void main(String[] args) 
    {
        System.out.println("[+]Server listening on port " + portServer + "\n");
        
        // try-with-resource
        try ( 
                ServerSocket server = new ServerSocket(portServer);
                Socket socket = server.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            )
        {   
            System.out.println("Connected client " + socket.getInetAddress() + "\n");

            String str = null;
            while((str = in.readLine()) != null)
            {
                System.out.println("Client: " + str);
                int n = Integer.parseInt(str);
                int result = cubo(n);
                System.out.println("Reply: " + result + "\n");
                out.println("Cube of " + str + " is " + result);
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("\n[+]Connection closed...");
    }
}