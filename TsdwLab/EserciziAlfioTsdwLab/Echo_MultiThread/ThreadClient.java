import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadClient extends Thread
{
    private static int count = 0;
    private int id = count ++;

    public static int getCount() {return count;}

    public ThreadClient(String addr, int port) throws Exception
    {
        count ++;
        start(addr, port);
    }

    public void start(String addr, int port)
    {
        // try-with-resources : resources are automatically closed when the try block ends
        try (
             Scanner keyboard = new Scanner(System.in);
             Socket socket = new Socket(addr, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // auto-flush
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) 
        {
            System.out.println("[+]Client " + id + " connected to server " + addr + ", port: " + port + "\n");

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
