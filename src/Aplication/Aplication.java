package Aplication;

import Company.Company;
import User.User;
import User.Consumer;
import Company.Job;

import java.util.ArrayList;
import java.util.List;

public class Aplication {
    public ArrayList<Company> companies;
    public ArrayList<Consumer> users;  /** am modificat din <User> in <Consumer> */
    public Integer[][] graph; /** putem face cunoscut graful deoarece fara Resume al utilizatorilor acesta
                                nu sugereaza ceva anume pentru utilizatori*/

    Aplication() {
        users = new ArrayList<>();
        companies = new ArrayList<>();
    }

    //singleton
    private static Aplication instance = null;

    public static Aplication getInstance() {
        if (instance == null) {
            instance = new Aplication();
        }
        return instance;
    }

    public int getNumberOfUsers() {
        return users.size();
    }

    public int getIndexOfUser(Consumer consumer) {
        return users.indexOf(consumer);
    }


    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public Company getCompany(String name) {
        for(Company company : companies)
            if(company.numeCompanie.compareTo(name) == 0)
                return company;
        return null;
    }

    public void add(Company company) {
        companies.add(company);
    }

    public void add(Consumer user) {
        users.add(user);
    }

    public boolean remove(Company company) {
        int index = users.indexOf(company);
        if(index != -1) {
            companies.remove(index);
            return true;
        }
        return false;
    }

    public boolean remove (User user) {
        int index = users.indexOf(user);
        if(index != -1) {
            users.remove(index);
            return true;
        }
        return false;
    }

    public ArrayList<Job> getJobs(List<String> companies) {
        ArrayList<Job> total_jobs = new ArrayList<>();

        for(String numeCompanie : companies)
            total_jobs.addAll(getCompany(numeCompanie).getJobs());

        return total_jobs;
    }
}
