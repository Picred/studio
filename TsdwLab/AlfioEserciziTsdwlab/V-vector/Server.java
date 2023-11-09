import java.io.*;
import java.net.*;

/*
 * 1­Socket 
 
Implementare un server, in C o Java, che risponde sul port 7777. 
 
Il server mantiene un vettore V con le ultime dieci richieste (stringhe di 10 
caratteri) ricevute. 
 
Le richieste a cui il server deve rispondere sono: 

1. la stringa "LIST", a cui il server risponde inviando al client tutte le 
risposte finora memorizzate in V, separate da '\n'; 

2. qualsiasi altra stringa viene trattata come messaggio da inserire nel 
vettore V: 

    2.1 se la stringa è già presente in V, il server risponde con "presente"; 
    2.2 se la stringa non è ancora presente in V, il server la aggiunge a V, 
    eventualmente sovrascrivendone una a caso tra quelle già esistenti, 
    e risponde "inserita" al client. 
 
Dopo avere risposto, il server chiude la connessione con il client e torna in 
attesa di richieste. 
 
Facoltativo per la prova in itinere, obbligatorio per l’esame completo : 
Implementare un client per testare il server. 

RMI

a) Implementare un metodo remoto  int conta(String) che prende in input una stringa e 
   restituisce il numero di caratteri della stessa. 
 
b) Implementare un server identico a quello dell’esercizio 1 (“ copia e incolla ”...) a meno 
   del requisito 2.1 modificato come segue: 

    2.1b se la stringa è già presente in V, il server invia la stringa al 
    servizio  conta()  e risponde al client "dati presenti, X caratteri", 
    sostituendo ad X il risultato dell'invocazione al metodo remoto 
    conta() . 
*/

public class Server
{
    static final int port = 7777;

    static String[] createVector(int n)
    {
        String[] V = new String[n];
        for(int i=0; i<n; i++)
            V[i] = new String("");
        return V;
    }

    static String LIST(String[] V)
    {
        String str = "";
        for(String s : V)
            str += s + "\n";
        return str;
    }

    static void printVector(String[] V) 
    { 
        System.out.println("Vector");
        System.out.println("-------------------------");
        System.out.println(LIST(V)); 
        System.out.println("-------------------------");
    }

    static int conta(String str) { return str.length(); }

    public static void main(String[] args)
    {
        System.out.println("[+]Server listening on port " + port + "...\n");
        try ( ServerSocket server = new ServerSocket(port);
              Socket socket = server.accept();
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) 
        {
            System.out.println("Client " + socket.getInetAddress() + " connected\n");

            String[] V = createVector(10);
            String recvline = "";
            int index = 0;
            boolean isPresent = false;
            while((recvline = in.readLine()) != null)
            {
                System.out.println("Client: " + recvline);
                
                if (recvline.equals("LIST"))
                {
                    printVector(V);
                    out.println(LIST(V));
                }
                else
                {
                    for (int i=0; i<V.length; i++)
                    {
                        if (recvline.equals(V[i]))
                        {
                            System.out.println("Dati presenti! La parola contiene "+conta(recvline)+" caratteri\n");
                            out.println("Dati presenti! La parola contiene "+conta(recvline)+" caratteri");
                            isPresent = true;
                            break;
                        }
                    }
                    if (!isPresent)
                    {
                        System.out.println("Inserita\n");
                        V[index] = new String(recvline);
                        out.println("Inserita");
                        index = (index + 1) % V.length;
                        isPresent = false;
                    }
                }
            }
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.out.println("[+]Connection closed...");
    }
}