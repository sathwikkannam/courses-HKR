package InsertionSort;

import Common.Utilities;

public class InsertionSortRecursive {


    /**
     * @param numbers A list of random numbers
     * @param upperBound An index to sort until.
     */
    public static void sort(int[] numbers, int upperBound){
        // If the size of the array is 1.
        if(upperBound <= 1){
            return;
        }

        sort(numbers, upperBound - 1);

        int currentElement = numbers[upperBound - 1]; // The element being compared to its previous elements.

        for (int i = upperBound - 2; i >=0 && numbers[i] > currentElement; i--) {
            Utilities.swap(numbers, i + 1, i); // Swap the elements rather than shifting.
        }

    }
}
