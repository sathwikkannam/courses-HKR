package se.hkr;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner input =  new Scanner(System.in);
        Main app = new Main();
        DatabaseConnection connection = app.makeConnection(input);

        do {
            System.out.println("Enter SQL Command: ");
            String command = input.nextLine();
            String column = input.nextLine();
            connection.executeQuery(command, column);

        } while (!input.nextLine().equals("EXIT") || !input.nextLine().equals("exit"));

    }



    public DatabaseConnection makeConnection(Scanner input){
        DatabaseConnection connection = null;
        System.out.print("Enter address: ");
        String address = (input.nextLine().length() == 0)? "localhost":input.nextLine();
        System.out.print("Enter PORT: ");
        String port = (input.nextLine().length() == 0)? "3306":input.nextLine();
        System.out.print("Enter Username: ");
        String username = (input.nextLine().length() == 0)? "root":input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        System.out.print("Select Schema: ");
        String schema = input.nextLine();

        try{
            connection = new DatabaseConnection(address, port, schema, username, password);
            System.out.println("\n--------------------------------");
            System.out.println("Connection Successful");
            System.out.println("---------------------------------");
        }catch(SQLException e){
            System.out.println("ERROR!");
        }
        
        return connection;

    }


//    UPDATE employees
//    SET
//            email = 'mary.patterson@classicmodelcars.com'
//    WHERE
//            employeeNumber = 1056;
}
