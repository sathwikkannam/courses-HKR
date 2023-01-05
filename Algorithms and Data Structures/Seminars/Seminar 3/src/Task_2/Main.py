from Hashing import Hashing
from Task_2.Pair import Pair


def main():
    test_case = [int(x)
                 for x in input("Enter elements separated by ',' (using the index of element as a key):").split(",")]
    size = int(input("Size: "))

    modes = [Hashing.CHAINING, Hashing.LINEAR_PROBING, Hashing.QUADRATIC_PROBING]

    for mode in modes:
        hashing = Hashing(size, mode)
        for i, item in enumerate(test_case):
            hashing.insert(Pair(i, item))

        print(f"{hashing.get_mode()}: \n{hashing.__str__()}")


if __name__ == '__main__':
    main()
