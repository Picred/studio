/*
	Realizzare un server che tiene traccia della disponibilità di 10 libri, che possono essere disponibili o in prestito;
	riceve delle richieste da parte dei client del tipo "titolo del libro"
	e risponde "Disponibile", "In prestito" o "Inesistente" a seconda del titolo richiesto.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Libreria 
{

    public record Book(String name, String state) {}

    static Book[] books = { new Book("Il Signore degli Anelli", "Disponibile"),
                            new Book("Cronache di Narnia", "Disponibile"),
                            new Book("Harry Potter", "In prestito"),
                            new Book("1984", "Disponibile"),
                            new Book("Il Piccolo Principe", "In prestito"),
                            new Book("Guerra e Pace", "Disponibile"),
                            new Book("Don Chisciotte", "In prestito"),
                            new Book("Moby Dick", "In prestito"),
                            new Book("Orgoglio e Pregiudizio", "Disponibile"),
                            new Book("Cime tempestose", "Disponibile")};

    static String processing(String book) 
    {
        for (Book b : books) 
        {
            if (book.equals(b.name))
                return b.state;
        }
        return "Inesistente"; 
    }

    public static void main(String[] args) 
    {
        if(args.length != 1)
        {
            System.out.println("Usage: Server <port>");
            System.err.println("Error: Invalid arguments");
            System.exit(1);
        }

        int ServerPort = Integer.parseInt(args[0]);
        System.out.println("[+]Server listening on port " + ServerPort + "\n");

        try ( ServerSocket ServerSocket = new ServerSocket(ServerPort);
              Socket socket = ServerSocket.accept();
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            )
        {
            System.out.println("[+]Connected host " + socket.getInetAddress() + "\n");

            String recvLine = null, outcome = null;
            while((recvLine = in.readLine()) != null)
            {
                System.out.println("Client: " + recvLine);
                outcome = processing(recvLine);
                System.out.println("Outcome: " + outcome + "\n");
                out.println(outcome);
            }
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        System.out.println("[+]Connection closed...");
    }
}