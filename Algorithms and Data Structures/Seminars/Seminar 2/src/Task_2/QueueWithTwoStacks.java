package Task_2;

import java.util.Stack;


//Queue- > enqueue, dequeue, size, and peak
public class QueueWithTwoStacks <T>{

    private final Stack<T> incoming;
    private final Stack<T> outgoing;


    public QueueWithTwoStacks(){
        this.incoming = new Stack<>();
        this.outgoing = new Stack<>();
    }



    public T peek(){
        return incoming.peek();
    }


    public int size(){
        return this.incoming.size();
    }



    // Enqueue
    public void offer(T item){

        // Move elements from incoming to outgoing to swap the order, so LIFO to FIFO order
        while(!incoming.isEmpty()){
            outgoing.push(incoming.pop());
        }

        incoming.push(item);

        while(!outgoing.isEmpty()){
            incoming.push(outgoing.pop());

        }


    }


    //Dequeue
    public T poll(){
        return this.incoming.pop();
    }

}
