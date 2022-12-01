package Task_3;

public class Contact {


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

    public Contact getNext() {
        return this.next;
    }

    public void setNext(Contact next) {
        this.next = next;
    }


}
