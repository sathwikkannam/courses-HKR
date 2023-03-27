package Seminar_2.Task_2;

import java.io.Serializable;

public class Competitor implements Serializable, Comparable<Competitor> {
    private String name;
    private int age;
    private String gender;
    private Time time;

    public Competitor(String name, int age, String gender, Time time) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public Time getTime() {
        return this.time;
    }

    @Override
    public int compareTo(Competitor o) { //Comparing the objects by taking the difference between them.
        return (this.time.getHours() * 3600 + this.time.getMinutes() * 60 + this.time.getSeconds()) - (
                o.time.getHours() * 3600 + o.time.getMinutes() * 60 + o.time.getSeconds());
    }

    @Override
    public String toString() {
        return String.format("%d:%02d:%02d --> %s",
                getTime().getHours(), getTime().getMinutes(), getTime().getSeconds(), getName());
    }
}
