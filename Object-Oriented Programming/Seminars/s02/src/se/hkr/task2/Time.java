package se.hkr.task2;

import java.io.Serializable;

public class Time implements Serializable {
    private int hours;
    private int minutes;
    private int seconds;

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours(){
        return this.hours;

    }

    public int getMinutes(){
        return this.minutes;

    }

    public int getSeconds(){
        return this.seconds;

    }
}
