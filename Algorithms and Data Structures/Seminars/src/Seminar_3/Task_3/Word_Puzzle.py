puzzle = [['t', 'h', 'i', 's'],
          ['w', 'a', 't', 's'],
          ['o', 'a', 'h', 'g'],
          ['f', 'g', 'd', 't']]

words = ["fat", "that", "this"]
rows = len(puzzle)
columns = len(puzzle[0])


def main():
    for word in words:
        for row in range(rows):
            for column in range(columns):
                if search(row, column, word):
                    print(f"{word} is present")


def search(row, column, word):
    """
    The 8 directions in 2D list: Horizontally Left, Horizontally Right, Vertically Up, Vertically Down and 4 ("X")
    Diagonal directions.
    :param row: Row in puzzle
    :param column: Colour in puzzle
    :param word: Word to search
    :return:
    """
    if not matches_char(row, column, word, 0):
        return False

    for x in range(1, -2, -1):
        for y in range(1, -2, -1):
            matched = 0
            row_dir, col_dir = row + x, column + y

            for i in range(1, len(word)):

                if in_bounds(row_dir, col_dir) and matches_char(row_dir, col_dir, word, i):
                    row_dir, col_dir = row_dir + x, col_dir + y  # Keep going in that direction.
                    matched += 1
                else:
                    break

            if matched is len(word) - 1:
                return True
    return False


def in_bounds(row, column):
    return row in range(rows) and column in range(columns)


def matches_char(row, column, word, at):
    return puzzle[row][column] is word[at]


if __name__ == '__main__':
    main()
