package Seminar_1;

import java.util.Scanner;

public class Task_3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Task_3 App = new Task_3();
        String[] names = {"abc", "Karl", "Emma", "sun"};

        for (String name : names) {
            System.out.println(name + " - " + App.encrypt(name));
        }

        System.out.print("Enter plain text: ");
        String entered_text = input.nextLine();
        System.out.println(App.encrypt(entered_text));
    }

    public String encrypt(String text) {
        for (int i = text.length() - 1; i >= 0; i--) {
            text += text.charAt(i);
        }
        return text;
    }
}

