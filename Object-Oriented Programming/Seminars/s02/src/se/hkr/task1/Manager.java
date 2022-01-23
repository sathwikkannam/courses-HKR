package se.hkr.task1;

public class Manager extends Employee{
    private String[] projects;

    public Manager(int id, String name, String dept, double salary, String[] projects) {
        super(id ,name, dept, salary);
        this.projects = projects;
    }

    public void setProjects(String[] projects) {
        this.projects = projects;
    }

    public String[] getProjects() {
        return this.projects;
    }

    @Override
    public void work(){
        super.work();
        System.out.println("Manager is managing:");
        for (int i = 0; i < getProjects().length; i++) {
            System.out.printf("%d) %s%n", i+1, getProjects()[i]);
        }
    }

}
