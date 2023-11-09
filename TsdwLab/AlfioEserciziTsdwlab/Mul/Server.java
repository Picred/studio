/*  Socket (C o Java)

    A) Realizzare un server (in C o in Java) chiamato Server A che si metta in ascolto sul port
    7777 per ricevere una stringa str composta da una sequenza di lunghezza
    arbitraria di caratteri numerici da (0 a 9) e terminata dal carattere \n. Il server dovrà
    quindi stampare il messaggio ricevuto sullo standard output. Testare il server usando
    telnet.

    B) Estendere le funzionalità dal server definito al punto precedente realizzando un secondo
    server chiamato Server B che oltre a stampare il messaggio ricevuto sullo standard
    output, lo invia come risposta al client (senza modificarlo). Testare il server usando
    telnet.

    C) Estendere le funzionalità dal server definito al punto precedente realizzando un terzo
    server chiamato Server C che oltre a stampare il messaggio ricevuto sullo standard
    output, lo passa ad un metodo “int MUL(String str)” che per ora restituisce
    sempre 0 per qualunque parametro di input str. Il risultato del metodo deve quindi
    essere inviato come messaggio di risposta al client. Testare il server usando telnet.

    D) Estendere le funzionalità dal server definito al punto precedente realizzando un quarto
    server chiamato Server D modificando il comportamento del metodo “int
    MUL(String str)” che dovrà restituire il prodotto delle singole cifre numeriche
    presenti nella stringa in input, ad esempio per la stringa “1234” restituirà l’intero 24. Il
    risultato del metodo deve quindi essere inviato come messaggio di risposta al client.
    Testare il server usando telnet.

    E) (Opzionale) Realizzare un semplice client per testare i server creati ai punti precedenti
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    static final int ServerPort = 7777;

    static int MUL(String str)
    {
        boolean thereIsDigits = false;
        int result = 1;
        for(int i=0; i<str.length(); i++)
        {
            char c = str.charAt(i);
            if(c >= '0' && c <= '9')
            {
                thereIsDigits = true;
                result *= Character.getNumericValue(c);
            }
        }
        return thereIsDigits ? result : 0;
    }

    public static void main(String[] args) 
    {
        System.out.println("[+]Server listening on port " + ServerPort + "\n");

        try ( ServerSocket server = new ServerSocket(ServerPort);
              Socket socket = server.accept();
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            )
        {
            System.out.println("[+]Connected host " + socket.getInetAddress() + "\n");
            String recvLine = null;
            while((recvLine = in.readLine()) != null)
            {
                System.out.println("Client: " + recvLine);
                int outcome = MUL(recvLine);
                System.out.println("Server: " + outcome + "\n");
                out.println(outcome);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        System.out.println("[+]Connection close...");
    }
}