
public class Main 
{
    public static void main(String[] args) throws Exception
    {
        Game g = new Game();
        Thread a = new Player(0, g);
        Thread b = new Player(1, g);

        a.start();
        b.start();

        a.join();
        b.join();

        System.out.println("\nGame over");
    }
}