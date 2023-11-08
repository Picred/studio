import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SubServer extends Thread{
    private Socket clientSocket;
    BufferedReader in;
    PrintWriter out;

    public SubServer(Socket clientSocket, BufferedReader in, PrintWriter out){
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
    }

    
    @Override
    public void run(){
    }
}