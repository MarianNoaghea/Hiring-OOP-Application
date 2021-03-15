package User;

import Company.Employee;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class User extends Consumer {

    public ArrayList<String> companiesOfInterest = new ArrayList<>();

    public Employee convert() {
        Employee employee = new Employee();
        employee.resume = this.resume;
        employee.cod = "NE"; //New Employee

        return employee;
    }

    public int getExperienceYears() {
        int luni_experienta = 0;

        for(Experience experience : this.resume.getExperiences()) {
            luni_experienta += ChronoUnit.MONTHS.between(experience.dataInceput, experience.dataSfarsit);
        }

        int ani_experienta = luni_experienta / 12;
        if(ani_experienta % 12 >= 3)
            ani_experienta++;

        return ani_experienta;
    }


    public double getTotalScore() {
        double score = 0.0;

        long ani_experienta = getExperienceYears();
        score = ani_experienta * 1.5 + this.meanGPA();

        return score;
    }

    //observer pattern
    public void update(String notification) {
        System.out.println(notification);
    }
}