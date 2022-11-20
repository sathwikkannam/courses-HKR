package InsertionSort;

public class InsertionSortRecursive {


    //https://www.geeksforgeeks.org/recursive-insertion-sort/
    public static void sort(int[] numbers, int upperBound){
        // If the given range of the array is 1, meaning the size of the array is 1.
        if (upperBound > 1){
            sort(numbers, upperBound - 1); // Sort the 0 to n - 1 elements to place the element in the correct position

            int currentElement = numbers[upperBound - 1]; // The element being compared to its previous elements.
            int pointer = upperBound - 2;


            // Shift the elements to the right if the numbers[pointer] is greater than the element.
            while (pointer >= 0 && numbers[pointer] > currentElement) {
                numbers[pointer+1] = numbers[pointer];
                pointer--;
            }
            numbers[pointer +1 ] = currentElement; // Place the element after shifting.
        }

    }
}
