import sys


def main():
    try:
        result_read = read_lines(sys.argv[1])
    except FileNotFoundError:
        print("An error occurred while trying to read the file.")
    else:
        result_parse_cars = parse_cars(result_read)
        distance_input = int(input("How far do you want to drive (kilometers)? "))
        result_percent = calculate_percentage(distance_input, result_parse_cars)
        display_result(result_percent)


def read_lines(filename):
    with open(filename, "r") as f_open:
        car_list = [LINE.strip("\n") for LINE in f_open]  # list of cars and range

    return car_list


def parse_cars(list_of_strings):
    seperated_list = []  # tuples of cars and int values of range
    for CONVERT_INT in list_of_strings:
        seperated_string = CONVERT_INT.split(":")
        seperated_list.append((seperated_string[0], int(seperated_string[1])))

    return seperated_list


def calculate_percentage(distance, cars):
    percent_list = []  # tuples of cars and percentage
    for car, car_range in cars:
        percentage = float(((distance/car_range)*100))
        percent_list.append((car, percentage))

    return percent_list


def display_result(percentages):
    print("""
To drive the specified distance would correspond to this many
percent of each cars specified max range.""")

    for car, range_ratio in percentages:
        range_percent = round(range_ratio)
        if range_percent <= 100:
            print(f"{car:<37}-->  {range_percent}%")
        if range_percent > 100:
            print(f"{car:<37}-->  Distance exceeds max range ({range_percent}%)")


if __name__ == '__main__':
    main()
