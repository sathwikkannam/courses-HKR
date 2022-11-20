package InsertionSort;

import Common.Utilities;

public class InsertionSortIterative {



    public static void sort(int[] numbers){
        for (int i = 1; i < numbers.length; i++){
            for (int j = i - 1; j >=0 && numbers[j + 1] < numbers[j]; j--) { // For element in list, we go check through all elements prior to that element.
                Utilities.swap(numbers, j+1, j);
            }
        }
    }
}
