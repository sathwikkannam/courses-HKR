from Heap import *
import time


# Running time test cases for Binary heap.
def main():
    averages = []
    for i in range(100000, 1000000 + 1, 100000):
        numbers = get_numbers(i)

        for j in range(15):
            binary_heap = BinaryHeap(i)

            start = time.time_ns()
            for num in numbers:
                binary_heap.insert(num)
            end = time.time_ns()
            averages.append(end - start)

        print(f"INPUTS: {i}, Binary Heap, Average time in nanoseconds: {round(sum(averages) / len(averages))}")
        averages.clear()

        for j in range(15):
            start = time.time_ns()
            BinaryHeapLinearTime(numbers)
            end = time.time_ns()
            averages.append(end - start)

        print(f"INPUTS: {i}, Binary Heap Linear Time, Average time in nanoseconds: {round(sum(averages) / len(averages))}")
        averages.clear()


def get_numbers(limit):
    with open("Seminar 1 - File with random numbers.txt", "r") as f:
        return list(map(int, f.readlines()[:limit]))


if __name__ == '__main__':
    main()
