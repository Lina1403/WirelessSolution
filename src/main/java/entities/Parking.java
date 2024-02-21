package entities;

public class Parking {
    private int idParking;
    private String nom;
    private int numPlace;
    private int capacite;

    public Parking(String nom, int numPlace, int capacite) {
        this.nom = nom;
        this.numPlace = numPlace;
        this.capacite = capacite;
    }

    public Parking() {
    }

    public int getIdParking() {
        return idParking;
    }

    public void setIdParking(int idParking) {
        this.idParking = idParking;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumPlace() {
        return numPlace;
    }

    public void setNumPlace(int numPlace) {
        this.numPlace = numPlace;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    @Override
    public String toString() {
        return "Parking{" +

                ", nom='" + nom + '\'' +
                ", numPlace=" + numPlace +
                ", capacite=" + capacite +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parking)) return false;
        Parking parking = (Parking) o;
        return idParking == parking.idParking &&
                numPlace == parking.numPlace &&
                capacite == parking.capacite &&
                nom.equals(parking.nom);
    }
}
