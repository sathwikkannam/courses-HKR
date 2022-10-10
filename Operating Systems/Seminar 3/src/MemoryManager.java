import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MemoryManager {

	private final int numberOfPages;
	private final int pageSize; // In bytes
	private final int numberOfFrames;
	private int[] pageTable; // -1 if page is not in physical memory
	private final byte[] RAM; // physical memory RAM
	private RandomAccessFile pageFile; //BACKING_STORE_FILE
	private int nextFrameNumber = 0;
	private int numberOfPageFaults = 0;
	private int myPageReplacementAlgorithm = 0;
	private final int invalidBit = -1;
	private final int pageNumberBoundary;
	private final int offsetBoundary;
	private final int[] frameOccurrences;

	public MemoryManager(int numberOfPages, int pageSize, int numberOfFrames, String pageFile,
			int pageReplacementAlgorithm) {

		this.numberOfPages = numberOfPages;
		this.pageSize = pageSize;
		this.numberOfFrames = numberOfFrames;
		this.myPageReplacementAlgorithm = pageReplacementAlgorithm;
		int logicalAddressSpace = numberOfPages * pageSize;
		int offset = (int) ((Math.log(pageSize) / Math.log(2)));
		this.pageNumberBoundary = (int) ((Math.log(logicalAddressSpace) / Math.log(2)) - offset); // Upper bits of logicalAddress.
		this.offsetBoundary = offset; // Lower bits of logicalAddress.
		this.frameOccurrences = new int[numberOfFrames]; // Holds the age of each frame. frameOccurrences[frameNumber] = lifetime (int);

		initPageTable();
		RAM = new byte[this.numberOfFrames * this.pageSize];

		try {

			this.pageFile = new RandomAccessFile(pageFile, "r");

		} catch (FileNotFoundException ex) {
			System.out.println("Can't open page file: " + ex.getMessage());
		}
	}

	private void initPageTable() {
		pageTable = new int[numberOfPages];
		for (int n = 0; n < numberOfPages; n++) {
			pageTable[n] = -1;
		}
	}

	public byte readFromMemory(int logicalAddress) {
		int pageNumber = getPageNumber(logicalAddress);
		int offset = getPageOffset(logicalAddress);

		for (int i = 0; i < this.frameOccurrences.length; i++) {
			this.frameOccurrences[i]++; // Each time we add page to a frame, we increment the lifetime of each frame.
		}

		if (pageTable[pageNumber] == -1) {
			pageFault(pageNumber);
		}

		int frame = pageTable[pageNumber];
		int physicalAddress = frame * pageSize + offset;
		byte data = RAM[physicalAddress];
		this.frameOccurrences[pageTable[pageNumber]] = 0; // Once we add page to a frame, we reset that frame's lifetime.


		System.out.print("Virtual address: " + logicalAddress);
		System.out.print(" Physical address: " + physicalAddress);
		System.out.println(" Value: " + data);
		return data;
	}

	private int getPageNumber(int logicalAddress) {
		return logicalAddress >> this.pageNumberBoundary; // Return n (pageNumberBoundary) upper bits of logicalAddress.

	}

	private int getPageOffset(int logicalAddress) {
		return (logicalAddress  << -this.offsetBoundary >>> -this.offsetBoundary); // Return n (offsetBoundary) lower bits of logicalAddress.
	}

	private void pageFault(int pageNumber) {
		if (myPageReplacementAlgorithm == Seminar3.NO_PAGE_REPLACEMENT){
			handlePageFault(pageNumber);
		}

		if (myPageReplacementAlgorithm == Seminar3.FIFO_PAGE_REPLACEMENT) {
			handlePageFaultFIFO(pageNumber);
		}

		if (myPageReplacementAlgorithm == Seminar3.LRU_PAGE_REPLACEMENT){
			handlePageFaultLRU(pageNumber);
		}

		readFromPageFileToMemory(pageNumber);
	}

	private void readFromPageFileToMemory(int pageNumber) {
		try {
			int frame = pageTable[pageNumber];
			pageFile.seek((long) pageNumber * pageSize);
			for (int b = 0; b < pageSize; b++)
				RAM[frame * pageSize + b] = pageFile.readByte();
		} catch (IOException ex) {
				ex.printStackTrace();
		}
	}

	public int getNumberOfPageFaults() {
		return numberOfPageFaults;
	}

	private void handlePageFault(int pageNumber) {
		this.pageTable[pageNumber] = nextFrameNumber;
		this.nextFrameNumber++;
		this.numberOfPageFaults++;

	}

	private void handlePageFaultFIFO(int pageNumber){
		/*
			If there is a page in the page table with the nextFrameNumber (points to free frame) means
			that we have reached maximum frames and nextFrameNumber overflowed. Therefore, we replace that page with that
			frame number with the new page because it was the first entry in the page table.
		 */

		int pageToRemove = 0; // Contains the pageNumber that needs to be replaced.

		for (int i = 0; i < pageTable.length; i++) {
			if(pageTable[i] == this.nextFrameNumber){
				pageToRemove = i;
				break;
			}
		}
		unsetPage(pageToRemove);// Set it to -1 (invalidBit).
		this.numberOfPageFaults++; // Increment pageFaults as we have -1 in the page table.
		updatePageTable(nextFrameNumber, pageNumber); // Set the current pageNumber to nextFrameNumber position.
		incrementFrameNumber(); // Increment frameNumber and prevent overflow.

	}

	private void handlePageFaultLRU(int pageNumber){

		/*
			Lifetime = How "long" a page is in a frame.

			Everytime we add a page to a frame, we increment the "lifetime" of all frames by 1. From this, we can find
			the Least Recently Used frame by finding out the frame with the highest lifetime. When we replace or add a page,
			the associated frame's lifetime is reset. 	
		 */

		int frame = getLruFrame(frameOccurrences); // Frame with the highest lifetime.
		int pageToRemove  = 0;

		for (int i = 0; i < pageTable.length; i++) {
			if(pageTable[i] == frame){ // Find the page with the highest lifetime in a frame.
				pageToRemove = i;
			}
		}

		unsetPage(pageToRemove); // Remove the page.
		this.numberOfPageFaults++; // Increment pageFaults as we have -1 in pageTable.
		updatePageTable(frame, pageNumber); // Replace page.

	}


	private void updatePageTable(int frameNumber, int pageNumber){
		this.pageTable[pageNumber] = frameNumber;

	}

	private void incrementFrameNumber(){
		this.nextFrameNumber++;

		if(this.nextFrameNumber == this.numberOfFrames){
			this.nextFrameNumber = 0;
		}
	}

	private void unsetPage(int pageNumber){
		this.pageTable[pageNumber] = invalidBit;
	}

	private int getLruFrame(int[] frames){ // Returns an index containing the highest value within the array.
		int temp = 0;
		int frame = 0;

		for (int i = 0; i < frames.length; i++) {
			if(frames[i] > temp){
				temp = frames[i];
				frame =  i;
			}
		}

		return frame;
	}

}
