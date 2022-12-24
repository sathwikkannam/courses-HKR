class BinarySearchTree:
    def __init__(self):
        self.root = None

    # @Param current_node -> root node relative to @Param node
    # @Param node         -> node to be inserted.
    def insert(self, node, current_node=None):
        # Root has no parent
        if self.root is None:
            self.root = node
            return

        if current_node is None:
            current_node = self.root

        if node.get_key() == current_node.get_key():
            return

        if node.get_key() < current_node.get_key():
            if current_node.get_left_node():
                return self.insert(node, current_node.get_left_node())

            current_node.set_left_node(node)
            current_node.get_left_node().set_parent(current_node)

        elif node.get_key() > current_node.get_key():
            if current_node.get_right_node():
                return self.insert(node, current_node.get_right_node())

            current_node.set_right_node(node)
            current_node.get_right_node().set_parent(current_node)

    def delete(self, node):
        if node == self.root:
            self.root = None
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
            minimum = self.__find_min_right(to_delete)
            self.delete(minimum)
            parent.set_left_node(minimum) if parent.get_left_node() == to_delete else parent.set_right_node(minimum)

    def __find_min_right(self, node):
        temp = None
        cur = node

        while cur.get_right_node() is None:
            if cur.get_right_node() < temp or None:
                temp = cur.get_right_node()

            cur = cur.get_right_node()

        return temp

    # @Param mode -> If mode == 1, it returns the node or None. Else, True or False
    def contains(self, node, current_node=None, mode=1):
        if self.root is None:
            return False

        if current_node is None:
            current_node = self.root

        if node.get_key() == current_node.get_key():
            return current_node if mode == 1 else True

        elif node.get_key() < current_node.get_key() and current_node.get_left_node():
            return self.contains(node, current_node.get_left_node())
        elif node.get_key() > current_node.get_key() and current_node.get_right_node():
            return self.contains(node, current_node.get_right_node())

        return None if mode == 1 else False

    def __str__(self, current_node=None):
        if self.root:
            self.__print(self.root)

    def __print(self, node):
        if node is not None:
            self.__print(node.get_left_node())
            print(node.get_key())
            self.__print(node.get_right_node())
