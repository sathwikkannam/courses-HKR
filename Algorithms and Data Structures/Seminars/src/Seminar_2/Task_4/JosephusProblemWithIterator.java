package Seminar_2.Task_4;

import java.util.Iterator;
import java.util.List;

public class JosephusProblemWithIterator {

    public static void solve(List<Integer> people, int passes) {
        Iterator<Integer> iterator = people.iterator();

        for (int peopleLeft = people.size(); peopleLeft != 1; peopleLeft--) {
            for (int k = 0; k <= passes; k++) {
                if (!iterator.hasNext()) {
                    iterator = people.iterator();
                }

                // Iterate #passes
                iterator.next();
            }

            //Remove the person after iterating "passes" times.
            iterator.remove();
        }


    }

}
