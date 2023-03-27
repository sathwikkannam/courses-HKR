package Task_3_philosophers;

public class Table {
	private final int nbrOfChopsticks;
	private final boolean[] chopstick; // true if chopstick[i] is available
	private Thread currentPhilosopherThread; // Task 3B.

	public Table(int nbrOfSticks) {
		this.nbrOfChopsticks = nbrOfSticks;
		this.chopstick = new boolean[nbrOfChopsticks];
		for (int i = 0; i < this.nbrOfChopsticks; i++) {
			this.chopstick[i] = true;
		}
	}

	public synchronized void getLeftChopstick(int n) {
		//Wait if chopstick[n] = leftChopStick is false/unavailable and if it is new thread when currentPhilosopherThread == null
		while(!this.chopstick[n] && this.currentPhilosopherThread == null) {
			this.threadWait();
		}

		this.chopstick[n] = false;
		this.currentPhilosopherThread = Thread.currentThread(); // Store the thread that picked up the left chopstick.
	}

	public synchronized void getRightChopstick(int n) {
		int pos = n + 1;
		if (pos == this.nbrOfChopsticks){
			pos = 0;
		}

		//Wait if chopstick[pos] = rightChopStick is false/unavailable and if the thread that picked up the left is not the same as current thread.
		while(!this.chopstick[pos] && this.currentPhilosopherThread != Thread.currentThread()){
			this.threadWait();
		}

		this.chopstick[pos] = false;
	}

	public synchronized void releaseLeftChopstick(int n) {
		this.chopstick[n] = true;
		this.currentPhilosopherThread = null; // Reset currentPhilosopherThread.
		notifyAll();

	}

	public synchronized void releaseRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks){
			pos = 0;

		}
		this.chopstick[pos] = true;
		this.currentPhilosopherThread = null;
		notifyAll();
	}


	public synchronized void threadWait(){
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
			threadWait();
		}
	}
}
