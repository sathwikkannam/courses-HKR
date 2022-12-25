class Node:
    BLACK = 1
    RED = 2

    def __init__(self, key, color=None):
        self.__key = key
        self.__left_node = None
        self.__right_node = None
        self.__parent = None
        self.__height = int(1)  # For AVL tree
        self.__color = color

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

    def get_height(self):
        return self.__height

    def set_height(self, height):
        self.__height = height

    def set_color(self, color):
        self.__color = color

    def get_color(self):
        return self.__color
