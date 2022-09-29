package Task_2B_readerwriter;

import java.util.concurrent.locks.ReentrantReadWriteLock;


public class RWLock {
    private final ReentrantReadWriteLock.WriteLock writeLock;
    private final ReentrantReadWriteLock.ReadLock readLock;

    public RWLock(){
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();

    }

    public void acquireRead() {
        this.readLock.lock();
    }

    public void acquireWrite(){
        this.writeLock.lock();
    }

    public void releaseRead(){
        this.readLock.unlock();

    }

    public void releaseWrite() {
        this.writeLock.unlock();
    }

}
