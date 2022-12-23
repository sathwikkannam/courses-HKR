puzzle = [['t', 'h', 'i', 's'],
          ['w', 'a', 't', 's'],
          ['o', 'a', 'h', 'g'],
          ['f', 'g', 'd', 't']]

word_list = ["fat", "that", "this"]

rows = len(puzzle[0])
columns = len(puzzle)


def main():
    for word in word_list:
        ordered_pair(word)


def ordered_pair(word):
    for x, y in puzzle: search(x, y, word)


def search(x, y, word):
    if puzzle[x][y] is not word[0]:
        return False

    matched = 0  # Stores how many characters have been matched
    row_pointer = 0
    column_pointer = 0

    # We already checked the first character, so we start from 1.
    for i in range(1, len(word)):
        if not out_of_bounds(row_pointer, column_pointer) and is_character(puzzle[row_pointer][column_pointer], word,
                                                                           i):
            row_pointer += 1
            column_pointer += 1
            matched += 1
        else:
            break

    return matched == len(word) - 1


def out_of_bounds(x, y):
    return x in range(0, rows) and y in range(0, columns)


def is_character(character, word, at):
    return character == word[at]


if __name__ == '__main__':
    main()
