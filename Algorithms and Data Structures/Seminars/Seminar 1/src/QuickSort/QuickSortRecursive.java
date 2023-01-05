package QuickSort;

import Common.Utilities;

public class QuickSortRecursive {


    /**
     * @param numbers A list of numbers.
     * @param lowerBound Lowest index to iterate from.
     * @param upperBound Highest index to iterate to.
     * @param method A pivot selection strategy.
     */
    public static void sort(int[] numbers, int lowerBound, int upperBound, String method){
        if (lowerBound >= upperBound){ // Base condition: if upperBound - lowerBound <= 1, meaning a subset with one element.
            return;

        }

        Pivot.swapPivot(numbers, lowerBound, upperBound, method);
        int pivot_index = Utilities.partition(numbers, lowerBound, upperBound);

        sort(numbers, lowerBound, pivot_index -1, method); // Sort elements left of pivot.
        sort(numbers, pivot_index + 1, upperBound, method); // Sort elements right of pivot.
    }


}

