import sys


def main():
    try:
        list1 = read_file(sys.argv[1])
        list2 = read_file(sys.argv[2])
    except FileNotFoundError:
        print("Error! The file(s) couldn't be found.")
    else:
        filter_list1 = filter_odd_or_even(list1, True)
        filter_list2 = filter_odd_or_even(list2, False)
        reversed_bubble_sort(filter_list1 + filter_list2)
        print(reversed_bubble_sort(filter_list1 + filter_list2))


def read_file(filename):
    all_numbers = []

    with open(filename, "r") as file:
        for line in file:
            for number in line.strip("\n").split():
                all_numbers.append(int(number.strip(".")) if number.__contains__(".") else int(number))
    return all_numbers


def filter_odd_or_even(numbers, odd):
    filter_list = []
    if odd:
        filter_list.extend(odd_num for odd_num in numbers if odd_num % 2 != 0)
    elif not odd:
        filter_list.extend(even_num for even_num in numbers if even_num % 2 == 0)

    return filter_list


def reversed_bubble_sort(numbers):
    length = len(numbers)
    for loops in range(length):
        for i in range(length-1):
            if numbers[i] < numbers[i+1]:
                numbers[i], numbers[i+1] = numbers[i+1], numbers[i]


if __name__ == '__main__':
    main()
