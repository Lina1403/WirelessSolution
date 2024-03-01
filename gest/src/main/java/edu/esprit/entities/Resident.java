package edu.esprit.entities;


import javafx.scene.control.DatePicker;

import java.util.Date;

public class Resident extends User {
private int num_urgence;
private Date date_of_arrival;

    @Override
    public String toString() {
        return "Resident{" +

                ", num_urgence=" + num_urgence +
                ", contact_urgence='" + date_of_arrival + '\'' + "nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                '}';
    }

    public int getNum_urgence() {
        return num_urgence;
    }

    public void setNum_urgence(int num_urgence) {
        this.num_urgence = num_urgence;
    }

    public Date getDate_of_arrival() {
        return date_of_arrival;
    }

    public void setDate_of_arrival(Date date_of_arrival) {
        this.date_of_arrival = date_of_arrival;
    }

    public Resident()
    {

    }
    public Resident(int id, String nom, String prenom, int number, String mail, String password, int num_urgence, Date date_of_arrival, Role role)
    {
        super(id, nom, prenom, number, mail, password,role);
        this.num_urgence = num_urgence;
        this.date_of_arrival=date_of_arrival;
    }

    public Resident(String nom, String prenom, int number, String mail, String password, int num_urgence, Date date_of_arrival,Role role) {
        super(nom, prenom, number, mail, password,role);
        this.num_urgence = this.num_urgence;
        this.date_of_arrival= this.date_of_arrival;
    }
}
