
public class Client 
{
    static final int MAX_THREADS = 1;
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

        while(true)
        {
            if(ThreadClient.getCount() < MAX_THREADS)
                new ThreadClient(addr, port).start();
            Thread.sleep(1000);
        }
    }
}
