package Seminar_2.Task_2;


import java.util.Stack;


public class QueueWithOneStack<T> {
    private final Stack<T> stack;


    public QueueWithOneStack() {
        this.stack = new Stack<>();
    }


    /**
     * Time complexity - O(1)
     *
     * @param item An element to add to stack
     */
    public void add(T item) {
        stack.add(item);
    }


    /**
     * Time complexity - O(N)
     * Pop all elements in the stack until we only have one element (the one at the bottom).
     * Then we add back all popped elements.
     *
     * @return top of stack
     */
    public T peek() {
        if (stack.isEmpty()) {
            throw new NullPointerException("Queue is empty");
        } else if (size() == 1) {
            return stack.peek();
        }

        T toPeek = stack.peek();
        T toReturn = peek();
        add(toPeek);
        return toReturn;

    }


    /**
     * @return Size of stack
     */
    public int size() {
        return this.stack.size();
    }


    /**
     * Time complexity - O(N)
     * Same operation as peek()
     *
     * @return An element in stack
     */
    public T poll() {
        if (stack.isEmpty()) {
            throw new NullPointerException("Queue is empty");
        } else if (size() == 1) {
            return stack.pop();
        }

        T toPeek = stack.pop();
        T toReturn = poll();
        add(toPeek);
        return toReturn;

    }

}
