package Task_2;

public class BinarySearch {

    private static boolean partition(int[] numbers, int lowerBound, int upperBound, int target) {

        if(upperBound < 1){ // If the given range has 1 element.
            return false;
        }

        int center = (upperBound + lowerBound) / 2;

        if(numbers[center] == target){ // The center always (if target in array)contains the target if it's in the array.
            return true;

        }
        if(numbers[center] < target){ // If target is bigger than center, then it must be after the center element.
            return partition(numbers, center + 1, upperBound, target);
        }else if(numbers[center] > target){ // If the target is smaller than center, then it must be before the center element.
            return partition(numbers, lowerBound, center - 1 , target);
        }

        return false;

    }


    public static boolean search(int[] numbers, int target){
        return partition(numbers, 0, numbers.length-1, target);
    }
}
