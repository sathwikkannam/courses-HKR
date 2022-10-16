import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
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

            execute(commandLine);
        }

    }

    public static void execute(String command) throws IOException {
        List<String> input = Arrays.asList(command.split(" "));
        BufferedReader reader;
        Process process;
        String line;

        if(input.get(0).equals("filedump")){
            input.set(0, "./filedump.sh");
            createProcess(Arrays.asList("chmod +x filedump.sh".split(" "))).start();

        }else if(input.get(0).equals("copyfile")){
            input.set(0, "./copyfile.sh");
            createProcess(Arrays.asList("chmod +x copyfile.sh".split(" "))).start();
        }

        process =  createProcess(input).start();
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while((line = reader.readLine()) != null){
            System.out.println(line);
        }

    }

    public static synchronized ProcessBuilder createProcess(List<String> input){
        return new ProcessBuilder(input);
    }

}