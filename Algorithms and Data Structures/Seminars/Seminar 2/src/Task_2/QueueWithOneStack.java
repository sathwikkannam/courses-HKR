package Task_2;


import java.util.Stack;

//    Queue- > enqueue, dequeue, size, and peak
public class QueueWithOneStack <T>{
    private Stack<T> stack;


    public QueueWithOneStack(){
        this.stack = new Stack<>();
    }



    public T peek(){
        return null;
    }


    public int size(){
        System.out.println(stack.size());
        return this.stack.size();
    }



    // Add to queue
    public void offer(T item){



    }


    //Remove from queue
    public T poll(){
        T toPoll;

        while(!stack.isEmpty()){



        }
        return null;

    }

}
