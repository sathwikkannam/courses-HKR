package se.hkr.task1;

public class Developer extends Employee{
    private String technology;

    public Developer(int id, String name, String dept, double salary, String technology) {
        super(id, name, dept, salary);
        this.technology = technology;
    }


    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return this.technology;
    }

    @Override
    public void work() {
        super.work();
        System.out.println("Developer is developing" + " " + getTechnology());
    }
}
