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
        invertOrder(incoming, outgoing);
        incoming.push(item);
        invertOrder(outgoing, incoming);

    }


    //Dequeue
    public T poll(){
        return this.incoming.pop();
    }


    private void invertOrder(Stack<T> from, Stack<T> to){
        while(!from.isEmpty()){
            to.push(from.pop());
        }

    }

}
