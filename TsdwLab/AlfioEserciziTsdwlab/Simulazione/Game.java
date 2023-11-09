
public class Game 
{
    private int round = 0;

    public int getRound() { return this.round; }

    public synchronized void setRound(int _round) { this.round = _round; }

    public synchronized void GameWait() throws Exception { wait(); }

    public synchronized void GameNotifyAll() throws Exception { notifyAll(); }

    public synchronized void GameSleep() throws Exception { Thread.sleep(500); }
}