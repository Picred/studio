import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client 
{
    public static void main(String[] args) throws Exception 
    {

        if (args.length != 2) 
        {
            System.out.println("Usage: Client <ip> <port>");
            System.err.println("Error arguments");
            System.exit(1);
        }

        String addr = args[0];
        int port = Integer.parseInt(args[1]);

        // try-with-resources : resources are automatically closed when the try block ends
        try (
             Scanner keyboard = new Scanner(System.in);
             Socket socket = new Socket(addr, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // auto-flush
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) 
        {
            System.out.println("[+]Client connected to server " + addr + ", port: " + port + "\n");

            while (true) 
            {
                System.out.print("Enter a message (or 'exit' to quit): ");
                String buffer = keyboard.nextLine(); 
                
                if ("exit".equalsIgnoreCase(buffer)) 
                    break;

                out.println(buffer);
                buffer = in.readLine();
                System.out.println("Server: " + buffer + "\n");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        System.out.println("\n[+]Connection closed...");
    }
}
