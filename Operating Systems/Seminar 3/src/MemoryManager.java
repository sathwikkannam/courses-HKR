import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

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
	private boolean framesFull = false;

	public MemoryManager(int numberOfPages, int pageSize, int numberOfFrames, String pageFile,
			int pageReplacementAlgorithm) {

		this.numberOfPages = numberOfPages;
		this.pageSize = pageSize;
		this.numberOfFrames = numberOfFrames;
		myPageReplacementAlgorithm = pageReplacementAlgorithm;

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
		// Implement by student in task one
		int logicalAddressSpace = numberOfPages * pageSize; //pageSize == frameSize.
		int pageNumberBoundary = (int) ((Math.log(logicalAddressSpace) / Math.log(2)) - (Math.log(pageSize) / Math.log(2))); // Upper bits of logicalAddress.
		return logicalAddress >> pageNumberBoundary; // Return n (pageNumberBoundary) upper bits of logicalAddress.

	}

	private int getPageOffset(int logicalAddress) {
		// Implement by student in task one
		int offsetBoundary = (int) ((Math.log(pageSize) / Math.log(2))); // Lower bits of logicalAddress.
		return (logicalAddress  << -offsetBoundary >>> -offsetBoundary); // Return n (offsetBoundary) lower bits of logicalAddress.
	}

	private void pageFault(int pageNumber) {
		if (myPageReplacementAlgorithm == Seminar3.NO_PAGE_REPLACEMENT)
			handlePageFault(pageNumber);

		if (myPageReplacementAlgorithm == Seminar3.FIFO_PAGE_REPLACEMENT) {
			try {
				handlePageFaultFIFO(pageNumber);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (myPageReplacementAlgorithm == Seminar3.LRU_PAGE_REPLACEMENT){
			try {
				handlePageFaultLRU(pageNumber);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	private void handlePageFaultFIFO(int pageNumber) throws IOException {
		for(int i = 0; i < pageTable.length; i++) {
			if(pageTable[i] == nextFrameNumber) {
				pageTable[i] = invalidBit;
			}
		}

		pageFile.seek((long) pageNumber * pageSize);
		for (int offset = 0; offset < numberOfFrames; offset++) {
			if(!pagePresent(pageNumber)){
				numberOfPageFaults++;
				addToRAM(nextFrameNumber * pageSize + offset, pageFile.readByte());
				updatePageTable(nextFrameNumber, pageNumber);
				incrementFrameNumber(); // Points to next frame, if frame == numberOfFrames, then resets. Hence, replaces first frame.
			}
		}

	}

	private void handlePageFaultLRU(int pageNumber) throws IOException {
		// Implement by student in task three
		// this solution allows different size of physical and logical memory
		// page replacement using LRU
		// Note depending on your solution, you might need to change parts of the
		// supplied code, this is allowed.
	}


	private boolean pagePresent(int page){
		return pageTable[page] != invalidBit;
	}


	private void updatePageTable(int frameNumber, int pageNumber){
		pageTable[pageNumber] = frameNumber;

	}

	private void incrementFrameNumber(){
		nextFrameNumber++;

		if(nextFrameNumber == numberOfFrames){
			nextFrameNumber = 0;
			framesFull = true;
		}
	}


	private void addToRAM(int physicalAddress, byte data){
		RAM[physicalAddress] = data;
	}
}
