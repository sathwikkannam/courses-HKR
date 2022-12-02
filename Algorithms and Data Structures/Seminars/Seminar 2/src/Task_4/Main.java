package Task_4;

import Task_3.MyLinkedList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int numberOfPeople;
        int passes;
        int mode;
        List<Integer> people = new ArrayList<>();
        MyLinkedList<JosephusProblemWithLinkedList.Person> ownLinkedPeople = new MyLinkedList<>();
        List<Integer> linkedPeople = new LinkedList<>();


        while(true){
            System.out.print("Number of people: ");
            numberOfPeople = scanner.nextInt();
            System.out.print("\nNumber of passes: ");
            passes = scanner.nextInt();
            System.out.print("\n1. ArrayList\n2. ArrayList with iterator\n 3. OwnLinkedList\n4. LinkedList with iterator\n5. Exit");
            mode = scanner.nextInt();


            for (int i = 1; i <= numberOfPeople ; i++) {
                if(mode == 1 || mode == 2){
                    people.add(i);
                }else if(mode ==3){
                    ownLinkedPeople.add(new JosephusProblemWithLinkedList.Person(i));
                }else if(mode ==4){
                    linkedPeople.add(i);
                }
            }

            float start = System.currentTimeMillis();
            switch (mode){
                case 1 -> JosephusProblemWithArrayList.solve(people, passes);
                case 2 -> JosephusProblemWithIterator.solve(people, passes);
                case 3 -> JosephusProblemWithLinkedList.solve(ownLinkedPeople, passes);
                case 4 -> JosephusProblemWithIterator.solve(linkedPeople, passes);
                default -> {
                    return;
                }
            }
            float end = System.currentTimeMillis();
            System.out.printf("\nPeople: %d, Passes: %d, Running time in ms: %f\n", numberOfPeople, passes, end - start);
        }

    }


}
