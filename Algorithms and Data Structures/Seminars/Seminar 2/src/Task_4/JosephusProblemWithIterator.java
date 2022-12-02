package Task_4;

import java.util.Iterator;
import java.util.List;

public class JosephusProblemWithIterator {

    public static void solve(List<Integer> people, int passes){
        Iterator<Integer> iterator = people.iterator();

        for (int peopleLeft = people.size(); peopleLeft != 1 ; peopleLeft--) {
            for (int k = 0; k <= passes ; k++) {
                if(!iterator.hasNext()){
                    iterator = people.iterator();
                }

                iterator.next();
            }
            iterator.remove();
        }


    }

}
