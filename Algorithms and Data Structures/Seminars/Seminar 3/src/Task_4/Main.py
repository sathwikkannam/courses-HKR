import os
import time

from Task_4.BinarySearchTree import BinarySearchTree
from Common import Node, get_numbers


# Running time test cases for Binary Search tree.
def main():
    os.chdir("..")
    averages = []
    trees = [BinarySearchTree(), BinarySearchTree(BinarySearchTree.AVL), BinarySearchTree(BinarySearchTree.RBT)]

    for tree in trees:
        for i in range(100000, 10000001, 100000):
            numbers = get_numbers(i)
            start = time.time_ns()
            for num in numbers:
                tree.insert(Node(num))
            end = time.time_ns()
            averages.append(end - start)
            print(
                f"INPUTS: {i}, Tree: {tree.get_mode()}, Average time in nanoseconds: {round(sum(averages) / len(averages))}")
            averages.clear()


if __name__ == '__main__':
    main()
