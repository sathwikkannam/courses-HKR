package Task_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Long> time = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String method;
        int inputs;
        int option;

        while(true) {
            System.out.print("1. Quick sort recursive\n2. Quick sort iterative\n3. Insertion sort\n4. Exit\n");
            option = scanner.nextInt();
            switch (option) {
                case 1, 2 -> {
                    System.out.print("Method: ");
                    method = scanner.next();
                }
                default -> {
                    return;
                }

            }

            System.out.print("Inputs: ");
            inputs = scanner.nextInt();


            for (int i = 0; i < 5; i++) {
                long start = System.currentTimeMillis();
                if (option == 3) {
                    InsertionSort.insertionSort(getNumbers(inputs));
                } else if (option == 1) {
                    QuickSort quickSort = new QuickSort(method);
                    quickSort.quickSortRecursive(getNumbers(inputs), 0, inputs - 1);
                } else {
                    QuickSort quickSort = new QuickSort(method);
                    quickSort.quickSortIterative(getNumbers(inputs));
                }
                long end = System.currentTimeMillis();
                time.add(end - start);
            }

            System.out.println("Running time for all 5 times");
            printArray(time);
            System.out.println("\nMethod: " + method + " Inputs: " + inputs + " took on average: " + getAverageTime(time));
            time.clear();
        }
    }


    public static int[] getNumbers(int limit){
        int[] numbers = new int[limit];
        String line;
        int i = 0;

        try {
            BufferedReader myReader = new BufferedReader(new FileReader("src/Seminar 1 - File with random numbers.txt"));
            while ((line = myReader.readLine()) != null && i < limit) {
                numbers[i++] = Integer.parseInt(line);
            }
            myReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return numbers;
    }


    public static long getAverageTime(ArrayList<Long> time){
        long sum = 0;
        for (Long t : time) {
            sum += t;
        }

        return sum/time.size();

    }

    private static void printArray(ArrayList<Long> time){
        for (Long aLong : time) {
            System.out.print(aLong + " ");
        }
    }
}