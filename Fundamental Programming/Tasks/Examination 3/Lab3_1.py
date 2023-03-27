import pickle
import sys


def main():
    try:
        dict1 = read_file(sys.argv[1])
        dict2 = read_file(sys.argv[2])
    except FileNotFoundError:
        print("Error: The files given as arguments are not valid.")
    else:
        new_dict1 = map_to_int(dict1)
        new_dict2 = map_to_int(dict2)
        faulty = find_faulty(new_dict1, new_dict2, 2)
        display_warnings(faulty)


def read_file(filename):
    with open(filename, "rb") as f_open:
        return pickle.load(f_open)


def map_to_int(measurements):
    return {room: int(measurements.get(room).strip("°")) for room in measurements}


def find_faulty(primary, secondary, threshold):
    return set(first_room for first_room, second_room in zip(primary, secondary)
               if first_room == second_room and abs(primary.get(first_room) - secondary.get(second_room)) > threshold)


def display_warnings(faulty_sensors):
    print("""
Analyzis of the provided files is complete.
-------------------------------------------

The following sensors differ more than 2°:""")
    for rooms in faulty_sensors:
        print(rooms)


if __name__ == '__main__':
    main()
