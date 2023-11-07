import java.util.Random;

public class MyThread extends Thread{
    private Random rand = new Random();
    private String threadName;
    private Shared shared;

    public MyThread(String name, Shared sh){
        this.threadName = name;
        this.shared = sh;
    }

    @Override
    public void run(){
        if(threadName.equals("tE")){ // tE
            while(true){
                try{
                    shared.sleepSh(200);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                int r = rand.nextInt()*2; // pari
                //sommalo a shared.n
            }
        }
        else{ // tO
            while(true){

            }
        }
    }
}