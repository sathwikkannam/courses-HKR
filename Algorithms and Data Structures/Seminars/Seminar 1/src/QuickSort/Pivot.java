package QuickSort;

import Common.Utilities;

import java.util.Random;


public class Pivot {

    public static void swapMedianOfThree(int[] numbers, int lowerBound, int upperBound){
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

    public static void swapFirst(int[] numbers, int lowerBound, int upperBound){
        Utilities.swap(numbers, lowerBound, upperBound); //Swap the first element with the last element
    }

    public static void swapRandom(int[] numbers, int lowerBound, int upperBound){
        if(lowerBound != upperBound){
            Utilities.swap(numbers, new Random().nextInt(lowerBound, upperBound), upperBound); //Swap random element with last element
        }
    }
}
