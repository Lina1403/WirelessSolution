package entities;

public class Parking {
    private int idParking;
    private String place;
    private int numPlace;
    private int capacite;

    public Parking(String place, int numPlace, int capacite) {
        this.place = place;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

                ", place='" + place + '\'' +
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
                place.equals(parking.place);
    }
}
