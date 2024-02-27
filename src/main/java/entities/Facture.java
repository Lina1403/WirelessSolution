package entities;

import javafx.scene.control.Tooltip;

import java.util.Date;
import java.util.Objects;

public class Facture {
    public int idFacture ;
    private int numFacture;
    private Date date ;
    private Type type;
    private float montant;
    private String descriptionFacture;

    // La clé étrangère
    private Appartement appartement ;




    public enum Type {
        Eau, Gaz, Electricite, Dechets
    }
    public Facture(){

    }
    public Facture(int idFacture) {
        this.idFacture = idFacture;
    }
    public Facture(int idFacture, int numFacture, Date date, Type type, float montant, String descriptionFacture, Appartement appartement) {
        this.idFacture = idFacture;
        this.numFacture = numFacture;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.descriptionFacture = descriptionFacture;
        this.appartement = appartement;
    }


    public Facture(int numFacture, Date date, Type type, float montant, String descriptionFacture) {
        this.numFacture = numFacture;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.descriptionFacture = descriptionFacture;


    }
    public Facture(int idFacture,int numFacture, Date date, Type type, float montant, String descriptionFacture) {
        this.idFacture = idFacture;
        this.numFacture = numFacture;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.descriptionFacture = descriptionFacture;


    }
    public Facture(int numFacture, Date date, Type type, float montant, String descriptionFacture, Appartement appartement) {
        this.numFacture = numFacture;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.descriptionFacture = descriptionFacture;
        this.appartement = appartement ;

    }

    public int getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(int numFacture) {
        this.numFacture = numFacture;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getDescriptionFacture() {
        return descriptionFacture;
    }

    public void setDescriptionFacture(String descriptionFacture) {
        this.descriptionFacture = descriptionFacture;
    }

    public Appartement getAppartement() {
        return appartement;
    }

    public void setAppartement(Appartement appartement) {
        this.appartement = appartement;
    }

    public int getIdFacture() {
        return idFacture;
    }


    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Facture{" +
                "idFacture="+ idFacture +
                "numFacture=" + numFacture +
                "Date=" + date +
                ", type=" + type +
                ", montant=" + montant +
                ", descriptionFacture='" + descriptionFacture + '\'' +
                ", appartement=" + appartement +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facture facture = (Facture) o;
        return numFacture == facture.numFacture &&  type == facture.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numFacture,  type);
    }
}
