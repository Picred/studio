import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws UnknownHostException{
        InetAddress serverAddr;

        if(args.length == 0)
            serverAddr = InetAddress.getByName(null);
        else serverAddr = InetAddress.getByName(args[0]);
        
        Socket socket = null;
        BufferedReader recv = null, stdIn = null;
        PrintWriter send = null;
    
        try {
            socket = new Socket(serverAddr, Server.PORT);
            recv = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            send = new PrintWriter(socket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            String input;
            while(true){
                input = stdIn.readLine();
                send.println(input);
                if(input.equals("FINE")) 
                    break;
                System.out.println(recv.readLine());
            }

        } catch (UnknownHostException e){
            System.out.println("Not found host");
            System.exit(1);
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            socket.close();
            recv.close();
            send.close();
            stdIn.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Closing...");
    }
}
