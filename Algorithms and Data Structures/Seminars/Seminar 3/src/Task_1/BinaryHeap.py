def get_right_child_index(parent_index):
    return int((2 * parent_index) + 2)


def get_left_child_index(parent_index):
    return int((2 * parent_index) + 1)


def get_parent_index_of(child_index):
    return int((child_index - 1) / 2)


class BinaryHeap:

    def __init__(self, size):
        self.__heap = [0] * (size + 1)
        self.__index = 0

    def insert(self, key):
        """
        Add to the end of the heap.
        Then check from key -> root to heapify up.
        :param key: The value of a node
        """
        self.__heap[self.__index] = key
        self.__index += 1
        self.__min_heapify(self.__index - 1)

    def __min_heapify(self, last_child):
        """
        Check from bottom node to the root.
        If the child has a parent and the parent's key is > last node: swap them,
        Then jump to its parent node and repeat until a node has no parent.
        :param last_child: The last inserted node
        :return: None
        """
        if not self.__has_parent(last_child):
            return

        if (self.__get_parent_of(last_child)) > self.__heap[last_child]:
            self.__swap(get_parent_index_of(last_child), last_child)
            self.__min_heapify(get_parent_index_of(last_child))

    def __get_parent_of(self, child_index):
        """
        :param child_index: An index of a child node.
        :return: Parent node
        """
        return self.__heap[get_parent_index_of(child_index)]

    def __has_parent(self, child_index):
        """
        :param child_index: An index of a child node.
        :return: A boolean if a child node has parent
        """
        return get_parent_index_of(child_index) >= 0

    def __swap(self, a, b):
        self.__heap[a], self.__heap[b] = self.__heap[b], self.__heap[a]

    def __str__(self):
        """
        Change the order to fit a traversal order
        :return: None
        """
        return "".join(
            f"Parent: {self.__heap[i]} \t "
            f"Left Child: {self.__heap[get_left_child_index(i)]} \t "
            f"Right child: {self.__heap[get_right_child_index(i)]}\n"

            for i in range(0, int(len(self.__heap) / 2)))


class BinaryHeapLinearTime(BinaryHeap):
    pass
