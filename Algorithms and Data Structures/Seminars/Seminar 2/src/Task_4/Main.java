package Task_4;

import Task_3.MyLinkedList;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int numberOfPeople;
        int passes;
        int mode;
        List<Integer> arrayList, javaLinkedList;
        MyLinkedList<Person> ownLinkedList;

        while(true){
            arrayList = new ArrayList<>();
            ownLinkedList = new MyLinkedList<>();
            javaLinkedList = new LinkedList<>();

            System.out.print("Number of people: ");
            numberOfPeople = scanner.nextInt();
            System.out.print("\nNumber of passes: ");
            passes = scanner.nextInt();
            System.out.print("\n1. ArrayList\n2. ArrayList with iterator\n 3. OwnLinkedList\n4. Java LinkedList with iterator\n5. Exit");
            mode = scanner.nextInt();


            for (int i = 1; i <= numberOfPeople ; i++) {
                if(mode == 1 || mode == 2){
                    arrayList.add(i);
                }else if(mode ==3){
                    ownLinkedList.add(new Person(i));
                }else if(mode ==4){
                    javaLinkedList.add(i);
                }
            }
            float start = System.currentTimeMillis();
            switch (mode){
                case 1 -> JosephusProblemWithArrayList.solve(arrayList, passes);
                case 2 -> JosephusProblemWithIterator.solve(arrayList, passes);
                case 3 -> JosephusProblemWithLinkedList.solve(ownLinkedList, passes);
                case 4 -> JosephusProblemWithIterator.solve(javaLinkedList, passes);
                default -> {
                    return;
                }
            }
            float end = System.currentTimeMillis();
            System.out.printf("\nPeople: %d, Passes: %d, Running time in ms: %f\n", numberOfPeople, passes, end - start);
        }


    }

}
