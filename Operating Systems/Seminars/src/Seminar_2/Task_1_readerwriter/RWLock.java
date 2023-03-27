package Seminar_2.Task_1_readerwriter;


/**
 * Requirements:
 * A reader should wait only if a writer has locked the data.
 * Multiple readers can access the same data simultaneously.
 * A writer must wait if readers or writers are active.
 */


public class RWLock {
    private int readers;
    private boolean isWriting;

    public RWLock() {
        this.isWriting = false;
        this.readers = 0;

    }


    //Only one thread can access any of the functions at a time.
    public synchronized void acquireRead() {
        while (this.isWriting) {
            this.awaitThread(); //Wait for writer thread.
        }
        this.readers++;

    }

    public synchronized void acquireWrite() {
        while (this.readers > 0 || this.isWriting) { //Blocking the writers since readers have higher priority. Not really starvation but waiting state for writers.
            this.awaitThread(); //Wait for writer thread or reader thread.
        }
        this.isWriting = true;

    }

    public synchronized void releaseRead() {
        this.readers--;
        if (this.readers <= 0) { // We can have multiple readers.
            notifyAll();
        }

    }

    public synchronized void releaseWrite() { //Notify all threads.
        this.isWriting = false;
        notifyAll();

    }

    private synchronized void awaitThread() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
