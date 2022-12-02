package Task_4;

import Common.Node;
import Task_3.MyLinkedList;

public class JosephusProblemWithLinkedList {

    static class Person extends Node<Person>{
        private final int number;
        private Person next;


        public Person(int number){
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

    public static void solve(MyLinkedList<Person> people, int passes){
        int i;
        for (i = passes; people.getHead().getNext() != null; i = (i + passes) % people.size()) {
            people.remove(i);
        }

    }


}
