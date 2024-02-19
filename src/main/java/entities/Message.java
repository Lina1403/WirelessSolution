package entities;

import java.sql.Date;
import java.util.Arrays;

public class Message {
    private int id ;
    private String contenu;
    private Date date_envoi;
    private User emetteur ;
    private Discussion discussion;
    private byte[] image;
    public Message(){}

    public Message(int id, String contenu, Date date_envoi, User emetteur, Discussion discussion, byte[] image) {
        this.id = id;
        this.contenu = contenu;
        this.date_envoi = date_envoi;
        this.emetteur = emetteur;
        this.discussion = discussion;
        this.image = image;
    }
    public Message(String contenu, Date date_envoi, User emetteur, Discussion discussion, byte[] image) {
        this.contenu = contenu;
        this.date_envoi = date_envoi;
        this.emetteur = emetteur;
        this.discussion = discussion;
        this.image = image;
    }
    public Message(String contenu, Date date_envoi, User emetteur, Discussion discussion) {
        this.contenu = contenu;
        this.date_envoi = date_envoi;
        this.emetteur = emetteur;
        this.discussion = discussion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate_envoi() {
        return date_envoi;
    }

    public void setDate_envoi(Date date_envoi) {
        this.date_envoi = date_envoi;
    }

    public User getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(User emetteur) {
        this.emetteur = emetteur;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", date_envoi=" + date_envoi +
                ", emetteur=" + emetteur +
                ", discussion=" + discussion +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
