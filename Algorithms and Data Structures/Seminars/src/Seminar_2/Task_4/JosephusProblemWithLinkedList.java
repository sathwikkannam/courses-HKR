package Seminar_2.Task_4;

import Seminar_2.Task_3.MyLinkedList;

public class JosephusProblemWithLinkedList {


    public static void solve(MyLinkedList<Person> people, int passes) {
        int i;

        for (i = passes; people.size() > 1; i = (i + passes) % people.size()) {
            people.remove(i);
        }

    }


}
