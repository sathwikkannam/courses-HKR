package Task_1;
import java.io.IOException;


public class MyRunnable implements Runnable{
    private final String command;

    public MyRunnable(String command){
        this.command = command;

    }


    @Override
    public void run() {
        try {
            TestProcessBuilder.createProcess(this.command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
