package edu.esprit.entities;



import java.util.Date;
import java.util.Objects;

public class User {
    private int id_user;
    private String nom_user;
    private String prenom_user;
    private String email;
    private String mdp;
    private int num_tel;
    private Date date;

    private String role;
    public User(){

    }

    public User(String nom_user, String prenom_user, String email, String mdp, int num_tel, Date date, String role) {
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email = email;
        this.mdp = mdp;
        this.num_tel = num_tel;
        this.date = date;
        this.role = role;
    }

    public User(int id_user, String nom_user, String prenom_user, String email, String mdp, int num_tel, Date date, String role) {
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email = email;
        this.mdp = mdp;
        this.num_tel = num_tel;
        this.date = date;

        this.role = role;
    }

    public User(String nomUser, String prenomUser, String email, String mdp) {
    }

    public User(int idUser, String nomUser, String prenomUser, String email, String mdp) {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getPrenom_user() {
        return prenom_user;
    }

    public void setPrenom_user(String prenom_user) {
        this.prenom_user = prenom_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /*  @Override
      public String toString() {
          return "User{" +
                  "nom_user='" + nom_user + '\'' +
                  ", prenom_user='" + prenom_user + '\'' +
                  ", email='" + email + '\'' +

                  ", num_tel='" + num_tel + '\'' +
                  ", date=" + date +
                  ", cartepro='" + cartepro + '\'' +
                  ", role='" + role + '\'' +
                  '}';
      }
   */@Override
    public String toString() {
        return nom_user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId_user() == user.getId_user();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_user());
    }
}