import os
import time

from Task_4.BinarySearchTree import BinarySearchTree
from Common import Node, get_numbers


# Running time test cases for Binary Search tree.
def main():
    os.chdir("..")  # To access 'Seminar 1 - File with random numbers' file.

    inputs_range = int(input("Enter input size: "))
    mode = int(input("\n1. Standard\n2. AVL\n3. Red Black Tree: "))
    print_order = int(input("\n1. PRE-ORDER\n2. IN-ORDER\n3. POST-ORDER\n4. LEVEL-ORDER: "))

    mode = None if mode == 1 else BinarySearchTree.AVL if mode == 2 else BinarySearchTree.RBT if mode == 3 else None

    tree = BinarySearchTree(mode)

    numbers = get_numbers(inputs_range)

    start = time.time_ns()
    for num in numbers:
        tree.insert(Node(num))
    runtime = time.time_ns() - start

    tree.__str__(mode=print_order)

    print(f"\nINPUTS: {inputs_range}, Tree: {tree.get_mode()}, Average time in nanoseconds: {abs(runtime)}")


if __name__ == '__main__':
    main()
