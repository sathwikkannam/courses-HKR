import random

import AVL
import RedBlackTree
from Task_4.Common.Node import Node


def find_min_right(node):
    """
    Basically, a linkedlist traversal until a node's right_node is null.
    :param node: A node.
    :return: Node with a minimum key.
    """
    minimum_node = None
    current_node = node

    while current_node.get_right_node() is None:
        if current_node.get_right_node() < minimum_node or None:
            minimum_node = current_node.get_right_node()

        current_node = current_node.get_right_node()

    return minimum_node


class BinarySearchTree:
    AVL = 1  # AVL Tree
    RBT = 2  # Red Black Tree

    def __init__(self, mode):
        self.__root = None
        self.__mode = mode
        self.__bst_handler = AVL if mode == self.AVL else RedBlackTree if mode == self.RBT else None

    def insert(self, node, relative_root=None):
        """
        :param node: A node object to be inserted
        :param relative_root: The root note relative to 'node'
        :return: None
        """
        # Root has no parent
        if self.__root is None:
            self.__root = node
            self.__root.set_color(Node.BLACK)
            return

        if relative_root is None:
            relative_root = self.__root

        if node.get_key() == relative_root.get_key():
            return

        if node.get_key() < relative_root.get_key():
            if relative_root.get_left_node():
                return self.insert(node, relative_root.get_left_node())

            relative_root.set_left_node(node)
            relative_root.get_left_node().set_parent(relative_root)

        elif node.get_key() > relative_root.get_key():
            if relative_root.get_right_node():
                return self.insert(node, relative_root.get_right_node())

            relative_root.set_right_node(node)
            relative_root.get_right_node().set_parent(relative_root)

        node.set_color(Node.RED)

        if self.__mode:
            self.__bst_handler.insert_handler(node=node, relative_root=relative_root)

    def delete(self, node):
        """
        :param node: A node to delete
        :return: None
        """
        if node == self.__root:
            self.__root = None
            return

        to_delete = self.contains(node, mode=1)
        parent = to_delete.get_parent()

        # No children
        if not (to_delete.get_left_node() and to_delete.get_right_node()):
            parent.set_left_node(None) if to_delete.get_key() < parent.get_key() else parent.set_right_node(None)

        # Only one left child
        elif to_delete.get_left_node() and not to_delete.get_right_node():
            parent.set_left_node(to_delete.get_left_node())

        # Only one right child
        elif not to_delete.get_left_node() and to_delete.get_right_node():
            parent.set_right_node(to_delete.get_right_node())

        # Both left and right children
        elif to_delete.get_left_node() and to_delete.get_right_node():
            minimum = find_min_right(to_delete)
            self.delete(minimum)
            parent.set_left_node(minimum) if parent.get_left_node() == to_delete else parent.set_right_node(minimum)

        if self.__mode:
            self.__bst_handler.delete_handler(parent)

    def contains(self, node, current_node=None, mode=1):
        """
        Traverses in one direction of the tree based on the node's key.
        :param node: The target node
        :param current_node: Same as in insert()
        :param mode: If mode == 1, it returns the node or None.
                     Else, True or False
        :return: A node or boolean depending on mode.
        """
        if self.__root is None:
            return False

        if current_node is None:
            current_node = self.__root

        if node.get_key() == current_node.get_key():
            return current_node if mode == 1 else True

        elif node.get_key() < current_node.get_key() and current_node.get_left_node():
            return self.contains(node, current_node.get_left_node())

        elif node.get_key() > current_node.get_key() and current_node.get_right_node():
            return self.contains(node, current_node.get_right_node())

        return None if mode == 1 else False

    def __str__(self, current_node=None, internal_call=False):
        """
        Printed in sorted order (In order).
        Recursively prints out the node's left and right nodes until it has no children.
        :param current_node: Initially, the node is root, then its children.
        :param internal_call: A boolean if the function is being recursively called.
        """
        if not current_node and not internal_call:
            current_node = self.__root

        if current_node:
            self.__str__(current_node.get_left_node(), True)
            print(current_node.get_key())
            self.__str__(current_node.get_right_node(), True)


if __name__ == '__main__':
    tree = BinarySearchTree(BinarySearchTree.AVL)

    for _ in range(20):
        tree.insert(Node(random.randrange(1, 100)))
    tree.__str__()
