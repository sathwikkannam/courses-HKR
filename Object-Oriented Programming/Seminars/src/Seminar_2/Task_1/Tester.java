package Seminar_2.Task_1;

public class Tester extends Employee {
    private String[] tools;

    public Tester(int id, String name, String dept, double salary, String[] tools) {
        super(id, name, dept, salary);
        this.tools = tools;
    }

    public void setTools(String[] tools) {
        this.tools = tools;
    }

    public String[] getTools() {
        return this.tools;
    }

    @Override
    public void work() {
        super.work();
        System.out.println("Tester is testing");
        for (int i = 0; i < getTools().length; i++) {
            System.out.printf("%d) %s%n", i + 1, getTools()[i]);
        }
    }
}
