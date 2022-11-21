package InsertionSort;

public class InsertionSortIterative {



    public static void sort(int[] numbers){
        int temp;
        int j;

        for (int i = 1; i < numbers.length; i++){
            temp = numbers[i];
            for (j = i - 1; j >= 0 && numbers[j] > temp; j--) { // For element in list, we go check through all elements prior to that element.
                numbers[j + 1] = numbers[j];
            }
            numbers[j + 1] = temp;
        }
    }
}
