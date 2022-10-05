package Task_2A_readerwriter;

/**
 * Requirements:
 *      A new reader are not allowed to start if there are writers waiting for the data.
 */

public class RWLock {
    private int readers;
    private boolean isWriterWaiting;


    public RWLock(){
        this.readers = 0;
        this.isWriterWaiting = false;

    }

    public synchronized void acquireRead() {
        while(this.isWriterWaiting){
            this.awaitThread();
        }
        this.readers++;

    }


    public synchronized void acquireWrite(){
        while(this.isWriterWaiting || this.readers > 0){
            this.awaitThread();

        }
        this.isWriterWaiting = true;

    }

    public synchronized void releaseRead(){
        this.readers--;
        notifyAll();

    }

    public synchronized void releaseWrite() {
        this.isWriterWaiting = false;
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
