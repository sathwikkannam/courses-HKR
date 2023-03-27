package Seminar_1.Task_2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Member> members = new ArrayList<>();
        Member Carl = new Member("Carl", Member.Role.MEMBER);
        Member Anna = new Member("Anna", Member.Role.ADMINISTRATOR);
        Member Paul = new Member("Paul", Member.Role.OWNER);

        members.add(Carl);
        members.add(Anna);
        members.add(Paul);


        for (Member member : members) {
            System.out.println(member.getName() + ", " + member.getRole());

        }


    }
}
