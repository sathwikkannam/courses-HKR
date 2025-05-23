package Seminar_2.Task_2;


import java.util.ArrayDeque;
import java.util.Queue;

public class StackWithOneQueue<T> {

    private final Queue<T> queue;

    public StackWithOneQueue() {
        this.queue = new ArrayDeque<>();

    }

    /**
     * Time complexity- O(N)
     * <p>
     * When item is added to the queue, the item is inserted.
     * Then we iterate through all elements - 1 to place them above the inserted item.
     * Hence, the inserted item (last to enter) becomes the first to leave.
     *
     * @param item An element to add to queue
     */
    public void push(T item) {
        queue.add(item);

        for (int i = 0; i < size() - 1; i++) {
            queue.add(queue.remove());
        }

    }


    /**
     * Time complexity- O(1)
     * <p>
     * The last item is placed in a position first item to leave the queue.
     *
     * @return poll() of queue
     */
    public T pop() {
        if (queue.isEmpty()) {
            throw new NullPointerException("Queue is empty");
        } else {
            return queue.remove();
        }

    }

    public int size() {
        return this.queue.size();

    }


    /**
     * Time complexity- O(1)
     *
     * @return peek() of queue
     */
    public T peek() {
        if (queue.isEmpty()) {
            throw new NullPointerException("Queue is empty");
        } else {
            return queue.peek();
        }
    }


}
