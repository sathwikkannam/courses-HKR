package se.hkr;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Main app = new Main();
        DatabaseConnection connection = app.makeConnection(input);

        if(connection != null){
            do {
                System.out.print("1. Retrieve\n2. Update\nChoice: ");
                int selection = input.nextInt();

                if(selection == 1){
                    input.nextLine();
                    app.doSelect(input, connection);

                }else{

                }
            } while (!input.nextLine().equals("EXIT") || !input.nextLine().equals("exit"));
        }


    }


    public DatabaseConnection makeConnection(Scanner input) {
        DatabaseConnection connection = null;
        System.out.print("Enter address: ");
        String address = (input.nextLine().length() == 0) ? "localhost" : input.nextLine();
        System.out.print("Enter PORT: ");
        String port = (input.nextLine().length() == 0) ? "3306" : input.nextLine();
        System.out.print("Enter Username: ");
        String username = (input.nextLine().length() == 0) ? "root" : input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        try {
            System.out.println("\n--------------------------------");
            connection = new DatabaseConnection(address, port, username, password);
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            System.out.println("ERROR!");
        }
        System.out.println("--------------------------------");

        return connection;

    }


    public void doSelect(Scanner input, DatabaseConnection connection){
        System.out.print("Enter coloums (seperated by ','): ");
        String columns = input.nextLine();
        System.out.print("FROM: ");
        String from = input.nextLine();
        System.out.print("LIMIT (0 for no limit): ");
        int limit = input.nextInt();
        connection.executeQuery(columns, from, limit);
    }

    public void doUpdate(Scanner input, DatabaseConnection connection){
        System.out.println("1. Update\n2. Insert\n3.Delete");
        int updateSelection = input.nextInt();

        switch(updateSelection){
            case 1:
            case 2:
            case 3:

        }
    }
}
