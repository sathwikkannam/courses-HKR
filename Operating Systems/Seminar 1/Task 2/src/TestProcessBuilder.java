import java.io.*;
import java.util.*;

public class TestProcessBuilder {
    private static final ArrayList<String> errors = new ArrayList<>();

    public static void createProcess(String command) throws java.io.IOException {

        List<String> input = Arrays.asList(command.split(" "));

        ProcessBuilder processBuilder = new ProcessBuilder(input);
        BufferedReader bufferReader = null;
        try {

            Process proc = processBuilder.start();
            InputStream inputStream = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(isr);

            String line;
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line );
            }
            bufferReader.close();
        } catch (java.io.IOException ioe) {
            if(!command.equals("showerrlog")){
                System.err.println("Error");
                System.err.println(ioe);
                errors.add(command);
            }

        } finally {
            if (bufferReader != null) {
                bufferReader.close();
            }
        }
    }


    public static ArrayList<String> getErrors(){
        return errors;
    }



}
