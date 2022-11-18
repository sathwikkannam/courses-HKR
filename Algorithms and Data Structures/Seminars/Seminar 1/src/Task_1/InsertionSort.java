package Task_1;

public class InsertionSort {



    public static void insertionSort(int[] numbers){
        for (int i = 1; i < numbers.length; i++){
            for (int j = i - 1; j >=0; j--) { // For element in list, we go check through all elements prior to that element.
                if(numbers[j +1] < numbers[j]){ // If the element is smaller than element before, we swap.
                    QuickSort.swap(numbers, j+1, j);
                }
            }
        }
    }
}
