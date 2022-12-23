class BinaryHeap:

    def __init__(self, size):
        self.heap = [0] * (size + 1)
        self.index = 0

    # Add to the end of the heap.
    # Then check from key -> root to heapify up.
    def insert(self, key):
        self.heap[self.index] = key
        self.index += 1
        self._min_heapify(self.index - 1)

    # Check from bottom node to the root.
    # If the child has a parent and the parent's key is > last node: swap them,
    # Then jump to its parent node and repeat until a node has no parent.
    def _min_heapify(self, last_child):
        if not self._has_parent(last_child):
            return

        if (self._get_parent_of(last_child)) > self.heap[last_child]:
            self._swap(self._get_parent_index_of(last_child), last_child)
            self._min_heapify(self._get_parent_index_of(last_child))

    def _get_left_child(self, parent_index):
        return int(self.heap[(2 * parent_index) + 1])

    def _get_right_child(self, parent_index):
        return int(self.heap[(2 * parent_index) + 2])

    def _get_parent_index_of(self, child_index):
        return int((child_index - 1) / 2)

    def _get_parent_of(self, child_index):
        return self.heap[self._get_parent_index_of(child_index)]

    def _has_parent(self, child_index):
        return self._get_parent_index_of(child_index) >= 0

    def _swap(self, a, b):
        self.heap[a], self.heap[b] = self.heap[b], self.heap[a]

    def __str__(self):
        return self.heap


class BinaryHeapLinearTime:
    pass
