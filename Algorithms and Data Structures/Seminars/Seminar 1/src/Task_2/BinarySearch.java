package Task_2;

public class BinarySearch {

    /**
     * @param numbers A list of numbers
     * @param lowerBound An index to start from
     * @param upperBound An index to start until
     * @param target A value to search
     * @return If the target present in numbers.
     */
    private static boolean partition(int[] numbers, int lowerBound, int upperBound, int target) {

        if(upperBound < 1){ // If the given range has 1 element.
            return false;
        }

        int center = (upperBound + lowerBound) / 2;

        if(numbers[center] == target){ // The center always (if target in the array)contains the target if it's in the array.
            return true;

        }
        if(numbers[center] < target){ // If target is bigger than the center, then it must be after the center element.
            return partition(numbers, center + 1, upperBound, target);
        }else if(numbers[center] > target){ // If the target is smaller than the center,
            // then it must be before the center element.
            return partition(numbers, lowerBound, center - 1 , target);
        }

        return false;

    }


    public static boolean search(int[] numbers, int target){
        return partition(numbers, 0, numbers.length-1, target);
    }
}
