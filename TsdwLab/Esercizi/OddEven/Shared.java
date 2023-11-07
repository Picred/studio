public class Shared{
    private int n;

    public Shared(){
        this.n = 0;
    }

    public /* synchronized */ void sleepSh(int dur) throws InterruptedException{
        Thread.sleep(dur);
    }

    
}