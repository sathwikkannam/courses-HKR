package se.hkr.task3;

import java.util.Arrays;

public class MyArrayList<T> {
    private boolean isEmpty = true;
    private int index;
    private T[] items;


@SuppressWarnings("unchecked")
    public MyArrayList(){
        this.items = (T[]) new Object[10];
    }


    public void add(T item) {
        if (index == items.length) {
            grow();
        }
        items[index] = item;
        index++;
    }

    public void grow() {
        items = Arrays.copyOf(items, items.length+10);
    }

    public int size(){
        return this.index;
    }


    public boolean isEmpty(){
        return index == 0;
    }

    public T get(int index) {
        T index_item = null;

        try {
            if(this.items[index]!= null) {
                index_item = this.items[index];
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return index_item;
    }

}
