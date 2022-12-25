def left_rotate(parent):
    right = parent.get_right_node()
    left = right.get_left_node()

    right.set_left_node(parent)
    parent.set_right_node(left)

    parent.set_height(get_tree_height(parent))
    right.set_height(get_tree_height(right))

    return right


def right_rotate( parent):
    left = parent.get_left_node()
    right = parent.get_right_node()

    left.set_right_node(parent)
    parent.set_left_node(right)

    parent.set_height(get_tree_height(parent))
    left.set_height(get_tree_height(left))

    return left


def get_tree_height( node):
    left = node.get_left_node().get_height() if node.get_left_node() else 0
    right = node.get_right_node().get_height() if node.get_right_node() else 0

    return max(left, right) + 1


def get_node_height( node):
    return node.get_height()


def get_balance(node):
    """
    :param node: An inserted node from where we check the height.
    :return: Returns the difference in height.
            If the difference is != {-1, 0, 1}, then its not balanced (±2)
    """

    left = node.get_left_node().get_height() if node.get_left_node() else 0
    right = node.get_right_node().get_height() if node.get_right_node() else 0

    return left - right
