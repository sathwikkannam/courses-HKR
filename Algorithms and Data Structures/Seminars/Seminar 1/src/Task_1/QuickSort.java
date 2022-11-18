package Task_1;

import java.util.Random;

class QuickSort {

    private final String method;

    public QuickSort(String method){
        this.method = method;
    }



    public static void swap(int[] numbers, int a, int b){
        int temp = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = temp;
    }


    public void quickSortRecursive(int[] numbers, int lowerBound, int upperBound){
        if(upperBound - lowerBound >= 1){
            swap(numbers, getPivotIndex(this.method, numbers, lowerBound, upperBound), upperBound); // Place the pivot at the end of the list.
            int pivot = upperBound;
            int leftPointer = lowerBound; // Travels from left to right, looks for a number >= pivot
            int rightPointer = upperBound; // Travels from right to left, looks for a number < pivot

            while(leftPointer < rightPointer){ // while left pointer and right pointer haven't crossed.


                while(numbers[leftPointer] <= numbers[pivot] && leftPointer <= upperBound - 1){
                    leftPointer++; // If the number is smaller than the pivot, move to next element to the right
                }

                while(numbers[rightPointer] > numbers[pivot] && rightPointer >= lowerBound){
                    rightPointer--; //If the number is larger than the pivot, move to next element to the left
                }


                if(leftPointer < rightPointer){ // This is when leftPointer found a large and rightPointer small number relative to pivot
                    swap(numbers, leftPointer, rightPointer); // Swap
                }
            }


            swap(numbers, leftPointer, pivot); // This is when leftPointer and rightPointer crossed, we swap pivot with leftPointer

            quickSortRecursive(numbers, lowerBound, leftPointer - 1); // Sort 0 to leftPointer - 1 or pivot - 1
            quickSortRecursive(numbers, leftPointer + 1, upperBound); // Sort leftPointer + 1 or pivot + 1 to end of the list
        }

    }


    public int getPivotIndex(String method, int[] numbers, int lower, int upper){
        switch (method){
            case "Median"->{
                int first = numbers[lower];
                int second_index = (numbers.length-1)/2;
                int second = numbers[second_index];
                int third = numbers[upper];

                if (second < first && first < third || third < first && first < second) {
                    return lower;
                }

                if (first < second && second < third || third < second && second < first) {
                    return second_index;
                }
                return upper;

            }case "Random"->{
                return new Random().nextInt(lower, upper);
            }case "Last"->{
                return upper;
            }case "First"->{
                return lower;
            }
        }

        return 0;
    }


    public void quickSortIterative(int[] numbers){


    }
}