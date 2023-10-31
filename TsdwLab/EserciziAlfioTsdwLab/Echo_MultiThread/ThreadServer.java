import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadServer extends Thread 
{
    private Socket socket;

    public ThreadServer(Socket socket) { this.socket = socket; }

    @Override
    public void run() 
    {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) 
        {
            String buffer;
            while ((buffer = in.readLine()) != null) 
            {
                System.out.println("Client: " + buffer);
                System.out.println("--> " + buffer.length() + " characters\n");
                out.println(buffer);
            }
            System.out.println("Client " + socket.getInetAddress() + " disconnected\n");
        } 
        catch (Exception e) { e.printStackTrace(); }
    }
}