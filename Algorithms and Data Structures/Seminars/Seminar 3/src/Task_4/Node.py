class Node:

    def __init__(self, key):
        self.__key = key
        self.__left_node = None
        self.__right_node = None
        self.__parent = None
        self.height = 1  # For AVL tree

    def set_left_node(self, node):
        self.__left_node = node

    def set_right_node(self, node):
        self.__right_node = node

    def get_left_node(self):
        return self.__left_node

    def get_right_node(self):
        return self.__right_node

    def set_parent(self, node):
        self.__parent = node

    def get_parent(self):
        return self.__parent

    def get_key(self):
        return self.__key
