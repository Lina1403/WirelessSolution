package entities;

import java.util.ArrayList;
import java.util.List;

public class Espace {
    private int idEspace;
    private String name;
    private String email;
    private String etat;
    private int capacite;
    private String description;
    private List<Event> numEvent = new ArrayList<>();

    public Espace(String name, String email, String etat, int capacite, String description) {
        this.name = name;
        this.email = email;
        this.etat = etat;
        this.capacite = capacite;
        this.description = description;
        this.numEvent = numEvent;
    }

    public Espace() {
    }

    public List<Event> getNumEvent() {
        return numEvent;
    }

    public void setNumEvent(List<Event> numEvent) {
        this.numEvent = numEvent;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return numEvent;
    }

    public void addEvent(Event event) {
        numEvent.add(event);
    }

    public void removeEvent(Event event) {
        numEvent.remove(event);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // Redéfinition de la méthode toString() pour afficher les informations de l'espace
    @Override
    public String toString() {
        return "Espace {idEspace=" + idEspace + ", nom=" + name + ", email=" + email + ", etat=" + etat + ", capacite=" + capacite + ", description=" + description + "}";
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Espace)) return false;
        Espace e = (Espace) o;
        return e.getName().equals(name) &&
                e.getEmail().equals(email) &&
                e.getEtat().equals(etat) &&
                e.getCapacite() == capacite &&
                e.getDescription().equals(description);
    }


}