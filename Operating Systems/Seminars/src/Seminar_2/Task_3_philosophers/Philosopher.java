package Task_3_philosophers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Philosopher implements Runnable{
    private final int myId;
    private final Table myTable;

    public Philosopher(int id,Table table){
        myId = id;
        myTable = table;
    }

    @Override
    public void run(){
        for(int i =0 ; i < 100; i++){
            try {

                // Modified the sleep times to observe deadlock.
                System.out.println("Philosopher " + myId + " thinks. Iteration "+ i);
                Thread.sleep((int)(Math.random()*10));
                                       
                myTable.getLeftChopstick(myId);
                System.out.println("Philosopher " + myId + " pick up left");
                Thread.sleep((int)(Math.random()*1));
            
                myTable.getRightChopstick(myId);
                System.out.println("Philosopher " + myId + " pick up right");
              
                System.out.println("Philosopher " + myId + " eats. Iteration "+ i);
                Thread.sleep((int)(Math.random()*10));
               
                myTable.releaseLeftChopstick(myId);
                System.out.println("Philosopher " + myId + " drop left");
                Thread.sleep((int)(Math.random()*1));
                            
                myTable.releaseRightChopstick(myId);
                System.out.println("Philosopher " + myId + " drop right");
                Thread.sleep((int)(Math.random()*1));
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
