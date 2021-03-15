package Company;

import java.util.ArrayList;

public abstract class Departament {

    public  String numeDepartament;
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Job> jobs = new ArrayList<>();

    public Departament(String numeDepartament) {
        this.numeDepartament = numeDepartament;
    }

    public abstract double getTotalSalaryBudget();
    public abstract String getTaxRule(); //adaugat de mine

    public ArrayList<Job> getJobs() {
        ArrayList<Job> joburiDisponibile = new ArrayList<>();
        for(Job job : jobs) {
            if (job.eDeschis == true) {
                joburiDisponibile.add(job);
            }
        }
        return joburiDisponibile;
    }

    public void add(Employee employee) {
        employees.add(employee);
    }

    public void remove(Employee employee) {
        employees.remove(employee);
    }

    public void add(Job job) {
        jobs.add(job);
    }


    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    //pentru interfata grafica
    @Override
    public String toString() {
        return numeDepartament;
    }
}
