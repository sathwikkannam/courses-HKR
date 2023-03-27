package Seminar_2.Task_1;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        Main App = new Main();

        String[] projects = {"PPG Signal", "Thorax Signal"};
        String[] tools = {"Selenium", "TestingWhiz", "Ranorex"};

        Employee manager = new Manager(1, "John", "R&D", 100000, projects);
        Employee tester = new Tester(2, "Anna", "R&D", 80000, tools);
        Employee developer = new Developer(3, "Henrik", "R&D", 96000, "Machine Learning Algorithm");

        employees.add(manager);
        employees.add(tester);
        employees.add(developer);

        App.printEmployees(employees);

    }

    public void printEmployees(ArrayList<Employee> workers) {
        for (Employee employee : workers) {
            System.out.println(employee.getName() + " is " + employee.getClass().getSimpleName() + "!");
            employee.work();

        }

    }

}
