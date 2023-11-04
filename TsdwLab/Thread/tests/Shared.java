public class Shared{
    private int number = 0;
    private int turn;

    public void add(){
        this.number++;
        this.turn = 0;
    }

    public int getN(){
        return this.number;
    }

    public int getTurn(){
        return this.turn;
    }

    public void sharedWait() throws InterruptedException{
        wait();
    }

    public synchronized void sharedNotify(){
        notifyAll();
    }

    public void setTurn(int t){
        this.turn = t;
    }
}