package entities;

import java.sql.Timestamp;

public class Discussion {
    private int id;
    private String titre;
    private Timestamp TimeStampCreation;
    private User createur;


    public Discussion(){}
    public Discussion(int id, String titre, Timestamp TimeStampCreation, User createur) {
        this.id = id;
        this.titre = titre;
        this.TimeStampCreation = TimeStampCreation;
        this.createur = createur;
    }
    public Discussion(String titre, Timestamp TimeStampCreation, User createur) {
        this.titre = titre;
        this.TimeStampCreation = TimeStampCreation;
        this.createur = createur;
    }
    public Discussion(int id,String titre, Timestamp TimeStampCreation) {
        this.id = id;
        this.titre = titre;
        this.TimeStampCreation = TimeStampCreation;

    }
    public Discussion(String titre, Timestamp TimeStampCreation) {
        this.titre = titre;
        this.TimeStampCreation = TimeStampCreation;

    }
    public Discussion(int id,String titre) {
        this.id = id;
        this.titre = titre;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Timestamp getTimeStampCreation() {
        return TimeStampCreation;
    }

    public void setTimeStampCreation(Timestamp TimeStampCreation) {
        this.TimeStampCreation = TimeStampCreation;
    }

    public User getCreateur() {
        return createur;
    }

    public void setCreateur(User createur) {
        this.createur = createur;
    }

    @Override
    public String toString() {
        return "Discussion{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", TimeStampCreation=" + TimeStampCreation +
                ", createur=" + createur +
                '}';
    }
}
