package services;

import entities.Parking;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceParking implements IService<Parking> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Parking p) throws SQLException {
        String req = "INSERT INTO parking (nom, type, capacite, nombreActuelles) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, p.getNom());
            st.setString(2, p.getType());
            st.setInt(3, p.getCapacite());
            st.setInt(4, p.getNombreActuelles());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    p.setIdParking(rs.getInt(1));
                }
                System.out.println("Parking ajouté !");
            } else {
                System.out.println("Échec de l'ajout du parking.");
            }
        }
    }

    @Override
    public void modifier(Parking p) throws SQLException {
        String req = "UPDATE parking SET nom=?, type=?, capacite=?, nombreActuelles=? WHERE idParking=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, p.getNom());
            st.setString(2, p.getType());
            st.setInt(3, p.getCapacite());
            st.setInt(4, p.getNombreActuelles());
            st.setInt(5, p.getIdParking());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Parking modifié !");
            } else {
                System.out.println("Échec de la modification du parking.");
            }
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM parking WHERE idParking=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Parking supprimé !");
            } else {
                System.out.println("Échec de la suppression du parking.");
            }
        }
    }

    @Override
    public Parking getOneById(int id) throws SQLException {
        Parking parking = null;
        String req = "SELECT * FROM parking WHERE idParking=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                parking = new Parking();

                parking.setIdParking(rs.getInt("idParking"));
                parking.setNom(rs.getString("nom"));
                parking.setType(rs.getString("type"));
                parking.setCapacite(rs.getInt("capacite"));
                parking.setNombreActuelles(rs.getInt("nombreActuelles"));
            }
        }

        return parking;
    }

    @Override
    public Set<Parking> getAll() throws SQLException {
        Set<Parking> parkings = new HashSet<>();
        String req = "SELECT * FROM parking";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Parking parking = new Parking();

                parking.setIdParking(rs.getInt("idParking"));
                parking.setNom(rs.getString("nom"));
                parking.setType(rs.getString("type"));
                parking.setCapacite(rs.getInt("capacite"));
                parking.setNombreActuelles(rs.getInt("nombreActuelles"));

                parkings.add(parking);
            }
        }

        return parkings;
    }
}
