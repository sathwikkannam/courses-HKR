from Task_4.Common.Node import Node


def delete_handler(parent: Node):
    if not parent:
        return

    parent.set_height(__get_balance(parent, mode=2))
    parent_balance = __get_balance(parent)
    left_balance = __get_balance(parent.get_left_node())
    right_balance = __get_balance(parent.get_right_node())

    if parent_balance > 1 and left_balance >= 0:
        return __right_rotate(parent)

    elif parent_balance < - 1 and right_balance <= 0:
        return __left_rotate(parent)

    elif parent_balance > 1 and left_balance < 0:
        parent.set_left_node(__left_rotate(parent.get_right_node()))
        return __right_rotate(parent)

    elif parent_balance < -1 and right_balance > 0:
        parent.set_right_node(__right_rotate(parent.get_right_node()))
        return __left_rotate(parent)


def insert_handler(relative_root: Node, node: Node):
    """
    :param relative_root: Root of 'node'
    :param node: The latest inserted node
    :return: None
    """
    relative_root.set_height(__get_balance(relative_root, mode=2))
    balance = __get_balance(node)

    # Left-left dominant
    #     3                  2
    #    /                  / \
    #   2          ->      1   3
    #  /
    # 1
    if balance > 1 and node.get_key() < relative_root.get_left_node().get_key():
        return __right_rotate(relative_root)

    # Right-right dominant
    #     4                          5
    #      \                        / \
    #       5             ->       4   6
    #        \
    #         6
    elif balance < - 1 and node.get_key() > relative_root.get_right_node().get_key():
        return __left_rotate(relative_root)

    # Left-right dominant
    #     4                   4               4
    #    /                   /               / \
    #   3           ->      5       ->      3   5
    #    \                /
    #     5              3
    elif balance > 1 and node.get_key() > relative_root.get_left_node().get_key():
        relative_root.set_left_node(__left_rotate(relative_root.get_left_node()))
        return __right_rotate(relative_root)

    # Right-left dominant
    #     3               3                  3
    #      \               \                / \
    #       4      ->       1        ->    1   4
    #      /                 \
    #    1                    4
    elif balance < - 1 and node.get_key() < relative_root.get_right_node().get_key():
        relative_root.set_right_node(__right_rotate(relative_root.get_right_node()))
        return __left_rotate(relative_root)


def __left_rotate(parent: Node) -> Node:
    right = parent.get_right_node()
    left = right.get_left_node()

    right.set_left_node(parent)
    parent.set_right_node(left)

    parent.set_height(__get_balance(parent, mode=2))
    right.set_height(__get_balance(right, mode=2))

    return right


def __right_rotate(parent: Node) -> Node:
    left = parent.get_left_node()
    right = parent.get_right_node()

    left.set_right_node(parent)
    parent.set_left_node(right)

    parent.set_height(__get_balance(parent, mode=2))
    left.set_height(__get_balance(left, mode=2))

    return left


def __get_balance(node: Node, mode=1):
    """
    :param mode: If 'mode' is 1, it returns the balance factor.
                 Else tree height
    :param node: An inserted node from where we check the height.
    :return: Returns the difference in height.
            If the difference is != {-1, 0, 1}, then its not balanced (±2)
    """

    left = node.get_left_node().get_height() if node.get_left_node() else 0
    right = node.get_right_node().get_height() if node.get_right_node() else 0

    return left - right if mode == 1 else max(left, right) + 1 if mode == 2 else None
