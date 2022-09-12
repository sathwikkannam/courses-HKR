public class Main {
    public static float seconds;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread. waiting for stopwatch thread...");

        Thread stopwatch =  new Thread(() -> {
            System.out.println("Stopwatch thread. Stopwatch starts now!");
            for (seconds = 0.1f; seconds <= 60; seconds+=1/100.0f){
                try {
                    System.out.printf("%s %.2f %s\n", "Stopwatch thread. Elapsed", seconds, "seconds");
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           }
        });

        stopwatch.start(); //Start the stopwatch thread.
        stopwatch.join(); // Wait for stopwatch thread to finish.
        System.out.println("Main Thread. Finished stopwatch thread.");

    }


}