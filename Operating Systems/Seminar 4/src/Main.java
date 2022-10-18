import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static final ArrayList<String> myCommands =  new ArrayList<>(Arrays.asList("filedump", "copyfile", "crtf"));
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

            execute(commandLine);
        }

    }

    public static void execute(String command) {
        List<String> input = Arrays.asList(command.split(" "));
        BufferedReader reader = null;
        Process process = null;

        try{
            if(myCommands.contains(input.get(0))){
                getProcessBuilder(Arrays.asList(("chmod +x " + input.get(0) + ".sh").split(" "))).start();
                input.set(0, "./" + input.get(0)+".sh");
            }

            process =  getProcessBuilder(input).start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            printStream(reader);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                closeReader(reader);
                process.destroy();
            }
        }


    }

    public static synchronized ProcessBuilder getProcessBuilder(List<String> input){
        return new ProcessBuilder(input);
    }


    public static synchronized void closeReader(Reader reader){
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static synchronized void printStream(BufferedReader bufferedReader) {
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(bufferedReader);
        }
    }

}