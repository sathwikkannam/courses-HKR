package se.hkr.task3;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        boolean keepRunning = true;
        Scanner option = new Scanner(System.in);
        MyArrayList<Item> arraylist = new MyArrayList<>();

        while(keepRunning){
            System.out.print("1] Add items\n2] View items\n3] Quit\n> ");
            switch(option.nextInt()){
                case 1:
                    System.out.print("Name: ");
                    String name = option.next();
                    while(true) {
                        try{
                            System.out.print("Quantity: ");
                            arraylist.add(new Item(name, Integer.parseInt(option.next())));
                            break;
                        } catch(InputMismatchException | NumberFormatException f){
                            System.out.println("Quantity needs to be integer value");
                        }
                    }
                    break;
                case 2:
                    if(arraylist.isEmpty()) {
                        break;
                    }else{
                        for (int i = 0; i < arraylist.size(); i++) {
                            System.out.println(arraylist.get(i).toString());
                        }
                    }
                    break;
                case 3:
                    keepRunning = false;
                    break;
            }
        }
    }

}
