package entities;

public class Parking {
    private int idParking;
    private String nom;
    private int capacite;
    private String type;
    private int nombreActuelles;

    public Parking() {
    }

    public Parking(String nom, int capacite, String type, int nombreActuelles) {
        this.nom = nom;
        this.capacite = capacite;
        this.type = type;
        this.nombreActuelles = nombreActuelles;
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

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNombreActuelles() {
        return nombreActuelles;
    }

    public void setNombreActuelles(int nombreActuelles) {
        this.nombreActuelles = nombreActuelles;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "idParking=" + idParking +
                ", nom='" + nom + '\'' +
                ", capacite=" + capacite +
                ", type='" + type + '\'' +
                ", nombreActuelles=" + nombreActuelles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parking parking = (Parking) o;

        if (idParking != parking.idParking) return false;
        if (capacite != parking.capacite) return false;
        if (nombreActuelles != parking.nombreActuelles) return false;
        if (nom != null ? !nom.equals(parking.nom) : parking.nom != null) return false;
        return type != null ? type.equals(parking.type) : parking.type == null;
    }

    @Override
    public int hashCode() {
        int result = idParking;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + capacite;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + nombreActuelles;
        return result;
    }
}
