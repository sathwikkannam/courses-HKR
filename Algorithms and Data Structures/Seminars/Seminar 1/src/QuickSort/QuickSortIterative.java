package QuickSort;

import Common.Utilities;

public class QuickSortIterative {

    static class Range{
        public int leftPointer;
        public int rightPointer;

        public Range(int leftPointer, int rightPointer) {
            this.leftPointer = leftPointer;
            this.rightPointer = rightPointer;

        }
    }

    private static int partition(int[] numbers, int lowerBound, int upperBound){

        int leftPointer = lowerBound - 1; // We decrement left pointer to prevent indexOutOfBoundsException

        for (int rightPointer = lowerBound; rightPointer < upperBound; rightPointer++) {
            if (numbers[rightPointer] <= numbers[upperBound]) {

                // We swap the elements i and j, so elements before i are smaller and elements between leftPointer and rightPointer are greater.
                // If the element is equal, then it can either be swapped and not.
                Utilities.swap(numbers, ++leftPointer, rightPointer);
            }
        }
        Utilities.swap(numbers, leftPointer + 1, upperBound); // Swap the pivot with leftPointer, so the pivot is in its final position.

        return leftPointer + 1;

    }


    //https://www.geeksforgeeks.org/iterative-quick-sort/
    public static void sort(int[] numbers, int lowerBound, int upperBound, String method) {

        int levelsOfPartitoning = -1;
        Range[] bounds = new Range[upperBound + 1];

        bounds[++levelsOfPartitoning] = new Range(lowerBound, upperBound);

        while (levelsOfPartitoning >= 0) {

            Range range = bounds[levelsOfPartitoning--];

            switch (method) {
                case "Median" -> Pivot.swapMedianOfThree(numbers, range.leftPointer, range.rightPointer);
                case "First" -> Pivot.swapFirst(numbers, range.leftPointer, range.rightPointer);
                case "Random" -> Pivot.swapRandom(numbers, range.leftPointer, range.rightPointer);
            }

            int pivot_index = partition(numbers, range.leftPointer, range.rightPointer);


            //right subset of the array.
            if (pivot_index + 1 < range.rightPointer) { // The next partition must be smaller than previous partition.
                bounds[++levelsOfPartitoning] = new Range(pivot_index + 1, range.rightPointer);
            }

            //left subset of the array.
            if (pivot_index - 1 >= range.leftPointer) {
                bounds[++levelsOfPartitoning] = new Range(range.leftPointer, pivot_index - 1);
            }

        }
    }


}

