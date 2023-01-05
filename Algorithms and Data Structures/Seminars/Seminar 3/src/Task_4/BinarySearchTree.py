import AVL
import RedBlackTree
from Common import Node


def find_min_right(node):
    """
    Find minimum node in the node's subtree which can't have a left child.
    :param node: A node.
    :return: Node with a minimum key.
    """
    minimum_node = None
    current_node = node

    while current_node.get_right_node() is None:
        if current_node.get_right_node() < minimum_node or None and not current_node.get_left_node():
            minimum_node = current_node.get_right_node()

        current_node = current_node.get_right_node()

    return minimum_node


class BinarySearchTree:
    AVL, RBT = "AVL", "RBT"  # AVL Tree and Red Black Tree
    PRE_ORDER, IN_ORDER, POST_ORDER, LEVEL_ORDER = 1, 2, 3, 4

    def __init__(self, mode=None):
        self.__root = None
        self.__mode = mode
        self.__bst_handler = AVL if mode is self.AVL else RedBlackTree if mode is self.RBT else None

    def get_mode(self):
        return self.__mode if self.__mode else "BST"

    def insert(self, node: Node, relative_root: Node = None):
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

        if node.get_key() is relative_root.get_key():
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

        if self.__mode:
            node.set_color(Node.RED)
            self.__bst_handler.insert_handler(node, relative_root if self.__mode is self.AVL else self.__root)

    def delete(self, node: Node):
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
            parent.set_left_node(minimum) if parent.get_left_node() is to_delete else parent.set_right_node(minimum)

    def contains(self, node: Node, current_node: Node = None, mode=1):
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

        if node.get_key() is current_node.get_key():
            return current_node if mode == 1 else True

        elif node.get_key() < current_node.get_key() and current_node.get_left_node():
            return self.contains(node, current_node.get_left_node())

        elif node.get_key() > current_node.get_key() and current_node.get_right_node():
            return self.contains(node, current_node.get_right_node())

        return None if mode == 1 else False

    def __str__(self, current_node: Node = None, internal_call=False, mode=None):
        """
        :param current_node: Initially, the node is root, then its children.
        :param internal_call: A boolean if the function is being recursively called.
        :param mode: To indicate which order to print.
        """
        current_node = self.__root if not current_node and not internal_call else current_node

        if not current_node:
            return

        if mode is self.IN_ORDER or not mode:  # Default case: Prints in order traversal
            self.__str__(current_node.get_left_node(), True, mode)
            print(current_node.get_key(), end=" ")
            self.__str__(current_node.get_right_node(), True, mode)

        elif mode is self.PRE_ORDER:
            print(current_node.get_key(), end=" ")
            self.__str__(current_node.get_left_node(), True, mode)
            self.__str__(current_node.get_right_node(), True, mode)

        elif mode is self.POST_ORDER:
            self.__str__(current_node.get_left_node(), True, mode)
            self.__str__(current_node.get_right_node(), True, mode)
            print(current_node.get_key(), end=" ")

        elif mode is self.LEVEL_ORDER:
            queue = [current_node]

            while len(queue) != 0:
                node = queue.pop()
                print(node.get_key(), end=" ")

                if node.get_left_node():
                    queue.append(node.get_left_node())
                if node.get_right_node():
                    queue.append(node.get_right_node())

            return
