package Aplication;

import Company.*;
import Exceptii.InvalidDatesException;
import Exceptii.ResumeIncompleteException;
import Interfete.AdminPage;

import Interfete.ManagerPage;
import User.Consumer;
import User.*;
import User.User;
import org.json.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;


public class TestFor {



    public static void main(String[] args) throws IOException, JSONException {

        String path1 = "/home/marian/an2/sem1/poo/tema/323CC_Noaghea_Marian/PROIECT/json/consumers.json";
        String path2 = "/home/marian/an2/sem1/poo/tema/323CC_Noaghea_Marian/PROIECT/json/jobs.json";
        String path3 = "/home/marian/an2/sem1/poo/tema/323CC_Noaghea_Marian/PROIECT/json/graph.txt";

        ArrayList<String> companies = new ArrayList<>();
        companies.add("Google");
        companies.add("Amazon");

        Aplication aplication = Aplication.getInstance();

        BazaDate bazaDate = new BazaDate();

        String jsonString = "";
        String jsonString2 = "";
        String jsonString3 = "";
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(path1)));
            jsonString2 = new String(Files.readAllBytes(Paths.get(path2)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject jsonObject2 = new JSONObject(jsonString2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        ArrayList<String> stringTypeConsumers = new ArrayList<>();
        stringTypeConsumers.add("recruiters");
        stringTypeConsumers.add("employees");
        stringTypeConsumers.add("managers");
        stringTypeConsumers.add("users");

        Consumer consumer;

        for(String consumerType : stringTypeConsumers) {
            JSONArray jsonConsumers = jsonObject.getJSONArray(consumerType);

            //creez instante diferite pentru fiecare tip de consumer
            for (int i = 0; i < jsonConsumers.length(); i++) {

                if(consumerType.equals("employees")) {
                    consumer = new Employee();
                    consumer.cod += "E" + (i + 1);
                }
                else
                    if(consumerType.equals("recruiters")) {
                        consumer = new Recruiter();
                        consumer.cod += "R" + (i + 1);
                    }

                    else
                        if(consumerType.equals("users")) {
                            consumer = new User();
                            consumer.cod += "U" + (i + 1);
                        }
                        else
                            consumer = new Manager();


                ArrayList<String> limbiStraine = new ArrayList<>();
                ArrayList<String> nivelLimbiStraine = new ArrayList<>();

                JSONArray limbiCunoscute = jsonConsumers.getJSONObject(i).getJSONArray("languages");
                for (int j = 0; j < limbiCunoscute.length(); j++) {
                    limbiStraine.add(jsonConsumers.getJSONObject(i).getJSONArray("languages").getString(j));
                    nivelLimbiStraine.add(jsonConsumers.getJSONObject(i)
                            .getJSONArray("languages_level").getString(j));
                }

                Information information = new Information(
                        jsonConsumers.getJSONObject(i).getString("name"),
                        jsonConsumers.getJSONObject(i).getString("email"),
                        jsonConsumers.getJSONObject(i).getString("phone"),
                        LocalDate.parse(jsonConsumers.getJSONObject(i).getString("date_of_birth"), formatter),
                        jsonConsumers.getJSONObject(i).getString("genre"),
                        limbiStraine,
                        nivelLimbiStraine);


                if(! (consumer instanceof User)) {
                    ((Employee) consumer).setSalary(jsonConsumers.getJSONObject(i).getDouble("salary"));
                }

                JSONArray educations = jsonConsumers.getJSONObject(i).getJSONArray("education");

                TreeSet<Education> educationTreeSet = new TreeSet<>();
                TreeSet<Experience> experienceTreeSet = new TreeSet<>();

                for (int j = 0; j < educations.length(); j++) {
                    JSONObject educatie = educations.getJSONObject(j);
                    Education education;
                    try {
                        if (educatie.get("end_date") instanceof String)
                            education = new Education(LocalDate.parse(educatie.getString("start_date"), formatter),
                                    LocalDate.parse(educatie.getString("end_date"), formatter),
                                    educatie.getString("name"),
                                    educatie.getString("level"),
                                    educatie.getDouble("grade")
                            );
                        else
                            education = new Education(LocalDate.parse(educatie.getString("start_date"), formatter),
                                    null,
                                    educatie.getString("name"),
                                    educatie.getString("level"),
                                    educatie.getDouble("grade")
                            );

                        educationTreeSet.add(education);

                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                }

                JSONArray experiences = jsonConsumers.getJSONObject(i).getJSONArray("experience");

                for (int j = 0; j < experiences.length(); j++) {
                    JSONObject experiena = experiences.getJSONObject(j);
                    Experience experience;

                    if(consumerType.equals("employees"))
                        ((Employee) consumer).setDepartament(experiena.getString("department"));
                    try {
                        if (experiena.get("end_date") instanceof String)
                            experience = new Experience(LocalDate.parse(experiena.getString("start_date"),
                                    formatter),
                                    LocalDate.parse(experiena.getString("end_date"), formatter),
                                    experiena.getString("company"),
                                    experiena.getString("position")
                            );
                        else {
                            experience = new Experience(LocalDate.parse(experiena
                                    .getString("start_date"), formatter),
                                    null,
                                    experiena.getString("company"),
                                    experiena.getString("position")
                            );

                            if(! (consumer instanceof User))
                                ((Employee) consumer).setCompanyName(experiena.getString("company"));
                        }
                        experienceTreeSet.add(experience);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }



                }
                //Builder Pattern
                Consumer.Resume resume = null;
                try {
                    resume = new Consumer.Resume.ResumeBuilder()
                            .information(information)
                            .educations(educationTreeSet)
                            .experiences(experienceTreeSet)
                            .build();
                } catch (ResumeIncompleteException e) {
                    e.printStackTrace();
                }
                consumer.resume = resume;

                aplication.add(consumer);
                if(consumerType.equals("employees"))
                    bazaDate.addEmployee((Employee) consumer);
                else
                    if(consumerType.equals("recruiters"))
                        bazaDate.addRecruiter((Recruiter) consumer);
                    else
                    if(consumerType.equals("managers"))
                        bazaDate.addManager((Manager) consumer);
                    else {
                        JSONArray jsonArraycompanies = jsonConsumers.getJSONObject(i)
                                .getJSONArray("interested_companies");
                        for(int j = 0; j < jsonArraycompanies.length(); j++)
                            ((User)consumer).companiesOfInterest.add(jsonArraycompanies.getString(j));
                        bazaDate.addUser((User) consumer);
                    }

            }
        }

        //Salvarea joburilor din jsonul creat de mine
        Job newJob;

        for(String companyName : companies) {

            JSONArray jsonJobs = jsonObject2.getJSONArray(companyName);

            for (int i = 0; i < jsonJobs.length(); i++) {


                Constraint constraintAbsolvire;
                Constraint constraintExperienta;
                Constraint constraintMedie;

                Integer minAbsolvire, maxAbsolvire, minExperienta, maxExperienta;
                Double minAverage, maxAverage;

                if(jsonJobs.getJSONObject(i).getJSONObject("constrAnAbsolvire").get("min").equals("null"))
                    minAbsolvire = null;
                else
                    minAbsolvire = jsonJobs.getJSONObject(i).getJSONObject("constrAnAbsolvire").getInt("min");

                if(jsonJobs.getJSONObject(i).getJSONObject("constrAnAbsolvire").get("max").equals("null"))
                    maxAbsolvire = null;
                else
                    maxAbsolvire = jsonJobs.getJSONObject(i).getJSONObject("constrAnAbsolvire").getInt("max");

                if(jsonJobs.getJSONObject(i).getJSONObject("constrAniExperienta").get("min").equals("null"))
                    minExperienta = null;
                else
                    minExperienta = jsonJobs.getJSONObject(i).getJSONObject("constrAniExperienta").getInt("min");

                if(jsonJobs.getJSONObject(i).getJSONObject("constrAniExperienta").get("max").equals("null"))
                    maxExperienta = null;
                else
                    maxExperienta = jsonJobs.getJSONObject(i).getJSONObject("constrAniExperienta").getInt("max");
                if(jsonJobs.getJSONObject(i).getJSONObject("constrMedieAcademica").get("min").equals("null"))
                    minAverage = null;
                else
                    minAverage = jsonJobs.getJSONObject(i).getJSONObject("constrMedieAcademica").getDouble("min");

                if(jsonJobs.getJSONObject(i).getJSONObject("constrMedieAcademica").get("max").equals("null"))
                    maxAverage = null;
                else
                    maxAverage = jsonJobs.getJSONObject(i).getJSONObject("constrMedieAcademica").getDouble("max");


                constraintAbsolvire = new Constraint(minAbsolvire, maxAbsolvire);
                constraintExperienta = new Constraint(minExperienta, maxExperienta);
                constraintMedie = new Constraint(minAverage, maxAverage);


                JSONObject jsonConstraint = jsonJobs.getJSONObject(i).getJSONObject("constrAnAbsolvire");
                newJob = new Job(jsonJobs.getJSONObject(i).getString("numeJob"),
                        jsonJobs.getJSONObject(i).getString("numeCompanie"),
                        constraintAbsolvire,
                        constraintExperienta,
                        constraintMedie,
                        jsonJobs.getJSONObject(i).getDouble("salariu")
                );
                newJob.noPositions = jsonJobs.getJSONObject(i).getInt("noPositions");
                newJob.eDeschis = true;
                bazaDate.addJob(newJob);
            }
        }

        int size = aplication.getNumberOfUsers();
        aplication.graph = new Integer[size][size];

        //citesc matricea de adiacenta pentru reteaua sociala
        Scanner inFile = new Scanner(new File(path3));
        for(int r = 0; r <size; r++) {
            for(int c = 0; c < size; c++) {
                if(inFile.hasNextInt()) {
                    aplication.graph[r][c] = inFile.nextInt();
                }
            }
        }

        //introduc datele din bazaDate in aplicatie
        for(Manager manager : bazaDate.managers) {
            aplication.add(new Company(manager.getNumeCompanie(), manager));
        }

        Departament itDepartment = null;

        for(Company company : aplication.companies) {
            for(Departament departament : company.departaments) {
                if(departament.numeDepartament.equals("IT")) {
                    itDepartment = departament;
                    break;
                }
            }
            for(Recruiter recruiter : bazaDate.recruiters) {
                if(((recruiter).getNumeCompanie().equals(company.numeCompanie))) {
                    company.add(recruiter);
                    itDepartment.add(recruiter);
                }
            }

            for(Departament departament : company.departaments) {
                for(Employee employee : bazaDate.employees)
                    if(departament.getClass().getSimpleName().equals(employee.getDepartmentName())
                            && company.numeCompanie.equals(employee.getNumeCompanie()))
                        departament.add(employee);

                for(Job job : bazaDate.jobs)
                    if(job.numeCompanie.equals(company.numeCompanie)) {
                        if (departament.getClass().getSimpleName().equals("IT"))
                            departament.add(job);
                    }

                for(Manager manager : bazaDate.managers)
                    if(manager.getNumeCompanie().equals(company.numeCompanie))
                        if(departament.getClass().getSimpleName().equals("Management")) {
                            manager.cod = "CEO";
                            departament.add(manager);
                        }
            }
        }



        User user1 = bazaDate.users.get(0);
        User user2 = bazaDate.users.get(1);
        User user3 = bazaDate.users.get(2);
        User user4 = bazaDate.users.get(3);


        Company company1 = aplication.companies.get(0);
        Company company2 = aplication.companies.get(1);

        Job job1 = company1.getJobs().get(0);
        Job job2 = company1.getJobs().get(1);
        Job job3 = company2.getJobs().get(0);
        Job job4 = company2.getJobs().get(1);



        job1.apply(user3);
        job3.apply(user2);
        job1.apply(user2);
        job2.apply(user4);
        job1.apply(user3);
        job2.apply(user1);
        job4.apply(user4);
        job4.apply(user1);

        company1.manager.process(job1);
        company1.manager.process(job2);
        company2.manager.process(job3);
        company2.manager.process(job4);


        JFrame frame = new JFrame("Admin Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AdminPage());
        frame.pack();
        frame.setVisible(true);


        JFrame frame2 = new JFrame("Manager Page");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.add(new ManagerPage(aplication.getCompany("Amazon")));
        frame2.pack();
        frame2.setVisible(true);
    }
}
