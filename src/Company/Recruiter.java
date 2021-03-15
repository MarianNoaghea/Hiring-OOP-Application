package Company;

import Aplication.Aplication;
import User.User;

public class Recruiter extends Employee {

    public static Double rating = 5d;

    public int evaluate(Job job, User user) {

        Aplication aplication = Aplication.getInstance();

        Double score = rating * user.getTotalScore();
        //creez o cerere de angajare
        Request cerere = new Request(job, user, this, score);

        Company company = aplication.getCompany(this.numeCompanie);
        //se trimite cererea catre manager
        company.manager.receiveRequest(cerere);

        rating += 0.1;
        return (int) Math.round(score);
    }

    public Double getRating() {
        return rating;
    }
}
