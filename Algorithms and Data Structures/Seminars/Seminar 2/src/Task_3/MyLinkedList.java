package Task_3;

import Common.Node;


public class MyLinkedList<T extends Node <T> > {
    private T head;

    /**
     * If there are no nodes, then head == item
     * Else we iterate over all nodes until we find a node with null pointer,
     * Then we set that node's pointer to item.
     * @param item A node to add to the end of linkedList.
     */
    public void add(T item) {
        // The new node's next pointer is always null.
        item.setNext(null);

        if (head == null) {
            head = item;

        } else {
            getLastNode().setNext(item);
        }

    }


    /**
     * If the index is 0, the head node's pointer is head and its pointer is null.
     * Else, we set the node's pointer to the node after it, and null if there are no nodes after the index.
     * @param index A value to indicate which node to delete.
     */
    public void remove(int index) {
        T node = head;
        T nextNode; // Stores the node that "node" is pointing to.

        if(index == 0){
            head = head.getNext();
        }else if(index > size() || index < 0){
            throw new IndexOutOfBoundsException();
        } else{

            for (int i = 0; i < index - 1; i++) {
                node = node.getNext();
            }

            nextNode = node.getNext();
            node.setNext(nextNode.getNext());
        }


    }


    public T get(int index) {
        int i = 0;
        T node;

        if(index > size() - 1){
            node = null;
        }else if(index < 0){
            throw new NullPointerException("Negative index");
        }else{
            for (node = head; node.getNext() != null && i != index; node = node.getNext(), i++) ;
        }

        return node;

    }


    /**
     * Iterate through all nodes until we reach a node with a null pointer.
     * @return Size of LinkedList.
     */
    public int size(){
        int i = 0;
        T node = head;

        while (node.getNext() != null) {
            i++;
            node = node.getNext();
        }

        return i + 1;

    }


    public T getHead(){
        return this.head;
    }


    private T getLastNode(){
        T node = head;

        while (node.getNext() != null) {
            node = node.getNext();
        }

        return node;

    }

    @Override
    public String toString(){
        T node;
        StringBuilder sb = new StringBuilder();

        for (node = getHead(); node.getNext() != null; node =  node.getNext()) {
            sb.append(node);
        }
        sb.append(node);

        return sb.toString();
    }

}
