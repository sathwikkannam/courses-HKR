import time
import random

#input_sizes = [100, 1000, 10000, 100000, 1000000]
input_sizes = [10]

def main():
    numbers = get_numbers("Seminar 1 - File with random numbers.txt")

    for inputs in input_sizes:
        start = time.time()
        quick_sort(numbers[:inputs], 0, inputs)
        #quick_sort(numbers, inputs)
        end = time.time()
        print("Iterative method with ", inputs, " inputs took: ", end - start)


def get_numbers(filename):
    return [int(number) for number in open(filename, "r")]



def quick_sort(numbers, minimum_index, maximum_index):

    pivot = numbers[random.randint(minimum_index, maximum_index)]
    for i in range(minimum_index, maximum_index):
        if numbers[i] < pivot:
            
        elif numbers[i] > pivot:






def k(numbers, size):
    pass



def is_sorted(numbers):


if __name__ == '__main__':
    main()
