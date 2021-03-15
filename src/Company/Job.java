package Company;

import Aplication.Aplication;
import User.User;

import java.util.List;

public class Job {
    public String numeJob;
    public String numeCompanie;
    public boolean eDeschis = true;
    public Constraint constrAnAbsolvire;
    public Constraint constrAniExperienta;
    public Constraint constrMedieAcademica;
    public List<Request> listaCandidati;
    /** lista de candidati ne ajuta la task 3 pentru observer pattern*/
    public int noPositions;
    Double salariu;

    public Job(String numeJob, String numeCompanie, Constraint constrAnAbsolvire,
               Constraint constrAniExperienta, Constraint constrMedieAcademica, Double salariu) {
        this.numeJob = numeJob;
        this.numeCompanie = numeCompanie;
        this.constrAnAbsolvire = constrAnAbsolvire;
        this.constrAniExperienta = constrAniExperienta;
        this.constrMedieAcademica = constrMedieAcademica;
        this.salariu = salariu;
    }

    Aplication aplication = Aplication.getInstance();

    public void apply(User user) {
        Company companieMama = aplication.getCompany(this.numeCompanie);

        companieMama.candidates.add(user);
        Recruiter recruiterPotrivit = companieMama.getRecruiter(user);
        recruiterPotrivit.evaluate(this, user);
        System.out.println(" user: "  + user.cod + " inscris pt postul de " + this.numeJob + " la " +
                this.numeCompanie + " recrutat de " + recruiterPotrivit.cod);
    }

    public boolean meetsRequirments(User user) {
        //testez toate cerintele de angajare
        int requirments = 0;

        if (user.getGraduationYear() == null)
            if (constrAnAbsolvire.maxim != null || constrAnAbsolvire.minim != null)
                return false;

        if (constrAnAbsolvire.minim == null)
            requirments++;
        else if ((int) constrAnAbsolvire.minim-10 <= user.getGraduationYear())
            requirments++;

        if (constrAnAbsolvire.maxim == null)
            requirments++;
        else if ((int) constrAnAbsolvire.maxim+10 >= user.getGraduationYear())
            requirments++;

        if (constrAniExperienta.minim == null)
            requirments++;
        else if ((int) constrAniExperienta.minim <= user.getExperienceYears())
            requirments++;

        if (constrAniExperienta.maxim == null)
            requirments++;
        else if ((int) constrAniExperienta.maxim >= user.getExperienceYears())
            requirments++;

        if (constrMedieAcademica.minim == null)
            requirments++;
        else if ((Double) constrMedieAcademica.minim <= user.getTotalScore())
            requirments++;

        if (constrMedieAcademica.maxim == null)
            requirments++;
        else if ((Double) constrMedieAcademica.maxim >= user.getTotalScore())
            requirments++;
        //daca am acumulat toate cele 6 cerinte atunci poate fi evaluat mai departe
        if(requirments == 6)
            return true;


        return false;
    }
}
