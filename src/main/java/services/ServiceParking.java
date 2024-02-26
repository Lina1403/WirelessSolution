package services;

import entities.Parking;
import utils.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


public class ServiceParking implements IService<Parking> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public int ajouter(Parking p) throws SQLException {
        String req = "INSERT INTO parking (nom,capacite , type, nombreActuelles) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, p.getNom());
            st.setInt(2, p.getCapacite());
            st.setString(3, p.getType());
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
        return 0;
    }

    @Override
    public void modifier(Parking p) throws SQLException {
        String req = "UPDATE parking SET nom = ?, capacite = ?, type = ?, nombreActuelles = ? WHERE idParking = ?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, p.getNom());
            st.setInt(2, p.getCapacite());
            st.setString(3, p.getType());
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

    public boolean existeNomParking(String nom) throws SQLException {
        String req = "SELECT COUNT(*) FROM parking WHERE nom = ?";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, nom);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
        return false;
    }
    public List<String> getTypes() throws SQLException {
        List<String> types = new ArrayList<>();

        String req = "SELECT DISTINCT type FROM parking";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                types.add(rs.getString("type"));
            }
        }

        return types;
    }
    public List<String> getParkingsByType(String type) throws SQLException {
        List<String> parkings = new ArrayList<>();

        String req = "SELECT nom FROM parking WHERE type = ?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, type);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                parkings.add(rs.getString("nom"));
            }
        }

        return parkings;
    }
    public Parking getParkingByName(String name) throws SQLException {
        String query = "SELECT * FROM parking WHERE nom = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Parking parking = new Parking();
                parking.setIdParking(resultSet.getInt("idParking"));
                parking.setNom(resultSet.getString("nom"));
                parking.setType(resultSet.getString("type"));
                // Vous pouvez définir d'autres propriétés du parking ici si nécessaire
                return parking;
            }
        }
        return null; // Retourner null si aucun parking n'est trouvé avec ce nom
    }

}
