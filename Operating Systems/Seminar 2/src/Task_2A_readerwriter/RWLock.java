package Task_2A_readerwriter;

/**
 * Requirements:
 *      A new reader are not allowed to start if there are writers waiting for the data.
 */

public class RWLock {
    private int readers;
    private boolean  isWritingWaiting;


    public RWLock(){
        this.readers = 0;
        this.isWritingWaiting = false;

    }

    public synchronized void acquireRead() {
        while(this.isWritingWaiting){
            this.awaitThread();
        }
        this.readers++;

    }


    public synchronized void acquireWrite(){
        while(this.isWritingWaiting || this.readers > 0){
            this.awaitThread();

        }
        this.isWritingWaiting = true;

    }

    public synchronized void releaseRead(){
        this.readers--;
        notifyAll();

    }

    public synchronized void releaseWrite() {
        this.isWritingWaiting = false;
        notifyAll();

    }


    private synchronized void awaitThread(){
        try {
            wait();
        }catch (InterruptedException e){
            e.printStackTrace();
            awaitThread();
        }

    }

}
