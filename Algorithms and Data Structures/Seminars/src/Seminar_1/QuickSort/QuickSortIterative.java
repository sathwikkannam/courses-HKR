package Seminar_1.QuickSort;

import Seminar_1.Common.Utilities;

public class QuickSortIterative {

    static class Range {
        public int leftPointer;
        public int rightPointer;

        public Range(int leftPointer, int rightPointer) {
            this.leftPointer = leftPointer;
            this.rightPointer = rightPointer;

        }
    }


    /**
     * Iteratively partition n iterations until bounds is empty.
     *
     * @param numbers    A list of numbers.
     * @param lowerBound Lowest index to iterate from.
     * @param upperBound Highest index to iterate to.
     * @param method     A pivot selection strategy.
     */
    public static void sort(int[] numbers, int lowerBound, int upperBound, String method) {

        int i = -1; // indicates if the "bounds" array is empty.
        Range[] bounds = new Range[upperBound + 1];

        bounds[++i] = new Range(lowerBound, upperBound);

        while (i >= 0) {

            Range range = bounds[i--];

            Pivot.swapPivot(numbers, range.leftPointer, range.rightPointer, method);
            int pivot_index = Utilities.partition(numbers, range.leftPointer, range.rightPointer);


            //right subset of the array.
            if (pivot_index + 1 < range.rightPointer) { // The next partition must be smaller than the previous partition.
                bounds[++i] = new Range(pivot_index + 1, range.rightPointer);
            }

            //left subset of the array.
            if (pivot_index - 1 >= range.leftPointer) {
                bounds[++i] = new Range(range.leftPointer, pivot_index - 1);
            }

        }
    }


}

