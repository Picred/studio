import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client 
{
    public static void main(String[] args) 
    {
        if(args.length < 2)
        {   
            System.err.println("Usage: Client <ip> <port>");
            System.exit(1);
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) 
        {

            System.out.println("[+]Connected to the server " + socket.getInetAddress() + " on port " + socket.getPort());

            while (true) 
            {
                System.out.print("Enter a request (REG, LOG, DEL, or EXIT): ");
                String request = scanner.nextLine();

                if (request.equalsIgnoreCase("EXIT")) 
                {
                    System.out.println("Goodbye!");
                    break;
                }

                System.out.print("Enter username: ");
                String usr = scanner.nextLine();

                System.out.print("Enter password: ");
                String pass = scanner.nextLine();

                Message msg = new Message(request, usr, pass);
                out.writeObject(msg);

                String response = in.readLine();
                System.out.println("Response: " + response + "\n");
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}