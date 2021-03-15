package Aplication;

import Company.Employee;
import Company.Job;
import Company.Manager;
import Company.Recruiter;
import User.User;

import java.util.ArrayList;

public class BazaDate {

    ArrayList<Employee> employees;
    ArrayList<Recruiter> recruiters;
    ArrayList<User> users;
    ArrayList<Manager> managers;
    ArrayList<Job> jobs;

    public BazaDate() {
        employees = new ArrayList<>();
        recruiters = new ArrayList<>();
        users = new ArrayList<>();
        managers = new ArrayList<>();
        jobs = new ArrayList<>();
    }

    void addEmployee(Employee consumer) {
        employees.add(consumer);
    }

    void addRecruiter(Recruiter consumer) {
        recruiters.add(consumer);
    }

    void addManager(Manager consumer) {
        managers.add(consumer);
    }

    void addUser(User consumer) {
        users.add(consumer);
    }

    void addJob(Job job) {
        jobs.add(job);
    }
}
