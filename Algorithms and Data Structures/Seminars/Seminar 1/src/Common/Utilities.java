package Common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static void swap(int[] numbers, int a, int b){
        int temp = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = temp;
    }


}
