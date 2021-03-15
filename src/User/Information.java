package User;

import java.awt.*;
import java.time.LocalDate;

import java.util.ArrayList;


public class Information {
    private final String nume;
    private final String email;
    private final String telefon;
    private final LocalDate dataNastere;
    private final String sex;
    private ArrayList<String> limbiCunoscute;
    private ArrayList<String> nivelLimbiCunoscute;

    public Information(String nume, String email, String telefon,
                       LocalDate dataNastere, String sex, ArrayList limbiCunoscute, ArrayList nivelLimbiCunoscute) {
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
        this.dataNastere = dataNastere;
        this.sex = sex;
        this.limbiCunoscute = limbiCunoscute;
        this.nivelLimbiCunoscute = nivelLimbiCunoscute;
    }

    public String getNume() {
        return nume;
    }
}
