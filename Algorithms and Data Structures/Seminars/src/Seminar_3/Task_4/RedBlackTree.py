from Common import Node


def insert_handler(node: Node, root):
    """
    If uncle is RED, then we recolor
    If uncle is BLACK, then we perform rotations.

    :param root: The BST tree root
    :param node: The newly inserted node.
    """

    if node is not root:
        if not node.get_parent():
            return

        if node.get_parent().get_color() is Node.BLACK:
            return

        if not node.get_parent().get_parent():
            parent = node.get_parent()
            grand_father = parent.get_parent()
            uncle = grand_father.get_right_node() if parent is grand_father.get_left_node() else grand_father.get_left_node()

            parent_color = parent.get_color()
            uncle_color = uncle.get_color()

            if uncle_color is Node.RED and parent_color is not Node.BLACK:
                parent.set_color(Node.BLACK)
                uncle.set_color(Node.BLACK)
                grand_father.set_color(Node.RED)
                return insert_handler(grand_father, root)

            if uncle_color is Node.BLACK and parent_color is not Node.BLACK:
                # Rotations

                # Left-left case
                if grand_father.get_left_node() is parent and parent.get_left_node() is node:
                    __right_rotate(grand_father)
                    __swap_colors(parent, grand_father)

                # Left-right case
                elif grand_father.get_left_node() is parent and parent.get_right_node() is node:
                    __left_rotate(parent)
                    __right_rotate(grand_father)
                    __swap_colors(grand_father, node)

                # Right-right case
                elif grand_father.get_right_node() is parent and parent.get_right_node() is node:
                    __left_rotate(grand_father)
                    __swap_colors(grand_father, parent)

                # Right-left case
                elif grand_father.get_right_node() is parent and parent.get_left_node() is node:
                    __right_rotate(parent)
                    __left_rotate(grand_father)
                    __swap_colors(grand_father, node)

    root.set_color(Node.BLACK)


def __left_rotate(node: Node):
    right = node.get_right_node()
    node.set_right_node(right.get_left_node())  # Turn 'right' subtree into 'node' subtree

    if right.get_left_nod():
        right.get_left_node().set_parent(node)

    right.set_parent(node.get_parent())  # Point 'node' parent to 'right'

    if node is node.get_parent().get_left_node():
        node.get_parent().set_left_node(right)

    else:
        node.get_parent().set_right_node(right)

    right.set_left_node(node)  # Point 'node' to 'right's left child
    node.set_parent(right)


def __right_rotate(node: Node):
    left = node.get_left_node()
    node.set_right_node(left.get_left_node())  # Turn 'left' subtree into 'node' subtree

    if left.get_right_node():
        left.get_right_node().set_parent(node)

    left.set_parent(node.get_parent())  # Point 'node' parent to 'left'

    if node is node.get_parent().get_right_node():
        node.get_parent().set_right_node(left)

    else:
        node.get_parent().set_right_node(left)

    left.set_right_node(node)  # Point 'node' to 'left's right child
    node.set_parent(left)


def __swap_colors(node1: Node, node2: Node):
    temp = node2.get_color()
    node2.set_color(node1.get_color())
    node1.set_color(temp)
