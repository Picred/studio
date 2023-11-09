import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class Server 
{
    static String handleRequest(Message msg) 
    {
        if ("LOG".equalsIgnoreCase(msg.operation)) 
            return log(msg) ? "Login successful" : "Login failed";

        else if ("REG".equalsIgnoreCase(msg.operation)) 
            return reg(msg) ? "Registration successful" : "Registration failed";

        else if ("DEL".equalsIgnoreCase(msg.operation)) 
            return del(msg) ? "Deletion successful" : "Deletion failed";
        
        return "Invalid operation";
    }

    static boolean log(Message msg) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader("Database.txt"))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                StringTokenizer tokenizer = new StringTokenizer(line);
                String usr = tokenizer.nextToken();
                String pass = tokenizer.nextToken();
                if (usr.equals(msg.username)) 
                    return pass.equals(msg.password);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    static boolean reg(Message msg) 
    {
        try (FileWriter fileWriter = new FileWriter("Database.txt", true)) 
        {
            fileWriter.write(msg.username + " " + msg.password + "\n");
            return true;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    static boolean del(Message msg) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader("Database.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("Database_tmp.txt"))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                StringTokenizer tokenizer = new StringTokenizer(line);
                String usr = tokenizer.nextToken();
                String pass = tokenizer.nextToken();

                if (usr.equals(msg.username) && !pass.equals(msg.password)) 
                    bw.write(usr + " " + pass + "\n");
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        File database = new File("Database.txt");
        File tempFile = new File("Database_tmp.txt");
        if (database.delete()) 
        {
            if (!tempFile.renameTo(database)) 
                System.out.println("Error renaming temp file");
        } 
        else 
            System.out.println("Error deleting the database file");

        return true;
    }

    public static void main(String[] args) throws ClassNotFoundException 
    {
        if(args.length < 1)
        {   
            System.err.println("Usage: Server <port>");
            System.exit(1);
        }

        int serverPort = Integer.parseInt(args[0]);

        System.out.println("Server listening on port " + serverPort + "\n");
        try (   ServerSocket serverSocket = new ServerSocket(serverPort);
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) 
        {

            System.out.println("[+]Client " + clientSocket.getInetAddress() + " connected");
            while(true)
            {
                Message msg = (Message) in.readObject();
                String outcome = handleRequest(msg);
                out.println(outcome);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        System.out.println("Connection close...");
    }
}