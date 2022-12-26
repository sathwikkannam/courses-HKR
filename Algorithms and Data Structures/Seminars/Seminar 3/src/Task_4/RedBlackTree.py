from Task_4.Common.Node import Node


def insert_handler(node: Node, root: Node):
    """
    Based on: https://www.youtube.com/watch?v=YCo2-H2CL6Q

    If uncle is RED, then we recolor
    If uncle is BLACK, then we perform rotations.

    :param root: Root of tree.
    :param node: The newly inserted node.
    """

    # If the rotations return a node when the given "node" to left_rotate and right_rotate is root.
    replace_root = None

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

            elif uncle_color is Node.BLACK and parent_color is not Node.BLACK:
                # Rotations

                # Left-left case
                if grand_father.get_left_node() is parent and parent.get_left_node() is node:
                    replace_root = __right_rotate(grand_father)
                    __swap_colors(parent, grand_father)

                # Left-right case
                elif grand_father.get_left_node() is parent and parent.get_right_node() is node:
                    replace_root = __left_rotate(parent)
                    replace_root = __right_rotate(grand_father)
                    __swap_colors(grand_father, node)

                # Right-right case
                elif grand_father.get_right_node() is parent and parent.get_right_node() is node:
                    replace_root = __left_rotate(grand_father)
                    __swap_colors(grand_father, parent)

                # Right-left case
                elif grand_father.get_right_node() is parent and parent.get_left_node() is node:
                    replace_root = __right_rotate(parent)
                    replace_root = __left_rotate(grand_father)
                    __swap_colors(grand_father, node)

            root = replace_root if replace_root else root

    root.set_color(Node.BLACK)


# https://www.codesdope.com/course/data-structures-red-black-trees-deletion/
def delete_handler(node: Node, root: Node):
    while node is not root and node.get_color() is Node.BLACK:
        if node is node.get_parent().get_left_node():
            sibling = node.get_parent().get_right_node()
            if sibling.get_color() is Node.RED:
                sibling.set_color(Node.BLACK)
                node.get_parent().set_color(Node.RED)
                __left_rotate(node.get_parent())
                sibling = node.get_parent().get_right_node()

            if sibling.get_left_node().get_color() is Node.BLACK and sibling.get_right_node().get_color() is Node.BLACK:
                sibling.set_color(Node.RED)
                node = node.get_parent()

            else:
                if sibling.get_right_node().get_color() is Node.BLACK:
                    sibling.get_left_node().set_color(Node.BLACK)
                    sibling.set_color(Node.RED)
                    __right_rotate(sibling)
                    sibling = node.get_parent().get_right_node()

                sibling.set_color(node.get_parent().get_color())
                node.get_parent().set_color(Node.BLACK)
                sibling.get_right_node().set_color(Node.BLACK)
                __left_rotate(node.parent)
                node = root

        else:
            sibling = node.get_parent().get_left_node()
            if sibling.get_color() is Node.RED:
                sibling.set_color(Node.BLACK)
                node.get_parent().set_color(Node.RED)
                __right_rotate(node.parent)
                sibling = node.get_parent().get_left_node()

            if sibling.get_right_node().get_color() is Node.BLACK and sibling.get_left_node().get_color() is Node.BLACK:
                sibling.set_color(Node.RED)
                node = node.get_parent()

            else:
                if sibling.get_left_node().get_color() is Node.BLACK:
                    sibling.get_right_node().set_color(Node.BLACK)
                    sibling.set_color(Node.RED)
                    __left_rotate(sibling)
                    sibling = node.get_parent().get_left_node()

                sibling.set_color(node.get_parent().get_color())
                node.get_parent().set_color(Node.BLACK)
                sibling.get_left_node().set_color(Node.BLACK)
                __right_rotate(node.parent)
                node = root

    node.set_color(Node.BLACK)


def __left_rotate(node: Node) -> Node:
    to_return = None
    right = node.get_right_node()
    node.set_right_node(right.get_left_node())

    if right.get_left_nod():
        right.get_left_node().set_parent(node)

    right.set_parent(node.get_parent())

    if not node.get_parent():
        to_return = right

    elif node is node.get_parent().get_left_node():
        node.get_parent().set_left_node(right)

    else:
        node.get_parent().set_right_node(right)

    right.set_left_node(node)
    node.set_parent(right)

    return to_return


def __right_rotate(node: Node) -> Node:
    to_return = None
    left = node.get_left_node()
    node.set_right_node(left.get_left_node())

    if left.get_right_node():
        left.get_right_node().set_parent(node)

    left.set_parent(node.get_parent())

    if not node.get_parent():
        to_return = left

    elif node is node.get_parent().get_right_node():
        node.get_parent().set_right_node(left)

    else:
        node.get_parent().set_right_node(left)

    left.set_right_node(node)
    node.set_parent(left)

    return to_return


def __swap_colors(node1: Node, node2: Node):
    temp = node1.get_color()
    node2.set_color(node2.get_color())
    node1.set_color(temp)
