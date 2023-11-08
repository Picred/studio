import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private static final int PORT = 8080;
    private static Database database = Database.getInstance();
    private static int connections = 0;
    private static int maxConnections = 3;
    private static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args){
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(PORT);
        } catch(IOException e){
            System.out.println("ServerSocket");
            e.printStackTrace();
        }

        BufferedReader in = null;
        PrintWriter out = null;
        Socket clientSocket = null;
        while(true){
            try{
                if(connections < maxConnections){
                    clientScket = serverSocket.accept();
                    out = new BufferedReader(new InputStreamReader(clientSocket.getOutputStream()));
                    in = new PrintWriter(clientSocket.getInputStream(), true);

                    threads.add(new SubServer(clientSocket, in, out));
                    // start(?) how to start. How start only this thread??? 
                    //gestisci fine while del server, quindi break
                }
            }catch(IOException e){
                System.out.println("accept");
                e.printStackTrace();
            }

            //wait all threads before finish

            for(Thread t : threads){
                t.join();
            }

            System.outprintln("MainServer closing..");
            try{
                in.close();
                out.close();
                serverSocket.close();
                clientSocket.close();
            } catch(IOException e){
                System.out.println("close");
                e.printStackTrace();
            }

        }
    }
}