package Task_3;

import Common.Node;

public class MyLinkedList<T extends Node <T> > {
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
            getLastNode().setNext(item);
        }

    }


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
            throw new IndexOutOfBoundsException();
        }else{
            for (node = head; node.getNext() != null && i != index; node = node.getNext(), i++) ;
        }

        return node;

    }


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


    public T getLastNode(){
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
