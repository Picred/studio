import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;


public class Server{
    private static final int PORT = 8080;
    private static int connections = 0;
    private static Database database = Database.getInstance();
    private static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args){
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(PORT);
        } catch(IOException e){
            System.out.println("ServerSocket");
            e.printStackTrace();
        }
        System.out.println("Starting: " + serverSocket);
        BufferedReader in = null;
        PrintWriter out = null;
        Socket clientSocket = null;
        while(connections < 3){
            try{
                clientSocket = serverSocket.accept();
                connections++;
                System.out.println("Connections: " + connections);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                threads.add(new SubServer(clientSocket, in, out));
                threads.get(threads.size() - 1).start();
                //gestisci fine while del server, quindi break
            }catch(IOException e){
                System.out.println("accept");
                e.printStackTrace();
            }
        }
        for(Thread t : threads){
            try{
                t.join();
            } catch(InterruptedException e){
                System.out.println("join");
                e.printStackTrace();
            }
        }
        System.out.println("MainServer closing..");
    }
}