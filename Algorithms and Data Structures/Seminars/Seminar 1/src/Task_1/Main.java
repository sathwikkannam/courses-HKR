package Task_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Long> time = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        while(true){
            String method;
            int inputs;
            System.out.print("Method: ");
            Scanner e = new Scanner(System.in);
            method = e.next();
            System.out.print("Inputs: " );
            inputs = e.nextInt();


            Thread f = new Thread(() ->{
                for (int i = 0; i < 5; i++) {
                    QuickSort quickSort = new QuickSort(method);
                    long start = System.currentTimeMillis();
                    quickSort.quickSortRecursive(getNumbers(inputs), 0, inputs - 1);
                    long end = System.currentTimeMillis();
                    time.add(end - start);
                }

            });

            f.start();
            f.join();
            System.out.println("All times: ");
            printArray(time);
            System.out.println();
            System.out.println(getAverageTime(time) + " MS");

        }


//        for (int k : interval) {
//            for (String method : methods) {
//                for (int j = 0; j < 5; j++) {
//                    QuickSort quickSort = new QuickSort(method);
//                    long start = System.currentTimeMillis();
//                    quickSort.quickSortRecursive(getNumbers(k), 0, k - 1);
//                    long end = System.currentTimeMillis();
//                    time.add(end - start);
//                }
//
//                printArray(time);
//                System.out.println();
//                System.out.println("Inputs: " + k + " QuickSortRecursive with pivot as " + method + " took: " + getAverageTime(time) + " ms");
//                time.clear();
//            }
//
//
//        }

    }


    public static int[] getNumbers(int limit){
        int[] numbers = new int[limit];
        String line;
        int i = 0;

        try {
            BufferedReader myReader = new BufferedReader(new FileReader("src/Task_1/Seminar 1 - File with random numbers.txt"));
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