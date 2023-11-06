public class Shared{
    private int sample;

    public Shared(){
        this.sample = 50;
    }

    public int getSample(){
        return sample;
    }

    public synchronized void setSample(int k){
        sample = k;
    }

    public synchronized void notifySh(){
        notify();
    }

    public synchronized void waitSh(){
        try{
            wait();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}