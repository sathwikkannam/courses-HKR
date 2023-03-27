package Seminar_1;

import java.util.ArrayList;
import java.util.Scanner;

public class Task_4 {
    public static void main(String[] args) {
        int counter = 0;
        ArrayList<Integer> seats = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        Task_4 App = new Task_4();

        System.out.print("How many passenger coaches are there: ");
        int number_of_coaches = input.nextInt();
        System.out.println("Specify number of seats in each coach:");

        do {
            counter++;
            System.out.print(counter + ": ");
            int number_of_seats = input.nextInt();
            seats.add(number_of_seats);


        } while (counter != number_of_coaches);

        System.out.print("To what degree should we book (in percentage)? ");
        int degree = input.nextInt();
        System.out.print("How many have already booked seats for this train? ");
        int booked_seats = input.nextInt();

        boolean booked_or_not = App.isOverbooked(seats, degree, booked_seats);

        if (booked_or_not) {
            System.out.println("The train is overbooked");
        } else {
            System.out.println("OK! The train is not overbooked");
        }
    }

    public boolean isOverbooked(ArrayList<Integer> list_seats, int degree_value, int booked) {
        int sum_of_seats = 0;

        for (int seats : list_seats) {
            sum_of_seats += seats;
        }

        return ((sum_of_seats * (double) degree_value / 100) - booked) < 0;
    }
}
