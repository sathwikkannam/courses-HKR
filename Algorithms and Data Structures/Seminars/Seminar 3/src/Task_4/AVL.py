from Task_4.Common import Rotation


def delete_handler(parent):
    if not parent:
        return

    parent.set_height(Rotation.get_tree_height(parent))
    parent_balance = Rotation.get_balance(parent)
    left_balance = Rotation.get_balance(parent.get_left_node())
    right_balance = Rotation.get_balance(parent.get_right_node())

    if parent_balance > 1 and left_balance >= 0:
        return Rotation.right_rotate(parent)

    elif parent_balance < - 1 and right_balance <= 0:
        return Rotation.left_rotate(parent)

    elif parent_balance > 1 and left_balance < 0:
        parent.set_left_node(Rotation.left_rotate(parent.get_right_node()))
        return Rotation.right_rotate(parent)

    elif parent_balance < -1 and right_balance > 0:
        parent.get_right_node(Rotation.right_rotate(parent.get_right_node()))
        return Rotation.left_rotate(parent)


def insert_handler(relative_root, node):
    """
    :param relative_root: Root of 'node'
    :param node: The latest inserted node
    :return: None
    """
    relative_root.set_height(Rotation.get_tree_height(relative_root))
    balance = Rotation.get_balance(node)

    # Left-left dominant
    #     3                  2
    #    /                  / \
    #   2          ->      1   3
    #  /
    # 1
    if balance > 1 and node.get_key() < relative_root.get_left_node().get_key():
        return Rotation.right_rotate(relative_root)

    # Right-right dominant
    #     4                          5
    #      \                        / \
    #       5             ->       4   6
    #        \
    #         6
    elif balance < - 1 and node.get_key() > relative_root.get_right_node().get_key():
        return Rotation.left_rotate(relative_root)

    # Left-right dominant
    #     4                   4               4
    #    /                   /               / \
    #   3           ->      5       ->      3   5
    #    \                /
    #     5              3
    elif balance > 1 and node.get_key() > relative_root.get_left_node().get_key():
        relative_root.set_left_node(Rotation.left_rotate(relative_root.get_left_node()))
        return Rotation.right_rotate(relative_root)

    # Right-left dominant
    #     3               3                  3
    #      \               \                / \
    #       4      ->       1        ->    1   4
    #      /                 \
    #    1                    4
    elif balance < - 1 and node.get_key() < relative_root.get_right_node().get_key():
        relative_root.set_right_node(Rotation.right_rotate(relative_root.get_right_node()))
        return Rotation.left_rotate(relative_root)
