public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread. waiting for stopwatch thread...");

        Thread stopwatch =  new Thread(() -> {
            System.out.println("Stopwatch thread. Stopwatch starts now!");
            for (float i = 0.01f; i <= 60; i+=1/100.0f){
                try {
                    Thread.sleep(10);
                    System.out.printf("%s %.2f %s\n", "Stopwatch thread. Elapsed", i, "seconds");
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