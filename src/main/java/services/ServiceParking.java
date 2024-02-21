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
        String req = "INSERT INTO parking (nom, numPlace, capacite) VALUES (?, ?, ?)";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, p.getNom());
        st.setInt(2, p.getNumPlace());
        st.setInt(3, p.getCapacite());

        st.executeUpdate();
        System.out.println("Parking ajouté !");
    }

    @Override
    public void modifier(Parking p) throws SQLException {
        String req = "UPDATE parking SET " +
                "Nom='" + p.getNom() + "', " +
                "numPlace='" + p.getNumPlace() + "', " +
                "capacite='" + p.getCapacite() + "' " +
                "WHERE idParking=" + p.getIdParking();

        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Parking modifié !");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM parking WHERE idParking=" + id;

        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Parking supprimé !");
    }

    @Override
    public Parking getOneById(int id) throws SQLException {
        Parking parking = null;
        String req = "SELECT * FROM parking WHERE idParking=" + id;

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                parking = new Parking();

                parking.setIdParking(rs.getInt("idParking"));
                parking.setNom(rs.getString("Nom"));
                parking.setNumPlace(rs.getInt("numPlace"));
                parking.setCapacite(rs.getInt("capacite"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e; // Rethrow the exception to be handled at a higher level if necessary
        }

        return parking;
    }

    @Override
    public Set<Parking> getAll() throws SQLException {
        Set<Parking> parkings = new HashSet<>();
        String req = "SELECT * FROM parking";

        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Parking parking = new Parking();

            parking.setIdParking(rs.getInt("idParking"));
            parking.setNom(rs.getString("Nom"));
            parking.setNumPlace(rs.getInt("numPlace"));
            parking.setCapacite(rs.getInt("capacite"));

            parkings.add(parking);
        }
        return parkings;
    }
}
