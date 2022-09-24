package Task_2_readerwriter;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Requirements:
 *      A new reader are not allowed to start if there are writers waiting for the data.
 */

public class RWLock {
    private final ReentrantReadWriteLock lock;
    private final ReentrantReadWriteLock.WriteLock writeLock;
    private final ReentrantReadWriteLock.ReadLock readLock;

    public RWLock(){
        this.lock = new ReentrantReadWriteLock(true);
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();

    }

    public void acquireRead() {
        while(lock.isWriteLocked() || lock.getReadHoldCount() > lock.getWriteHoldCount()){ //Priority to writers.
            try {
                readLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        readLock.lock();
    }

    public void acquireWrite(){
        writeLock.lock();
    }

    public void releaseRead(){
        readLock.unlock();

    }

    public void releaseWrite() {
        writeLock.unlock();
    }

}
