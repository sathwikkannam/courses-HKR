package Seminar_2.Task_4;

import Seminar_2.Common.Node;

public class Person extends Node<Person> {
    private final int number;
    private Person next;


    public Person(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }


    @Override
    public void setNext(Person next) {
        this.next = next;
    }


    @Override
    public Person getNext() {
        return next;
    }


    @Override
    public String toString() {
        return String.format("Number: %d\n", number);
    }


}
