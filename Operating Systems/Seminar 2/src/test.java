import java.util.Random;


public class test{
    static int count;
    static int size = 10;
    static int[] list = new int[size];


    public void main(String[] args){




        Thread produce = new Thread(() ->{

            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            while(true){
                Random r = new Random();
                list[count] = r.nextInt(100);
                count++;

            }


        });



        Thread consume = new Thread(() ->{

            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            while(true){
                System.out.println("Consumed: " + list[count]);
                count--;
            }


        });

        produce.start();
        consume.start();


    }





    public static void consume(){




    }




    //To access the buffer
    //A thread must acquire a lock
    //Access the lock
    //Release the lock.

}