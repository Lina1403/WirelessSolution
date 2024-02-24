package entities;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Event {
    private Integer idEvent;
    private String title;
    private Date date;
    private int nbrPersonne;
    private String description;
    private Espace espace;
    private Time heure;

    public Event() {
    }

    public Event(Integer idEvent, String title, Date date, int nbrPersonne, String description, Espace espace, Time heure) {
        this.idEvent = idEvent;
        this.title = title;
        this.date = date;
        this.nbrPersonne = nbrPersonne;
        this.description = description;
        this.espace = espace;
        this.heure = heure;
    }

    public Event(String title, Date date, int nbrPersonne, String description, Espace espace, Time heure) {
        this.title = title;
        this.date = date;
        this.nbrPersonne = nbrPersonne;
        this.description = description;
        this.espace = espace;
        this.heure = heure;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNbrPersonne() {
        return nbrPersonne;
    }

    public void setNbrPersonne(int nbrPersonne) {
        this.nbrPersonne = nbrPersonne;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Espace getEspace() {
        return espace;
    }

    public void setEspace(Espace espace) {
        this.espace = espace;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    @Override
    public String toString() {
        return "Event{" +
                "idEvent=" + idEvent +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", nbrPersonne=" + nbrPersonne +
                ", description='" + description + '\'' +
                ", espace=" + espace +
                ", heure=" + heure +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return nbrPersonne == event.nbrPersonne &&
                Objects.equals(idEvent, event.idEvent) &&
                Objects.equals(title, event.title) &&
                Objects.equals(date, event.date) &&
                Objects.equals(description, event.description) &&
                Objects.equals(espace, event.espace) &&
                Objects.equals(heure, event.heure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvent, title, date, nbrPersonne, description, espace, heure);
    }
}
