package com.example;
import java.sql.Date;
import java.util.ArrayList;

public class Classe {

    public int numero;
    public String sezione;
    public String aula;
    public ArrayList<Alunno> alunni;
    
    public Classe(int numero, String sezione, String aula, ArrayList<Alunno> alunni) {
        this.numero = numero;
        this.sezione = sezione;
        this.aula = aula;
        this.alunni = alunni;
    }
    public Classe() {
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getSezione() {
        return sezione;
    }
    public void setSezione(String sezione) {
        this.sezione = sezione;
    }
    public String getAula() {
        return aula;
    }
    public void setAula(String aula) {
        this.aula = aula;
    }
    public ArrayList<Alunno> getAlunni() {
        return alunni;
    }

    public void setAlunni(ArrayList<Alunno> alunni) {
        this.alunni = alunni;
    }
}