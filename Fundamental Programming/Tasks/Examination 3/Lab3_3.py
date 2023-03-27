import pickle
import sys


def main():
    list_files = []
    bin_file = sys.argv[1]
    try:
        open(bin_file)
    except FileNotFoundError:
        print("Error: There was a problem with at least one of the files.")
    else:
        while True:
            display_menu()
            choice_input = int(input("\nEnter choice: "))
            if choice_input == 1:
                file_input = input("Enter a filename (include pull path): ")
                try:
                    open(file_input)
                except FileNotFoundError:
                    print("Error: There was a problem with at least one of the files.")
                else:
                    list_files.append(file_input)
            elif choice_input == 2:
                duplicate_set = cross_reference(list_files)
                names_list = map_numbers_to_names(duplicate_set, bin_file)
                display_suspects(names_list)
                break


def display_menu():
    print("""
1. Add file
2. Calculate""")


def cross_reference(files):
    phone_nums = []
    for file in files:
        with open(file, "r") as f_open:
            phone_nums.extend(line.strip() for line in f_open)

    return set(number for number in phone_nums if phone_nums.count(number) == len(files))


def map_numbers_to_names(numbers, filename):
    names = []
    with open(filename, "rb") as f_open:
        diction = pickle.load(f_open)
        for number in numbers:
            names.extend(diction.get(number) for suspect in diction if number == suspect)
            if number not in diction:
                names.append(f"Unknown ({number})")
    return names


def display_suspects(names):
    print("""
The following persons was present on all crime scenes:
------------------------------------------------------""")
    if len(names) == 0:
        print("No matches")
    else:
        for name in names:
            print(name)


if __name__ == '__main__':
    main()
