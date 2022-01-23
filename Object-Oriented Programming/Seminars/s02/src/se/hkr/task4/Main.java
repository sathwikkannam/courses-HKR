package se.hkr.task4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {
    public static void main(String[] args){
        Main app =  new Main();
        app.popStack(app.createStack(app.createQueue()));

    }

    public Queue<String> createQueue(){
        String[] letters = {"A", "B", "C", "D", "E", "F"};
        Queue<String> plane = new LinkedList<>();

        for (int i = 40; i > 0 ; i--) {
            for (String alphabet: letters) {
                plane.add(i+alphabet);
            }
        }
        return plane;
    }

    public Stack<String> createStack(Queue<String> seats){
        Stack<String> stack_seats = new Stack<>();
        int size = seats.size();

        for (int i = 0; i < size; i++) {
            stack_seats.add(seats.poll());
        }
        return stack_seats;
    }

    public Stack<String> popStack(Stack<String> stack_seats){
        int size = stack_seats.size();

        for (int i = 0; i < size; i++) {
            stack_seats.pop();
        }
        return stack_seats;
    }

    public void viewQueue(Queue<String> q_seats){
        if(q_seats.isEmpty()){
            System.out.println("The queue is empty");
        } else {
            System.out.println(q_seats);
        }

    }

    public void viewStack(Stack<String> stack_seats){
        if(stack_seats.isEmpty()){
            System.out.println("The stack is empty");
        } else {
            System.out.println(stack_seats);
        }
    }
}
