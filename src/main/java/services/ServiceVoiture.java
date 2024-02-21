package services;

import entities.Voiture;
import entities.Parking;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceVoiture implements IService<Voiture>{
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Voiture v) throws SQLException {
        String req = "INSERT INTO voiture (nom, email, numSerie, marque, couleur, numPlace) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, v.getNom());
        st.setString(2, v.getEmail());
        st.setString(3, v.getNumSerie());
        st.setString(4, v.getMarque());
        st.setString(5, v.getCouleur());
        st.setInt(6, v.getNumPlace()); // Assuming numPlace is an integer

        st.executeUpdate();
        System.out.println("Voiture added !");
    }


    @Override
    public void modifier(Voiture v) throws SQLException {
        String req = "UPDATE voiture SET " +
                "id_voiture='" + v.getId_voiture() + "', " +
                "nom='" + v.getNom() + "', " +
                "email='" + v.getEmail() + "', " +
                "numSerie='" + v.getNumSerie() + "', " +
                "marque='" + v.getMarque() + "', " +
                "couleur='" + v.getCouleur() + "', " +
                "numPlace='" + v.getNumPlace() + "' " +
                "WHERE id_voiture=" + v.getId_voiture();

        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Voiture modified !");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM voiture WHERE id_voiture=" + id;

        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Voiture deleted !");
    }
    @Override
    public Voiture getOneById(int id) throws SQLException {
        Voiture voiture = null;
        String req = "SELECT * FROM voiture WHERE id_voiture=" + id;

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                voiture = new Voiture();

                // Check for null values before setting them
                int id_voiture = rs.getInt("id_voiture");
                if (!rs.wasNull()) {
                    voiture.setId_voiture(id_voiture);
                }

                String nom = rs.getString("nom");
                if (nom != null) {
                    voiture.setNom(nom);
                }

                String email = rs.getString("email");
                if (email != null) {
                    voiture.setEmail(email);
                }

                String numSerie = rs.getString("numSerie");
                if (numSerie != null) {
                    voiture.setNumSerie(numSerie);
                }

                String marque = rs.getString("marque");
                if (marque != null) {
                    voiture.setMarque(marque);
                }

                String couleur = rs.getString("couleur");
                if (couleur != null) {
                    voiture.setCouleur(couleur);
                }

                int numPlace = rs.getInt("numPlace");
                if (!rs.wasNull()) {
                    Parking parking = new Parking();
                    parking.setNumPlace(numPlace);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e; // Rethrow the exception to be handled at a higher level if necessary
        }

        return voiture;
    }


    @Override
    public Set<Voiture> getAll() throws SQLException {
        Set<Voiture> voitures = new HashSet<>();
        String req = "SELECT * FROM voiture";

        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            // Pour chaque ligne de résultat, créez une instance de Voiture et ajoutez-la à l'ensemble voitures
            Voiture voiture = new Voiture();

            voiture.setId_voiture(rs.getInt("id_voiture"));
            voiture.setNom(rs.getString("nom"));
            voiture.setEmail(rs.getString("email"));
            voiture.setNumSerie(rs.getString("numSerie"));
            voiture.setMarque(rs.getString("marque"));
            voiture.setCouleur(rs.getString("couleur"));
            if (rs.getInt("id_voiture") != 0) {
                Parking parking = new Parking();
                parking.setNumPlace(rs.getInt("numPlace"));
            }
            voitures.add(voiture);
        }
        return voitures;
    }



}
