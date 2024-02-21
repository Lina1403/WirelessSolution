package entities;

import java.util.Objects;

public class Voiture {
    private int id_voiture; // Change from int to Integer
    private String nom;
    private String email;
    private String numSerie;
    private String marque;
    private String couleur;
    private int numPlace; // Newly added attribute

    public Voiture() {
    }

    public Voiture(int id_voiture, String nom, String email, String numSerie, String marque, String couleur, int numPlace) {
        this.id_voiture = id_voiture;
        this.nom = nom;
        this.email = email;
        this.numSerie = numSerie;
        this.marque = marque;
        this.couleur = couleur;
        this.numPlace = numPlace;
    }

    public Voiture(String nom, String email, String numSerie, String marque, String couleur, int numPlace) {
        this.nom = nom;
        this.email = email;
        this.numSerie = numSerie;
        this.marque = marque;
        this.couleur = couleur;
        this.numPlace = numPlace;
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

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public int getNumPlace() {
        return numPlace;
    }

    public void setNumPlace(int numPlace) {
        this.numPlace = numPlace;
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
                ", numSerie='" + numSerie + '\'' +
                ", marque='" + marque + '\'' +
                ", couleur='" + couleur + '\'' +
                ", numPlace=" + numPlace +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voiture)) return false;
        Voiture voiture = (Voiture) o;
        return id_voiture == voiture.id_voiture &&
                numPlace == voiture.numPlace &&
                Objects.equals(nom, voiture.nom) &&
                Objects.equals(email, voiture.email) &&
                Objects.equals(numSerie, voiture.numSerie) &&
                Objects.equals(marque, voiture.marque) &&
                Objects.equals(couleur, voiture.couleur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_voiture, nom, email, numSerie, marque, couleur, numPlace);
    }
}
