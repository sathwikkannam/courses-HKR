package se.hkr.task2;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Main app = new Main();
        ArrayList<Competitor> competitors_list = app.letsRun("participants.txt");
        Collections.sort(competitors_list);

        System.out.println("Running result:");
        for (Competitor competitor : competitors_list) {
            System.out.println(competitor.toString());
        }
        app.writeToFile(competitors_list, "RunningTimeResults.ser");
    }

    public ArrayList<Competitor> letsRun(String path) throws FileNotFoundException {
        ArrayList<Competitor> competitors =  new ArrayList<>();
        Scanner read = new Scanner(new File(path));
        Random rand = new Random();

        while (read.hasNextLine()) {
            int hour = rand.nextInt(1, 3);
            int minute = rand.nextInt(0, 59);
            int second = rand.nextInt(0, 59);

            String[] line  = read.nextLine().split(",");
            competitors.add(new Competitor(line[0], Integer.parseInt(line[1]), line[2], new Time(hour, minute, second)));
        }
        return competitors;

    }

    public void writeToFile(ArrayList<Competitor> competitors_list, String path){
        try{
            ObjectOutputStream object_output = new ObjectOutputStream(new FileOutputStream(path));
            object_output.writeObject(competitors_list);
            object_output.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
