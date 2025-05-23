package Seminar_2.Task_1_readerwriter;

import java.util.logging.Level;
import java.util.logging.Logger;

//import Task_1_readerwriter.RWLock;  // <- Task 1
//import Task_2A_readerwriter.RWLock; // <- Task 2A
//import Task_2B_readerwriter.RWLock; // <- Task 2B

public class Data {
    private static final int INITIAL_VALUE = 100;
    private int myValue = INITIAL_VALUE;
    private final RWLock readerAndWriterLock;

    public Data() {
        readerAndWriterLock = new RWLock();
    }

    public void read(int id) {
        readerAndWriterLock.acquireRead();
        int val = myValue;

        System.out.println("Reader:" + id + " got the value: " + val);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Reader:" + id + " thinks that the value is: " + val + ", but the value is: " + myValue);
        readerAndWriterLock.releaseRead();
    }

    public void write(int id, int val) {
        readerAndWriterLock.acquireWrite();
        myValue = val;
        System.out.println("Writer:" + id + " updated the value to :" + val);
        readerAndWriterLock.releaseWrite();
    }
}
