package entities;

import java.util.Objects;

public class Parking {

    private int id_parking;
    private String place;
    private int nbr_place;
    private int capacite_parking;

    public Parking(int id_parking, String place, int nbr_place, int capacite_parking) {
        this.id_parking = id_parking;
        this.place = place;
        this.nbr_place = nbr_place;
        this.capacite_parking = capacite_parking;
    }

    public int getId_parking() {
        return id_parking;
    }

    public void setId_parking(int id_parking) {
        this.id_parking = id_parking;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getNbr_place() {
        return nbr_place;
    }

    public void setNbr_place(int nbr_place) {
        this.nbr_place = nbr_place;
    }

    public int getCapacite_parking() {
        return capacite_parking;
    }

    public void setCapacite_parking(int capacite_parking) {
        this.capacite_parking = capacite_parking;
    }

    @Override
    public String toString() {
        return "parking{" +
                "id_parking=" + id_parking +
                ", place='" + place + '\'' +
                ", nbr_place=" + nbr_place +
                ", capacite_parking=" + capacite_parking +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parking parking = (Parking) o;
        return id_parking == parking.id_parking &&
                nbr_place == parking.nbr_place &&
                capacite_parking == parking.capacite_parking &&
                Objects.equals(place, parking.place);
    }
}
