import java.net.*;
import java.io.*;

public class Server {
    public static final int PORT = 1050;
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(Server.PORT);
        } catch (IOException e){

        }

        Socket clientSocket = null;
        BufferedReader recv = null;
        PrintWriter send = null;

        try{
            clientSocket = serverSocket.accept();
            System.out.println("Accepted " + clientSocket);

            recv = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            send = new PrintWriter(clientSocket.getOutputStream(), true);

            String received;

            while((received = recv.readLine()) != null){
                if (received.equals("FINE"))
                    break;
                System.out.println("Received: " + received);
                send.println("ACK from server.");
            }
        } catch(IOException e){
            System.out.print("accept()");
            System.exit(1);
        }

        try{
            recv.close();
            send.close();
            clientSocket.close();
            serverSocket.close();
        }catch(IOException e){
            System.out.println("Error closing..");
        }
    }
}
