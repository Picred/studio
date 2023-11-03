import java.lang.Thread;

public class Test extends Thread{
    private int tid;
    private Shared shared;

    public Test(int tid, Shared shared){
        this.tid = tid;
        this.shared = shared;
    };

    @Override
    public void run(){
        if (shared.getTurn() != this.tid){
            try{
                shared.sharedWait();
            } catch (InterruptedException e){
                System.out.println("error here");
            }
        }
        shared.add();
        System.out.println("[Tid:" + this.tid + "] Number = " + shared.getN());
        shared.setTurn(1-tid); // TODO: capire -> non va più se rimuovo questa riga. Dopo la notify il thread arriva qui e non controlla il turno
        shared.sharedNotify();
    }
}