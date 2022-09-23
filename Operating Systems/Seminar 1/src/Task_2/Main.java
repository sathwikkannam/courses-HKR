package Task_2;

import java.util.Scanner;
import Task_1.MyRunnable;
import Task_1.TestProcessBuilder;

public class Main {
    public static void main(String[] args){
        String commandLine;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n***** Welcome to the Java Command Shell *****");
        System.out.println("If you want to exit the shell, type END and press RETURN.\n");

        while (true) {
            System.out.print("jsh>");
            commandLine = scanner.nextLine();
            // if user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }
            if (commandLine.equalsIgnoreCase("end")) { //User wants to end shell
                System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                scanner.close();
                System.exit(0);
            }

            if((commandLine.equals("showerrlog"))){
                TestProcessBuilder.getErrors().forEach(System.out::println);
            }

            Thread thread = new Thread(new MyRunnable(commandLine));
            thread.start();
        }

    }
}