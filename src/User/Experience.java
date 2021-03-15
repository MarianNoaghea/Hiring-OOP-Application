package User;

import Exceptii.InvalidDatesException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Experience implements Comparable<Experience>{
    public LocalDate dataInceput;
    LocalDate dataSfarsit;
    String pozitieCompanie;
    String numeCompanie;
    String departament;

    public Experience(LocalDate dataInceput, LocalDate dataSfarsit, String pozitieCompanie,
                      String numeCompanie) throws InvalidDatesException {
        if(dataSfarsit != null)
        if(ChronoUnit.DAYS.between(dataInceput, dataSfarsit) < 0) {
            throw new InvalidDatesException("Eroare cronologica!");
        }

        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
        this.pozitieCompanie = pozitieCompanie;
        this.numeCompanie = numeCompanie;
    }


    @Override
    public int compareTo(Experience o) {
        if(dataSfarsit != null)
        if((int) ChronoUnit.DAYS.between( o.dataSfarsit, dataSfarsit) != 0)
            return (int) ChronoUnit.DAYS.between(dataSfarsit, o.dataSfarsit);
        return (int) o.numeCompanie.compareTo(numeCompanie);
    }

}
