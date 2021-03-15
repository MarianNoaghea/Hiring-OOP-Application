package Company;

import Aplication.Aplication;
import User.Consumer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Employee extends Consumer {
    String numeCompanie;
    Double salary;
    String departamentName;

    public Employee() {
        numeCompanie = "ExempluNume";
        salary = 0.1;
    }


    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getNumeCompanie() {
        return this.numeCompanie;
    }

    public void setCompanyName(String numeCompanie) {
        this.numeCompanie = numeCompanie;
    }

    public void setDepartament(String departament) {
        this.departamentName = departament;
    }

    //metoda adaugata de mine
    public Company getCompany() {
        Aplication aplication = Aplication.getInstance();

        for(Company company : aplication.companies)
            if(company.numeCompanie.equals(numeCompanie))
                return company;
    return null;
    }

    public String getDepartmentName() {
        return this.departamentName;
    }

    //pentru interfata grafica
    @Override
    public String toString() {
        return resume.getInformation().getNume() + " : " + cod;
    }

    //adaugat de mine pentru calcularea taxelor
    public int getLuniVechime() {
        return (int) ChronoUnit.MONTHS.between(this.resume.getExperiences().last().dataInceput, LocalDate.now());
    }
}
