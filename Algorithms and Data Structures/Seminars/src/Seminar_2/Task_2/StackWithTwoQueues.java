package Seminar_2.Task_2;

import java.util.ArrayDeque;
import java.util.Queue;


public class StackWithTwoQueues<T> {

    private final Queue<T> output;
    private final Queue<T> temp;

    public StackWithTwoQueues() {
        this.output = new ArrayDeque<>();
        this.temp = new ArrayDeque<>();

    }

    /**
     * Time complexity - O(2N)
     * <p>
     * When we swap elements form output to temp,
     * the elements are placed above the @Param item as an item should be first to leave.
     * When adding elements, temp is always empty.
     *
     * @param item An element to add to temp stack
     */
    public void push(T item) {
        temp.add(item);
        invertOrder(output, temp);
        invertOrder(temp, output);

    }


    /**
     * Time complexity- O(1)
     * <p>
     * The last item is placed in a position first item to leave the queue.
     *
     * @return poll() of output stack
     */
    public T pop() {
        if (output.isEmpty()) {
            throw new NullPointerException("Queue is empty");
        } else {
            return output.poll();
        }

    }

    public int size() {
        return this.output.size();

    }


    /**
     * Time complexity- O(1)
     *
     * @return peek() of output stack.
     */
    public T peek() {
        if (output.isEmpty()) {
            throw new NullPointerException("Queue is empty");
        } else {
            return output.peek();
        }

    }


    private void invertOrder(Queue<T> from, Queue<T> to) {
        while (!from.isEmpty()) {
            to.add(from.poll());
        }
    }


}
