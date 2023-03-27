package Seminar_2.Task_2;

import java.util.Stack;


public class QueueWithTwoStacks<T> {

    private final Stack<T> input;
    private final Stack<T> output;

    public QueueWithTwoStacks() {
        this.output = new Stack<>();
        this.input = new Stack<>();
    }


    /**
     * Time complexity - O(1)
     *
     * @param item An element to add to input stack
     */
    public void add(T item) {
        input.add(item);

    }


    /**
     * Time complexity:
     * Best case: O(1) when output is not empty;
     * Worst case: O(N) when output is empty,
     * then we need to swap elements from input to output to swap the order of pop.
     *
     * @return Top of output stack
     */
    public T peek() {
        if (output.isEmpty()) {

            if (input.isEmpty()) {
                throw new NullPointerException("Queue is empty");
            }

            invertOrder(input, output);
        }
        return output.peek();

    }


    /**
     * @return Size of output stack
     */
    public int size() {
        return this.output.size();
    }


    /**
     * Same complexity as peek()
     *
     * @return pop of output stack
     */

    public T poll() {
        if (output.isEmpty()) {

            if (input.isEmpty()) {
                throw new NullPointerException("Queue is empty");
            }

            invertOrder(input, output);
        }
        return output.pop();


    }


    /**
     * Swap element from one stack to another stack.
     *
     * @param from An input stack
     * @param to   An output stack
     */
    private void invertOrder(Stack<T> from, Stack<T> to) {
        while (!from.isEmpty()) {
            to.push(from.pop());
        }

    }

}
