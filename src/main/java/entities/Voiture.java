package entities;

import java.util.Objects;

public class Voiture {
    private int idVoiture;
    private int idResident; // Assuming this is the foreign key referring to the resident
    private String marque;
    private String model;
    private String couleur;
    private String matricule;
    private Parking parking; // Changed type from String to Parking

    public Voiture() {
    }

    public Voiture(int idResident, String marque, String model, String couleur, String matricule, Parking parking) {
        this.idResident = idResident;
        this.marque = marque;
        this.model = model;
        this.couleur = couleur;
        this.matricule = matricule;
        this.parking = parking;
    }

    public int getIdVoiture() {
        return idVoiture;
    }

    public void setIdVoiture(int idVoiture) {
        this.idVoiture = idVoiture;
    }

    public int getIdResident() {
        return idResident;
    }

    public void setIdResident(int idResident) {
        this.idResident = idResident;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "idVoiture=" + idVoiture +
                ", idResident=" + idResident +
                ", marque='" + marque + '\'' +
                ", model='" + model + '\'' +
                ", couleur='" + couleur + '\'' +
                ", matricule='" + matricule + '\'' +
                ", parking=" + parking +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voiture voiture = (Voiture) o;
        return idVoiture == voiture.idVoiture &&
                idResident == voiture.idResident &&
                Objects.equals(marque, voiture.marque) &&
                Objects.equals(model, voiture.model) &&
                Objects.equals(couleur, voiture.couleur) &&
                Objects.equals(matricule, voiture.matricule) &&
                Objects.equals(parking, voiture.parking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVoiture, idResident, marque, model, couleur, matricule, parking);
    }
}
