package services;

import entities.Parking;
import entities.Voiture;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceVoiture {

    Connection cnx = DataSource.getInstance().getCnx();

    public int ajouter(Voiture v) throws SQLException {
        // Vérifier si le parking existe avant d'ajouter la voiture
        ServiceParking serviceParking = new ServiceParking();
        Parking parking = serviceParking.getOneById(v.getParking().getIdParking());

        if (parking != null) {
            String req = "INSERT INTO voiture (marque, model, couleur, matricule, idParking, idResident) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement st = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, v.getMarque());
                st.setString(2, v.getModel());
                st.setString(3, v.getCouleur());
                st.setString(4, v.getMatricule());
                st.setInt(5, v.getParking().getIdParking());
                st.setInt(6, v.getIdResident());

                int rowsAffected = st.executeUpdate();

                // Vérifier si l'insertion a été réussie
                if (rowsAffected > 0) {
                    // Récupérer l'identifiant de la voiture ajoutée
                    ResultSet generatedKeys = st.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int idVoiture = generatedKeys.getInt(1);
                        System.out.println("Voiture ajoutée avec l'identifiant : " + idVoiture);
                        return idVoiture;
                    } else {
                        System.out.println("Impossible de récupérer l'identifiant de la voiture ajoutée.");
                    }
                } else {
                    System.out.println("Aucune voiture ajoutée.");
                }
            }
        }
        return -1;
    }

    public void modifier(Voiture v) throws SQLException {
        String req = "UPDATE voiture SET " +
                "marque=?, " +
                "model=?, " +
                "couleur=?, " +
                "matricule=? " +
                "WHERE idVoiture=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, v.getMarque());
            st.setString(2, v.getModel());
            st.setString(3, v.getCouleur());
            st.setString(4, v.getMatricule());
            st.setInt(5, v.getIdVoiture());

            st.executeUpdate();
            System.out.println("Voiture modifiée !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de la voiture.");
        }
    }

    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM voiture WHERE idVoiture=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Voiture supprimée !");
            } else {
                System.out.println("Aucune voiture trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la suppression de la voiture : " + e.getMessage());
        }
    }

    public Voiture getOneById(int id) throws SQLException {
        Voiture voiture = null;
        String req = "SELECT * FROM voiture WHERE idVoiture=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                voiture = new Voiture();
                voiture.setIdVoiture(rs.getInt("idVoiture"));
                voiture.setIdResident(rs.getInt("idResident"));
                voiture.setMarque(rs.getString("marque"));
                voiture.setModel(rs.getString("model"));
                voiture.setCouleur(rs.getString("couleur"));
                voiture.setMatricule(rs.getString("matricule"));

                // Créer un service pour obtenir l'objet Parking à partir de son ID
                ServiceParking serviceParking = new ServiceParking();
                Parking parking = serviceParking.getOneById(rs.getInt("idParking"));

                // Attribuer l'objet Parking à l'objet Voiture
                voiture.setParking(parking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de la voiture.");
        }

        return voiture;
    }

    public Set<Voiture> getAll() throws SQLException {
        Set<Voiture> voitures = new HashSet<>();
        String req = "SELECT * FROM voiture";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Voiture voiture = new Voiture();
                voiture.setIdVoiture(rs.getInt("idVoiture"));
                voiture.setIdResident(rs.getInt("idResident"));
                voiture.setMarque(rs.getString("marque"));
                voiture.setModel(rs.getString("model"));
                voiture.setCouleur(rs.getString("couleur"));
                voiture.setMatricule(rs.getString("matricule"));

                // Récupérer les détails du parking associé à cette voiture
                int idParking = rs.getInt("idParking");
                ServiceParking serviceParking = new ServiceParking();
                Parking parking = serviceParking.getOneById(idParking);

                // Attribuer l'objet Parking à l'objet Voiture
                voiture.setParking(parking);

                voitures.add(voiture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des voitures.");
        }

        return voitures;
    }
    public boolean existeMatricule(String matricule) throws SQLException {
        String query = "SELECT COUNT(*) FROM voiture WHERE matricule = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, matricule);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

}
