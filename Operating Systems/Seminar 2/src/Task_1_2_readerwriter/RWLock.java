package Task_1_2_readerwriter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock {
    private boolean isReading = false;
    private boolean isWriting = false;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    ReentrantReadWriteLock.ReadLock read = lock.readLock();
    ReentrantReadWriteLock.WriteLock write = lock.writeLock();


    public RWLock(){

    }


    //Only one thread can access any of the functions at a time.
    public synchronized void acquireRead() throws InterruptedException {
        while(isWriting){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        isReading = true;

    }

    public synchronized void acquireWrite(){
        while(isWriting || isReading){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        isWriting = true;
    }

    public synchronized void releaseRead(){
        notifyAll();
        isReading = false;

    }

    public synchronized void releaseWrite() {
        notifyAll();
        isWriting = false;
        writersWaiting--;
    }

}
