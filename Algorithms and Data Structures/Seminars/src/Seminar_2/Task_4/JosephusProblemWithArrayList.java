package Seminar_2.Task_4;

import java.util.List;

public class JosephusProblemWithArrayList {


    public static void solve(List<Integer> people, int passes) {
        int i;

        for (i = passes; people.size() > 1; i = (i + passes) % people.size()) {
            people.remove(i);
        }

    }


}
