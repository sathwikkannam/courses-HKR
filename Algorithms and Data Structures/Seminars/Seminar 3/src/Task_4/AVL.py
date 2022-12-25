from Task_4.Common import Rotation


class AVL:

    def __init__(self):
        self.rotation = Rotation

    def insert_handler(self, relative_root, node):
        """
        :param relative_root: Root of 'node'
        :param node: The latest inserted node
        :return: None
        """
        relative_root.set_height(self.rotation.get_tree_height(relative_root))
        balance = self.rotation.get_balance(node)

        # Left-left dominant
        #     3                  2
        #    /                  / \
        #   2          ->      1   3
        #  /
        # 1
        if balance > 1 and node.get_key() < relative_root.get_left_node().get_key():
            return self.rotation.right_rotate(relative_root)

        # Right-right dominant
        #     4                          5
        #      \                        / \
        #       5             ->       4   6
        #        \
        #         6
        elif balance < - 1 and node.get_key() > relative_root.get_right_node().get_key():
            return self.rotation.left_rotate(relative_root)

        # Left-right dominant
        #     4                   4               4
        #    /                   /               / \
        #   3           ->      5       ->      3   5
        #    \                /
        #     5              3
        elif balance > 1 and node.get_key() > relative_root.get_left_node().get_key():
            relative_root.set_left_node(self.rotation.left_rotate(relative_root.get_left_node()))
            return self.rotation.right_rotate(relative_root)

        # Right-left dominant
        #     3               3                  3
        #      \               \                / \
        #       4      ->       1        ->    1   4
        #      /                 \
        #    1                    4
        elif balance < - 1 and node.get_key() < relative_root.get_right_node().get_key():
            relative_root.set_right_node(self.rotation.right_rotate(relative_root.get_right_node()))
            return self.rotation.left_rotate(relative_root)

    def delete_handler(self, parent):
        if not parent:
            return

        parent.set_height(self.rotation.get_tree_height(parent))
        parent_balance = self.rotation.get_balance(parent)
        left_balance = self.rotation.get_balance(parent.get_left_node())
        right_balance = self.rotation.get_balance(parent.get_right_node())

        if parent_balance > 1 and left_balance >= 0:
            return self.rotation.right_rotate(parent)

        elif parent_balance < - 1 and right_balance <= 0:
            return self.rotation.left_rotate(parent)

        elif parent_balance > 1 and left_balance < 0:
            parent.set_left_node(self.rotation.left_rotate(parent.get_right_node()))
            return self.rotation.right_rotate(parent)

        elif parent_balance < -1 and right_balance > 0:
            parent.get_right_node(self.rotation.right_rotate(parent.get_right_node()))
            return self.rotation.left_rotate(parent)
