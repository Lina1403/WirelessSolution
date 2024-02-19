package entities;

import java.util.Objects;

public class Voiture {
    private int id_voiture; // Change from int to Integer
    private String nom;
    private String email;
    private String num_serie;
    private String marque;
    private String couleur;
    Parking parking ;
    public Voiture() {
    }

    public Voiture(int id_voiture, String nom, String email, String num_serie, String marque, String couleur, Parking parking ) {
        this.id_voiture = id_voiture;
        this.nom = nom;
        this.email = email;
        this.num_serie = num_serie;
        this.marque = marque;
        this.couleur = couleur;
    }

    public Voiture(  String nom, String email, String num_serie, String marque, String couleur, Parking parking) {
        this.nom = nom;
        this.email = email;
        this.num_serie = num_serie;
        this.marque = marque;
        this.couleur = couleur;
        this.parking =parking;
    }






    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getNum_serie() {
        return num_serie;
    }

    public void setNum_serie(String num_serie) {
        this.num_serie = num_serie;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }


    // Other attributes and methods

    public int getId_voiture() {
        return id_voiture;
    }

    public void setId_voiture(int id_voiture) {
        this.id_voiture = id_voiture;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", num_serie='" + num_serie + '\'' +
                ", marque='" + marque + '\'' +
                ", couleur='" + couleur + '\'' +
                ", parking=" + parking +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voiture)) return false;
        Voiture voiture = (Voiture) o;
        return
                Objects.equals(nom, voiture.nom) &&
                Objects.equals(email, voiture.email) &&
                Objects.equals(num_serie, voiture.num_serie) &&
                Objects.equals(marque, voiture.marque) &&
                Objects.equals(couleur, voiture.couleur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, email, num_serie, marque, couleur);
    }
}
