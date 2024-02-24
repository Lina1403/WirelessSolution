package services;

import entities.Parking;
import entities.Voiture;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceVoiture implements IService<Voiture> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Voiture v) throws SQLException {
        // Vérifier si le parking existe avant d'ajouter la voiture
        ServiceParking serviceParking = new ServiceParking();
        Parking parking = serviceParking.getOneById(v.getParking().getIdParking());

        if (parking != null) {
            String req = "INSERT INTO voiture (idResident, marque, model, couleur, matricule, idParking) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement st = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
                st.setInt(1, v.getIdResident());
                st.setString(2, v.getMarque());
                st.setString(3, v.getModel());
                st.setString(4, v.getCouleur());
                st.setString(5, v.getMatricule());
                st.setInt(6, v.getParking().getIdParking());

                st.executeUpdate();
                System.out.println("Voiture ajoutée !");
            }
        }
    }

    @Override
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

    @Override
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
            System.out.println("Erreur lors de la suppression de la voiture.");
        }
    }

    @Override
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


    @Override
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
}
