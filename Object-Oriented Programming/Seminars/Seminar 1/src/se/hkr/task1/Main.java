package se.hkr.task1;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main App = new Main();
        App.openCalendar(App.storeChocolateObjects());

    }

    public ChocolatePiece[] storeChocolateObjects() {
        int counter = 0;
        int index = 0;
        ChocolatePiece[] Pieces = new ChocolatePiece[24];
        int[] weight = {7, 8, 4};
        Random rand = new Random();

        while (counter != Pieces.length) {
            int rand_index = rand.nextInt(weight.length);
            int rand_weight = weight[rand_index];
            counter++;
            switch (rand_weight) {
                case 7:
                    ChocolatePiece star = new ChocolatePiece("star", 7);
                    Pieces[index] = star;
                    index++;
                    break;
                case 8:
                    ChocolatePiece santa = new ChocolatePiece("santa", 8);
                    Pieces[index] = santa;
                    index++;
                    break;
                case 4:
                    ChocolatePiece bell = new ChocolatePiece("bell", 4);
                    Pieces[index] = bell;
                    index++;
                    break;
            }
        }
        return Pieces;

    }

    public void openCalendar(ChocolatePiece[] chocolates) {
        int eaten = 0;
        Scanner input = new Scanner(System.in);

        while(eaten != chocolates.length){
            System.out.print("Choose window: ");
            int window = input.nextInt();

            if(window >= 0 && window < chocolates.length){
                if(!chocolates[window].isEaten()){
                    System.out.println(chocolates[window].getShape() + ", " + chocolates[window].getWeight());
                    chocolates[window].setEaten(true);
                    eaten ++;

                } else {
                    System.out.println("Chocolate Piece is Eaten");
                }
            } else {
                System.out.println("Window is out of range");
            }

            }

            }
        }


