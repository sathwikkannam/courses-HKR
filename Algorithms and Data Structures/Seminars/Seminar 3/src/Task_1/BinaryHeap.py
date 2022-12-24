def get_right_child_index(parent_index):
    return int((2 * parent_index) + 2)


def get_left_child_index(parent_index):
    return int((2 * parent_index) + 1)


def get_parent_index_of(child_index):
    return int((child_index - 1) / 2)


class BinaryHeap:

    def __init__(self, size):
        self.__heap = [0] * (size + 1)
        self.index = 0

    # Add to the end of the heap.
    # Then check from key -> root to heapify up.
    def insert(self, key):
        self.__heap[self.index] = key
        self.index += 1
        self.__min_heapify(self.index - 1)

    # Check from bottom node to the root.
    # If the child has a parent and the parent's key is > last node: swap them,
    # Then jump to its parent node and repeat until a node has no parent.
    def __min_heapify(self, last_child):
        if not self.__has_parent(last_child):
            return

        if (self.__get_parent_of(last_child)) > self.__heap[last_child]:
            self.__swap(get_parent_index_of(last_child), last_child)
            self.__min_heapify(get_parent_index_of(last_child))

    def __get_parent_of(self, child_index):
        return self.__heap[get_parent_index_of(child_index)]

    def __has_parent(self, child_index):
        return get_parent_index_of(child_index) >= 0

    def __swap(self, a, b):
        self.__heap[a], self.__heap[b] = self.__heap[b], self.__heap[a]

    # Change the order to fit a traversal order
    def __str__(self):
        return "".join(
            f"Parent: {self.__heap[i]} \t "
            f"Left Child: {self.__heap[get_left_child_index(i)]} \t "
            f"Right child: {self.__heap[get_right_child_index(i)]}\n"

            for i in range(0, int(len(self.__heap) / 2)))


class BinaryHeapLinearTime(BinaryHeap):
    pass
