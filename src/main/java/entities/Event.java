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
    private String statutEvent;
    private String description;
    private Integer numEspace;

    public Event() {
    }

    public Event(Integer idEvent, String name, String email, String title, Date date, int nbrPersonne, String statutEvent, String description, Integer numEspace) {
        this.idEvent = idEvent;
        this.name = name;
        this.email = email;
        this.title = title;
        this.date = date;
        this.nbrPersonne = nbrPersonne;
        this.statutEvent = statutEvent;
        this.description = description;
        this.numEspace = numEspace;
    }

    public Event(String name, String email, String title, Date date, int nbrPersonne, String statutEvent, String description, Integer numEspace) {
        this.name = name;
        this.email = email;
        this.title = title;
        this.date = date;
        this.nbrPersonne = nbrPersonne;
        this.statutEvent = statutEvent;
        this.description = description;
        this.numEspace = numEspace;
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

    public String getStatutEvent() {
        return statutEvent;
    }

    public void setStatutEvent(String statutEvent) {
        this.statutEvent = statutEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumEspace() {
        return numEspace;
    }

    public void setNumEspace(Integer numEspace) {
        this.numEspace = numEspace;
    }

    @Override
    public String toString() {
        return "event{" +
                "idEvent=" + idEvent +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", nbrPersonne=" + nbrPersonne +
                ", statutEvent='" + statutEvent + '\'' +
                ", description='" + description + '\'' +
                ", numEspace=" + numEspace +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return nbrPersonne == event.nbrPersonne &&
                Objects.equals(idEvent, event.idEvent) &&
                Objects.equals(name, event.name) &&
                Objects.equals(email, event.email) &&
                Objects.equals(title, event.title) &&
                Objects.equals(date, event.date) &&
                Objects.equals(statutEvent, event.statutEvent) &&
                Objects.equals(description, event.description) &&
                Objects.equals(numEspace, event.numEspace);
    }


}
