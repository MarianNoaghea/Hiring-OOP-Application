package User;

import Exceptii.InvalidDatesException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Education implements Comparable<Education>{
    LocalDate dataInceput;
    LocalDate dataSfarsit;
    String numeInstitutie;
    String nivelEducatie;
    Double medieFinala;

    public Education(LocalDate dataInceput, LocalDate dataSfarsit, String numeInstitutie,
                     String nivelEducatie, Double medieFinala) throws InvalidDatesException {

        if(dataSfarsit != null) {
            if (ChronoUnit.DAYS.between(dataInceput, dataSfarsit) < 0) {
                throw new InvalidDatesException("Eroare cronologica!");
            }
        }

        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
        this.numeInstitutie = numeInstitutie;
        this.nivelEducatie = nivelEducatie;
        this.medieFinala = medieFinala;
    }

    public Education(LocalDate dataInceput, String numeInstitutie, String nivelEducatie) {
        this.dataInceput = dataInceput;
        this.dataSfarsit = null;
        this.numeInstitutie = numeInstitutie;
        this.nivelEducatie = nivelEducatie;
        this.medieFinala = null;
    }
    @Override
    public int compareTo(Education o) {
        if(dataSfarsit != null)
        if((int) ChronoUnit.DAYS.between(o.dataSfarsit, dataSfarsit) != 0)
            return (int) ChronoUnit.DAYS.between(dataSfarsit, o.dataSfarsit); //sortat crescator
        return (int) (o.medieFinala - medieFinala); //sortat descrescator
    }
}
