package Task_3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MyLinkedList<Contact> list = new MyLinkedList<>();
        Scanner scanner = new Scanner(System.in);
        int index;
        String name;
        String address;


        while (true) {
            System.out.print("\nOperations:\n1.Add\n2.Remove\n3.Get node\n4.Display all contacts\n5.Exit\nChoice:");
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.print("Name: ");
                    name = scanner.next();
                    System.out.print("Address: ");
                    address = scanner.next();
                    list.add(new Contact(name, address));
                }
                case 2 -> {
                    System.out.print("\nIndex to remove: ");
                    index = scanner.nextInt();
                    list.remove(index);
                }
                case 3 -> {
                    System.out.print("\nIndex to get: ");
                    index = scanner.nextInt();
                    System.out.println(list.get(index));
                }
                case 4 -> System.out.println(list);
                default -> {
                    return;
                }
            }
        }

    }

}
