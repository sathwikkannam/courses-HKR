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

		if (pageTable[pageNumber] == -1) {
			pageFault(pageNumber);
		}

		int frame = pageTable[pageNumber];
		int physicalAddress = frame * pageSize + offset;
		byte data = RAM[physicalAddress];

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
		pageTable[pageNumber] = nextFrameNumber;
		nextFrameNumber++;
		numberOfPageFaults++;

	}

	private void handlePageFaultFIFO(int pageNumber){
		/*`
			If there is a page in the page table with the nextFrameNumber (points to free frame) means
			that we have reached maximum frames and nextFrameNumber overflowed. Therefore, we replace that page with that
			frame number with the new page because it was the first entry in the page table.
		 */

		int pageToRemove = 0; // Contains the pageNumber that needs to be replaced.

		for (int i = 0; i < pageTable.length; i++) {
			if(pageTable[i] == nextFrameNumber){
				pageToRemove = i;
				break;
			}
		}
		pageTable[pageToRemove] = invalidBit; // Set it to -1 (invalidBit).
		numberOfPageFaults++; // Increment pageFaults as we have -1 in the page table.
		updatePageTable(nextFrameNumber, pageNumber); // Set the current pageNumber to nextFrameNumber position.
		incrementFrameNumber(); // Increment frameNumber.

	}

	private void handlePageFaultLRU(int pageNumber){


	}


	private void updatePageTable(int frameNumber, int pageNumber){
		pageTable[pageNumber] = frameNumber;

	}

	private void incrementFrameNumber(){
		nextFrameNumber++;

		if(nextFrameNumber == numberOfFrames){
			nextFrameNumber = 0;
		}
	}

}
