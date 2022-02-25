import sys

MESSAGES = []  # tuples of each message
SECOND_ARG = sys.argv[1]


def main():
    try:
        result_messages = read_file(SECOND_ARG)

    except FileNotFoundError:
        print(f"Error: The file '{SECOND_ARG}' could not be found.")

    else:
        name_input = str(input("Enter a name to search for: "))
        for LIST_NAME, LIST_MSG in result_messages:
            if LIST_NAME == name_input:
                display_entry(LIST_NAME, LIST_MSG)


def read_file(filename):
    with open(filename, "r") as f_open:
        for LINE in f_open:
            line1 = LINE.strip("\n")
            line2 = f_open.__next__().strip("\n")
            MESSAGES.append((line1, line2))

    return MESSAGES


def display_entry(name, message):
    print(f"[{name}] --> {message}")


if __name__ == '__main__':
    main()
