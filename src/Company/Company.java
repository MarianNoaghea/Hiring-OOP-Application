package Company;

import Aplication.Aplication;
import User.User;

import java.util.ArrayList;


public class Company {
    public String numeCompanie;
    public Manager manager;
    public ArrayList<Departament> departaments;
    public ArrayList<Recruiter> recruiters;

    public ArrayList<User> candidates = new ArrayList<>();

    public Company(String numeCompanie, Manager manager) {
        departaments = new ArrayList<>();
        recruiters = new ArrayList<>();

        //factory pattern
        this.add(new DepartmentFactory().factory("Finance"));
        this.add(new DepartmentFactory().factory("IT"));
        this.add(new DepartmentFactory().factory("Management"));
        this.add(new DepartmentFactory().factory("Marketing"));
        this.numeCompanie = numeCompanie;
        this.manager = manager;
    }

    public void add(Departament departament) {
        departaments.add(departament);
    }

    public void add(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public void add(Employee employee, Departament destDepartment) {
        destDepartment.add(employee);
    }

    public void remove(Employee employee) {
        for(Departament d : departaments)
            for(Employee e : d.employees)
                if(employee.equals(e)) {
                    d.remove(e);
                    break;
            }
    }

    public void remove(Departament departament) {
        departaments.remove(departament);
    }

    public void remove(Recruiter recruiter) {
        recruiters.remove(recruiter);
}

    public void move(Departament source, Departament destination) {
        for(Employee employee : source.employees) {
            destination.add(employee);
        }
        Departament departamentDeSters = null;
        for(Departament departament : departaments) {
            if(departament.equals(source)) {
                departamentDeSters = departament;
                break;
            }
        }
        departaments.remove(departamentDeSters);
    }

    public void move(Employee employee, Departament newDepartment) {
        for(Departament d : this.departaments)
            for(Employee e : d.employees)
                if(employee.equals(e))
                    d.remove(e);
        newDepartment.add(employee);
    }

    public boolean contains(Departament departament) {
        for(Departament d : departaments)
            if(departament.equals(d))
                return true;
        return false;
    }

    public boolean contains(Employee employee) {
        for(Departament d : departaments)
            for(Employee e : d.employees)
                if(employee.equals(e))
                    return true;
        return false;
    }

    public boolean contains(Recruiter recruiter) {
        for(Recruiter r : recruiters)
            if(recruiter.equals(r))
                return true;
        return false;
    }

    public Recruiter getRecruiter(User user) {
        //gaseste cel mai departat dpdv social
        //incep cu primul recruter si compar cu restul
        Recruiter recruiterPotrivit = recruiters.get(0);
        int maxDegreeInFriendship = recruiterPotrivit.getDegreeInFriendship(user);
        for(Recruiter recruiter : recruiters) {
            int degree = recruiter.getDegreeInFriendship(user);

            //primul criteriu : distanta din reteaua sociala
            if(maxDegreeInFriendship < degree) {
                maxDegreeInFriendship = degree;
                recruiterPotrivit = recruiter;
            }
            else // al doilea criteriu : scorul recruterului
                if(maxDegreeInFriendship == degree) {
                    if(recruiter.getRating() > recruiterPotrivit.getRating())
                        recruiterPotrivit = recruiter;
                }
        }

        return recruiterPotrivit;
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> joburiDisponibile = new ArrayList<>();
        for(Departament departament : departaments) {
            //adaug in array joburile disponibile
            joburiDisponibile.addAll(departament.getJobs());
        }
        return joburiDisponibile; //de la fiecare departament
    }

    public IT getITDepartment() {  //adaugat de mine pentru testare
        for(Departament departament : departaments) {
            if(departament.getClass().getSimpleName().equals("IT"))
                return (IT) departament;

        }
    return null;
    }


    //observer pattern
    void subscribe(User user) {
        candidates.add(user);
    }

    void unsubscribe(User user) {
        for(Company company : Aplication.getInstance().companies)
            company.candidates.remove(user);
    }
    //transmit userilor candidati ca un mesaj
    public void notify(User candidat, String notification) {
        for(User user : candidates) {
                user.update("[" + user.cod + "'s" + " imbox]:"
                        + "From: " + numeCompanie + "| msj: " + notification);
        }
    }
    //pentru interfata grafica
    @Override
    public String toString() {
        return numeCompanie;
    }
}
