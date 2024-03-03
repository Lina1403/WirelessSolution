package entities;

public class Concierge extends User {
    private String horraire_service;
    private String service;

    public Concierge() {

    }
    public String getHorraire_service() {
        return horraire_service;
    }

    public void setHorraire_service(String horraire_service) {
        this.horraire_service = horraire_service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Concierge(int id, String nom, String prenom, int number, String mail, String password, Role role, String horraire_service, String service) {
        super(id, nom, prenom, number, mail, password,role);
        this.horraire_service = horraire_service;
        this.service = service;
    }

    public Concierge(String nom, String prenom, int number, String mail, String password,Role role, String horraire_service, String service) {
        super(nom, prenom, number, mail, password,role);
        this.horraire_service = horraire_service;
        this.service = service;
    }

    @Override
    public String toString() {
        return "Concierge{" +
                "horraire_service='" + horraire_service + '\'' +
                ", service='" + service + '\'' +
        "nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' + '}';
    }
}
