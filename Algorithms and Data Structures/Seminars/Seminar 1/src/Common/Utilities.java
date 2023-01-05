package Common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utilities {

    /**
     * @param limit A number indicating how to many random numbers to get from file.
     * @return A list of random numbers
     */
    public static int[] getNumbers(int limit){
        int[] numbers = new int[limit];
        String line;
        int i = 0;

        try {
            BufferedReader myReader = new BufferedReader(new FileReader("src/Seminar 1 - File with random numbers.txt"));
            while ((line = myReader.readLine()) != null && i < limit) {
                numbers[i++] = Integer.parseInt(line);
            }
            myReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return numbers;
    }

    public static void swap(int[] numbers, int a, int b){
        int temp = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = temp;
    }


    /**
     * Quicksort partition.
     * Moves all elements smaller than pivot to the left of pivot, and greater than pivot to the right of pivot.
     * @param numbers A list of numbers
     * @param lowerBound Lowest index to iterate from
     * @param upperBound Highest index to iterate to
     * @return An index of the pivot in its final position.
     */
    public static int partition(int[] numbers, int lowerBound, int upperBound){

        int leftPointer = lowerBound - 1; // We decrement left pointer to prevent indexOutOfBoundsException

        for (int rightPointer = lowerBound; rightPointer < upperBound; rightPointer++) {
            if (numbers[rightPointer] <= numbers[upperBound]) {

                // We swap the elements leftPointer and rightPointer,
                // so elements before i are smaller and elements between leftPointer and rightPointer are greater.
                // If the element is equal, then it can either be swapped and not.
                Utilities.swap(numbers, ++leftPointer, rightPointer);
            }
        }
        Utilities.swap(numbers, leftPointer + 1, upperBound); // Swap the pivot with leftPointer.
        // so the pivot is in its final position.

        return leftPointer + 1;

    }


}
