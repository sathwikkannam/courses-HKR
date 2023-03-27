package se.hkr.task3;

public class Item {
    private final String name;
    private final int quantity;

    public Item(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String getName(){
        return this.name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public String toString(){
        return String.format("Name: %s, Quantity: %d", getName(), getQuantity());
    }
}

