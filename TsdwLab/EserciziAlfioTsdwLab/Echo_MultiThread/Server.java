import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{

    public static void main(String[] args) throws Exception 
    {
        if (args.length != 1) 
        {
            System.out.println("Usage: Server <port>");
            System.err.println("Error arguments");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        System.out.println("[+]Server listening on port " + port + "\n");

        try (ServerSocket serverSocket = new ServerSocket(port)) 
        {
            while (true) 
            {
                Socket socket = serverSocket.accept();
                System.out.println("Client " + socket.getInetAddress() + " connected\n");
                new ThreadServer(socket).start(); 
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        System.out.println("[+]Connection closed...");
    }
}
