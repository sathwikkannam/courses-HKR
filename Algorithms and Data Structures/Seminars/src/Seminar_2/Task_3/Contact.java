package Seminar_2.Task_3;

import Seminar_2.Common.Node;

public class Contact extends Node<Contact> {


    private final String name;
    private final String address;
    private Contact next;

    public Contact(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    @Override
    public Contact getNext() {
        return this.next;
    }

    @Override
    public void setNext(Contact next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return String.format("[Name: %s, Address: %s]\n", getName(), getAddress());
    }


}
