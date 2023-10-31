import java.util.Random;

public class Player extends Thread {

    Game g = null;
    final  int index;
    static int position = 0;
    
    Player(int _index, Game _g)
    {
        this.index = _index;
        this.g = _g;
    }    

    @Override
    public void run()
    {
        try 
        {
            while (true)
            {
                int turno = g.getRound();
                if (turno == index)
                {   
                    int outcome = launch();
                    System.out.println("P" + index + "'s turn");
                    System.out.println("Launch: " + outcome);
                    System.out.println("Position: " + position + "\n");
                    position += outcome;
    
                    if (position >= 100) 
                    {
                        System.out.println("P" + index + " won");
                        g.setRound(-1);
                        g.GameNotifyAll();
                        break;
                    }
                    else
                    {
                        g.setRound(1 - index);
                        g.GameNotifyAll();
                        g.GameSleep();
                        g.GameWait();
                    }
                }
                else if (turno == -1)
                {
                    System.out.println("P" + index + " lost");
                    break;
                }
                else 
                {
                    g.GameWait();
                }
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static int launch() { return new Random().nextInt(7); }
}