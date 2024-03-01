package entities;

import java.util.Date;
import java.util.Objects;

public class Event {
    private Integer idEvent;
    private String title;
    private Date date;
    private int nbrPersonne;
    private String listeInvites; // Renamed variable
    private Espace espace;

    public Event() {
    }

    public Event(Integer idEvent, String title, Date date, int nbrPersonne, String listeInvites, Espace espace) {
        this.idEvent = idEvent;
        this.title = title;
        this.date = date;
        this.nbrPersonne = nbrPersonne;
        this.listeInvites = listeInvites;
        this.espace = espace;
    }

    public Event(String title, Date date, int nbrPersonne, String listeInvites, Espace espace) {
        this.title = title;
        this.date = date;
        this.nbrPersonne = nbrPersonne;
        this.listeInvites = listeInvites;
        this.espace = espace;
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

    public String getListeInvites() { // Getter renamed
        return listeInvites;
    }

    public void setListeInvites(String listeInvites) { // Setter renamed
        this.listeInvites = listeInvites;
    }
    public Espace getEspace() {
        return espace;
    }

    public void setEspace(Espace espace) {
        this.espace = espace;
    }

    @Override
    public String toString() {
        return "Event{" +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", nbrPersonne=" + nbrPersonne +
                ", listeInvites='" + listeInvites + '\'' + // Updated variable name in the output
                ", espace=" + espace +
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
                Objects.equals(listeInvites, event.listeInvites) && // Updated variable name
                Objects.equals(espace, event.espace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvent, title, date, nbrPersonne, listeInvites, espace);
    }
}