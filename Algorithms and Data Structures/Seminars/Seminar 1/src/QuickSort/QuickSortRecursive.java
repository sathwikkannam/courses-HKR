package QuickSort;

import Common.Utilities;

public class QuickSortRecursive {


    public static void sort(int[] numbers, int lowerBound, int upperBound, String method){
        if (lowerBound >= upperBound){ // Base condition: if upperBound - lowerBound <= 1, meaning a subset with one element.
            return;

        }

        switch(method){
            case "Median" -> Pivot.swapMedianOfThree(numbers, lowerBound, upperBound);
            case "First" -> Pivot.swapFirst(numbers,lowerBound, upperBound);
            case "Random" -> Pivot.swapRandom(numbers, lowerBound, upperBound);
        }

        int pivot_index = partition(numbers, lowerBound, upperBound);
        sort(numbers, lowerBound, pivot_index -1, method); // Sort elements left of pivot.
        sort(numbers, pivot_index + 1, upperBound, method); // Sort elements right of pivot.
    }


    //https://www.geeksforgeeks.org/java-program-for-quicksort/
    private static int partition(int[] numbers, int lowerBound, int upperBound){

        int leftPointer = lowerBound - 1; // We decrement left pointer to prevent indexOutOfBoundsException

        for (int rightPointer = lowerBound; rightPointer < upperBound; rightPointer++) {
            if (numbers[rightPointer] <= numbers[upperBound]) {

                // We swap the elements leftPointer and rightPointer, so elements before i are smaller and elements between leftPointer and rightPointer are greater.
                // If the element is equal, then it can either be swapped and not.
                Utilities.swap(numbers, ++leftPointer, rightPointer);
            }
        }
        Utilities.swap(numbers, leftPointer + 1, upperBound); // Swap the pivot with leftPointer, so the pivot is in its final position.

        return leftPointer + 1;

    }

}

