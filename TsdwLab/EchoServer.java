import java.io.*;
import java.net.*;

public class EchoServer {
    public static final int PORT = 1050; // Server port
    public static final String SECRET = "mischief-managed"; // Server secret per chiudere la socket

    public static void main(String[] args) {
        ServerSocket serverSocket = null; 
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) { // non c'è la porta libera, ovvero c'è un altro server attivo o serve l'autorizzazione
            e.printStackTrace();
        }
        System.out.println("EchoServer: started ");
        System.out.println("Server Socket: " + serverSocket);
        Socket clientSocket=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            // Waits until connection is available (bloccante)
            clientSocket = serverSocket.accept(); //accept() restituisce un Socket quando accetta la connessione. E' bloccante
            System.out.println("Connection accepted: "+ clientSocket);

            // Input Stream
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //accesso alle info che si ricevono dal socketClient (quello che manda il client)

            // Output Stream
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            // Loop, "mischief-managed" will close it
            while ((inputLine = in.readLine()) != null) { //finche ricevo comandi
                if (inputLine.equals(SECRET))  //mi fermo solo se leggo SECRET
                    break;
                System.out.println("Echoing: " + inputLine); 
                out.println(inputLine); // Send it back
            }
        }
        catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
        // Closing all flows
        System.out.println("EchoServer: closing...");

        try {
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Errorclosing...");
            e.printStackTrace();
        }

    }
}

// java -cp <jar_path> per compilare