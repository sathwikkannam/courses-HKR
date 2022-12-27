import time
import os

from Task_4.BinarySearchTree import BinarySearchTree
from Common import Node, get_numbers

file = ""


# Running time test cases for Binary Search tree.
def main():
    global file
    os.chdir("..")
    file = os.path.join(os.getcwd(), "Seminar 1 - File with random numbers.txt")

    averages = []
    trees = [BinarySearchTree(), BinarySearchTree(BinarySearchTree.AVL), BinarySearchTree(BinarySearchTree.RBT)]

    for tree in trees:
        for i in range(100000, 10000001, 100000):
            numbers = get_numbers(i)
            for _ in range(15):
                start = time.time_ns()
                for num in numbers:
                    tree.insert(Node(num))
                end = time.time_ns()
                averages.append(end - start)
            print(f"INPUTS: {i}, Tree: {tree.get_mode() if tree.get_mode() else 'BST'}, Average time in nanoseconds: {round(sum(averages) / len(averages))}")
            averages.clear()


if __name__ == '__main__':
    main()
