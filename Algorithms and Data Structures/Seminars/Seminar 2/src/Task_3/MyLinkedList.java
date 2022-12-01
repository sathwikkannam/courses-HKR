package Task_3;

public class MyLinkedList<T extends Contact> {
    private T head;

    /*
        1. Add node
        2. Remove node
        3. Get node using index
     */

    public void add(T item) {
        if (head == null) {
            head = item;

            // The new node's next pointer is always null.
            item.setNext(null);

        } else {

            T node = head;

            // We travel from head until a node is null
            while (node.getNext() != null) {
                node = (T) node.getNext();
            }

            // When node is null -> the last node in LinkedList
            node.setNext(item);
        }

    }


    public void remove(int index) {
        T node = head;
        T nextNode; // Stores the node that "node" is pointing to.


        for (int i = 0; i < index - 1; i++) {
            node = (T) node.getNext();
        }


        nextNode = (T) node.getNext();
        node.setNext(nextNode.getNext());

    }


    public T get(int index) {
        int i = 0;
        T node;

        for (node = head; node.getNext() != null && i != index; node = (T) node.getNext(), i++) ;

        return node;

    }


    @Override
    public String toString() {
        T node;
        StringBuilder nodes = new StringBuilder();

        for (node = head; node.getNext() != null; node = (T) node.getNext()) {
            nodes.append(String.format("[Name: %s, Address: %s]\n", node.getName(), node.getAddress()));
        }


        // Include the last node with null pointer.
        nodes.append(String.format("[Name: %s, Address: %s]\n", node.getName(), node.getAddress()));

        return nodes.toString();
    }

}
