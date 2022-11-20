package Common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utilities {

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

    public static Double getAverageTime(ArrayList<Double> time){
        Double sum = (double) 0;
        for (Double t : time) {
            sum += t;
        }

        return sum/time.size();

    }

    public static String printArray(ArrayList<Double> time){
        StringBuilder te = new StringBuilder("[ ");
        for (Double aLong : time) {
            te.append(aLong.toString()).append(", ");
        }

        te = new StringBuilder(te + "]");

        return te.toString();
    }

    public static void swap(int[] numbers, int a, int b){
        int temp = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = temp;
    }


}
