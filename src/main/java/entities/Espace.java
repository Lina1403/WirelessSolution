package entities;

import java.util.ArrayList;
import java.util.List;

public class Espace {
    private int idEspace;
    private String name;
    private String etat;
    private int capacite;
    private String description;
    private int numEspace; // Ajout de l'attribut numEspace

    private List<Event> listEvent = new ArrayList<>();

    public Espace(String name, String etat, int capacite, String description, int numEspace) {
        this.name = name;
        this.etat = etat;
        this.capacite = capacite;
        this.description = description;
        this.numEspace = numEspace;
        this.listEvent = listEvent;
    }

    public Espace() {
    }

    public List<Event> getNumEvent() {
        return listEvent;
    }

    public void setNumEvent(List<Event> numEvent) {
        this.listEvent = numEvent;
    }

    public int getIdEspace() {
        return idEspace;
    }

    public void setIdEspace(int idEspace) {
        this.idEspace = idEspace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public List<Event> getEvents() {
        return listEvent;
    }

    public void addEvent(Event event) {
        listEvent.add(event);
    }

    public void removeEvent(Event event) {
        listEvent.remove(event);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Ajout des méthodes getter et setter pour numEspace
    public int getNumEspace() {
        return numEspace;
    }

    public void setNumEspace(int numEspace) {
        this.numEspace = numEspace;
    }

    @Override
    public String toString() {
        return "Espace{" +
                "idEspace=" + idEspace +
                ", name='" + name + '\'' +
                ", etat='" + etat + '\'' +
                ", capacite=" + capacite +
                ", description='" + description + '\'' +
                ", numEspace=" + numEspace +
                ", numEvent=" + listEvent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Espace)) return false;
        Espace e = (Espace) o;
        return e.getName().equals(name) &&
                e.getEtat().equals(etat) &&
                e.getCapacite() == capacite &&
                e.getDescription().equals(description) &&
                e.getNumEvent() == listEvent &&
                e.getNumEspace() == numEspace;

    }
}
