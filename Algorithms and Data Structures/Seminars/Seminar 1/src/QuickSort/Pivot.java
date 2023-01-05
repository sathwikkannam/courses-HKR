package QuickSort;

import Common.Utilities;

import java.util.Random;


public class Pivot {

    /**
     * @param numbers A list of numbers
     * @param lowerBound Lowest index to iterate from
     * @param upperBound Highest index to iterate to
     */
    private static void swapMedianOfThree(int[] numbers, int lowerBound, int upperBound){
        int indexToSwap;

        int first = numbers[lowerBound];
        int center_index = (upperBound - lowerBound)/2; // Not center for arrays with odd elements.
        int center = numbers[center_index];
        int last = numbers[upperBound];

        if (center < first && first < last || last < first && first < center) {
            indexToSwap = lowerBound;

        }else if (first < center && center < last || last < center && center < first) {
            indexToSwap = center_index;
        }else{
            indexToSwap = upperBound;

        }

        Utilities.swap(numbers, indexToSwap, upperBound);

    }

    /** Swap the first element with the last element
     * @param numbers A list of numbers
     * @param lowerBound Lowest index to iterate from
     * @param upperBound Highest index to iterate to
     */
    private static void swapFirst(int[] numbers, int lowerBound, int upperBound){
        Utilities.swap(numbers, lowerBound, upperBound);
    }

    /** Swap random element with last element
     * @param numbers A list of numbers
     * @param lowerBound Lowest index to iterate from
     * @param upperBound Highest index to iterate to
     */
    private static void swapRandom(int[] numbers, int lowerBound, int upperBound){
        if(lowerBound != upperBound){
            Utilities.swap(numbers, new Random().nextInt(lowerBound, upperBound), upperBound);
        }
    }

    /** Selects which function to run based on the method.
     * @param numbers A list of numbers
     * @param lowerBound Lowest index to iterate from
     * @param upperBound Highest index to iterate to
     * @param method A pivot selection strategy.
     */
    public static void swapPivot(int[] numbers, int lowerBound, int upperBound, String method){
        switch (method) {
            case "Median" -> swapMedianOfThree(numbers, lowerBound, upperBound);
            case "First" -> swapFirst(numbers, lowerBound, upperBound);
            case "Random" -> swapRandom(numbers, lowerBound, upperBound);
        }
    }
}
