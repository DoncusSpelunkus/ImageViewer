package dk.easv;

import java.util.concurrent.TimeUnit;

public class NewThread implements Runnable{

    private boolean exit;
    private String name;
    Thread t;
    NewThread(String name){

    }

    @Override
    public void run() {
        while (true){
            handleBtnNextAction();
            try {
                if(timeChoiceBox.getValue() != null){
                    TimeUnit.SECONDS.sleep(timeChoiceBox.getValue());
                }
                else TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
