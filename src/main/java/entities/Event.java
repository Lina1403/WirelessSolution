package entities;

import java.util.Date;
import java.util.Objects;

public class Event {
    private Integer idEvent;
    private String name;
    private String email;
    private String title;
    private Date date;
    private int nbrPersonne;
    private String description;
    Espace espace ;
    public Event() {
    }



    public Event(Integer idEvent, String name, String email, String title, Date date, int nbrPersonne,  String description, Espace espace) {
        this.idEvent = idEvent;
        this.name = name;
        this.email = email;
        this.title = title;
        this.date = date;
        this.nbrPersonne = nbrPersonne;
        this.description = description;
        this.espace = espace;
    }

    public Event( String name, String email, String title, Date date, int nbrPersonne,  String description, Espace espace) {

        this.name = name;
        this.email = email;
        this.title = title;
        this.date = date;
        this.nbrPersonne = nbrPersonne;
        this.description = description;
        this.espace = espace;
    }


    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
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

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", nbrPersonne=" + nbrPersonne +
                ", description='" + description + '\'' +
                ", espace=" + espace +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name) && Objects.equals(title, event.title) && Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, title, date);
    }
}
