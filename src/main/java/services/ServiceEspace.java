
package services;

import entities.Event;
import utils.DataSource;
import entities.Espace;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;



public class ServiceEspace implements IService<Espace> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Espace e) {
        Connection cnx = DataSource.getInstance().getCnx();
        try {
            PreparedStatement PS = cnx.prepareStatement("INSERT INTO espace(name,email,etat,capacite,description) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            PS.setString(1, e.getName()); // Correction : getName() au lieu de getIdEspace()
            PS.setString(2, e.getEmail());
            PS.setString(3, e.getEtat()); // Correction : getEtat() au lieu de getTypeEspace()
            PS.setInt(4, e.getCapacite());
            PS.setString(5, e.getDescription());
            PS.executeUpdate();

            // Récupérer l'ID auto-incrémenté généré par la base de données
            ResultSet generatedKeys = PS.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idEspace = generatedKeys.getInt(1);
                e.setIdEspace(idEspace);
            }

            System.out.println("Espace ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public Set<Espace> getAll() {
        Set<Espace> espaces = new HashSet<>();

        String req = "SELECT * FROM espace";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idEspace = res.getInt("idEspace");
                String name = res.getString("name");
                String email = res.getString("email");
                String etat = res.getString("etat");
                int capacite = res.getInt("capacite");
                String description = res.getString("description");
                Espace e = new Espace(name, email, etat, capacite, description);
                e.setIdEspace(idEspace);
                espaces.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return espaces;
    }


    @Override
    public void modifier(Espace e) { // Correction : utiliser Espace au lieu de E
        try {
            PreparedStatement PS = cnx.prepareStatement("UPDATE espace SET name=?, email=?, etat=?, capacite=?, description=? WHERE idEspace=?");

            PS.setString(1, e.getName()); // Correction : getName() au lieu de getNomEspace()
            PS.setString(2, e.getEmail()); // Correction : getEmail() au lieu de getAdresseEspace()
            PS.setString(3, e.getEtat()); // Correction : getEtat() au lieu de getTypeEspace()
            PS.setInt(4, e.getCapacite());
            PS.setString(5, e.getDescription());

            Integer espaceId = e.getIdEspace();
            if (espaceId != null) { // Vérifier si l'objet e a un identifiant
                PS.setInt(6, espaceId.intValue()); // Convertir l'Integer en int
            } else {
                System.out.println("L'objet Espace ne possède pas d'identifiant.");
                return; // Sortir de la méthode si l'objet n'a pas d'identifiant
            }

            PS.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public Espace getOneByName(String E) {
        Espace espaceByName = null;

        try {
            PreparedStatement PS = cnx.prepareStatement("SELECT * FROM espace WHERE name=?");
            PS.setString(1, E);
            ResultSet res = PS.executeQuery();

            if (res.next()) {
                int idEspace = res.getInt("idEspace");
                String name = res.getString("name");
                String email = res.getString("email");
                String etat = res.getString("Etat");
                int capacite = res.getInt("capacite");
                String description = res.getString("description");
                espaceByName = new Espace(name, email, etat, capacite, description);
                espaceByName.setName(name);
                espaceByName.setIdEspace(idEspace);
                espaceByName.setEmail(email);
                espaceByName.setEtat(etat);
                espaceByName.setCapacite(capacite);
                espaceByName.setDescription(description);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());


        }

        return espaceByName;
    }

    public void supprimer(String E) {
        try {
            PreparedStatement PS = cnx.prepareStatement("DELETE FROM espace WHERE name=?");
            PS.setString(1, E);
            PS.executeUpdate();
            System.out.println("Espace supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
