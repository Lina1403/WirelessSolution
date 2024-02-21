package entities;

public class Parking {
    private int idParking;
    private String nom;
    private int type;
    private int capacite;

    public Parking(String nom, int type, int capacite) {
        this.nom = nom;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
                ", type=" + type +
                ", capacite=" + capacite +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parking)) return false;
        Parking parking = (Parking) o;
        return idParking == parking.idParking &&
                type == parking.type &&
                capacite == parking.capacite &&
                nom.equals(parking.nom);
    }
}