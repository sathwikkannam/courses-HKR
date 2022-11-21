import Common.Utilities;
import InsertionSort.InsertionSortIterative;
import InsertionSort.InsertionSortRecursive;
import QuickSort.QuickSortIterative;
import QuickSort.QuickSortRecursive;
import Task_2.BinarySearch;

import java.util.Scanner;


//TODO Increase the JVM stack size to prevent StackOverFlow Exception with "-Xss256m" option.
public class Main {

    public static void main(String[] args){

        int option;
        String pivot = null;
        int inputs;
        Scanner scanner = new Scanner(System.in);
        int target = 0;
        int[] numbers;
        double start;
        double end;
        boolean targetPresent = false;

        while(true){
            System.out.print("1. Quick sort recursive\n2. Quick sort iterative\n3. Insertion sort recursive\n4. Insertion sort iterative\n5. Binary search\n6. Exit");
            option = scanner.nextInt();

            switch (option){

                case 1, 2 -> {
                    System.out.print("Select pivot (Median, First, Random): ");
                    pivot = scanner.next();
                }
                case 3, 4 -> pivot = null;
                case 5 -> {
                    System.out.print("Target: ");
                    target = scanner.nextInt();
                }
                default -> {
                    return;
                }

            }

            System.out.print("Inputs (How many elements to binary search or sort): ");
            inputs = scanner.nextInt();
            numbers = Utilities.getNumbers(inputs);

            start = System.currentTimeMillis();
            switch (option){
                case 1 -> QuickSortRecursive.sort(numbers, 0, inputs - 1, pivot);
                case 2 -> QuickSortIterative.sort(numbers, 0, inputs - 1, pivot);
                case 3 -> InsertionSortRecursive.sort(numbers, inputs - 1);
                case 4 -> InsertionSortIterative.sort(numbers);
                case 5 -> targetPresent = BinarySearch.search(numbers, target);

            }
            end = System.currentTimeMillis();


            switch (option) {
                case 1, 2 -> System.out.printf("\n--------\nPivot: %s\nInputs: %d\n", pivot, inputs);
                case 3, 4 -> System.out.printf("\n--------\nInputs: %d\n",inputs);
                case 5 -> System.out.printf("\n--------\nSearch scope: %d\n, Target: %d\nIs target in list: %b\n", inputs, target, targetPresent);

            }

            System.out.printf("Running time: %.3f in milliseconds\n--------\n", end - start);

        }
    }
}
