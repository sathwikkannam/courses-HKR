from Hashing import Hashing


def main():
    test_case = [int(x) for x in input("Enter elements separated by ',':").split(",")]
    size = int(input("Size: "))

    modes = [Hashing.CHAINING, Hashing.LINEAR_PROBING, Hashing.QUADRATIC_PROBING]

    for mode in modes:
        hashing = Hashing(size, mode)
        for item in test_case:
            hashing.insert(item)

        print(f"{mode=}: \n{hashing.__str__()}")


if __name__ == '__main__':
    main()
