package services;

import entities.Parking;
import entities.Voiture;
import utils.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceParking implements IService<Parking> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Parking v) {
        String req = "INSERT INTO parking(place, nbr_place, capacite_parking) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, v.getPlace());
            ps.setInt(2, v.getNbr_place());
            ps.setInt(3, v.getCapacite_parking());
            ps.executeUpdate();
            System.out.println("Place parking ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Parking v) {
        String req = "UPDATE parking SET place=?, nbr_place=?, capacite_parking=? WHERE id_parking=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, v.getPlace());
            ps.setInt(2, v.getNbr_place());
            ps.setInt(3, v.getCapacite_parking());
            ps.setInt(4, v.getId_parking());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Parking modifié avec succès !");
            } else {
                System.out.println("Aucune modification effectuée.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Set<Parking> getAll() {
        Set<Parking> parkings = new HashSet<>();

        String req = "SELECT * FROM parking";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id_parking = res.getInt("id_parking");
                String place = res.getString("place");
                int nbr_place = res.getInt("nbr_place");
                int capacite_parking = res.getInt("capacite_parking");
                Parking parking = new Parking(id_parking, place, nbr_place, capacite_parking);
                parkings.add(parking);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return parkings;
    }

    public Parking getParkingById(int id) {
        Parking parkingById = null;

        try {
            String query = "SELECT * FROM parking WHERE id_parking=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id_parking = resultSet.getInt("id_parking");
                String place = resultSet.getString("place");
                int nbr_place = resultSet.getInt("nbr_place");
                int capacite_parking = resultSet.getInt("capacite_parking");

                parkingById = new Parking(id_parking, place, nbr_place, capacite_parking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parkingById;
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM parking WHERE id_parking= ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id); // Set the id parameter
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Parking supprimé !");
            } else {
                System.out.println("Aucun parking trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la suppression du parking : " + e.getMessage());
        }
    }



    @Override
    public Parking getOneById(int id) {
        Parking ParkingById = null;

        try {
            String query = "SELECT * FROM parking WHERE id_parking=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id_parking = resultSet.getInt("id_parking");
                String place = resultSet.getString("place");
                int nbr_place = resultSet.getInt("nbr_place");
                int capacite_parking = resultSet.getInt("capacite_parking");


                ParkingById = new Parking(id_parking, place, nbr_place, capacite_parking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ParkingById;
    }
}







