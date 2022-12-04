package Task_4;

import Task_3.MyLinkedList;

public class JosephusProblemWithLinkedList {


    public static void solve(MyLinkedList<Person> people, int passes){
        int i;
        for (i = passes; people.getHead().getNext() != null; i = (i + passes) % people.size()) {
            people.remove(i);
        }

    }


}
