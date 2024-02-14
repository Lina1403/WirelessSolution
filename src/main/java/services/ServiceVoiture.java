package services;

import entities.voiture;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public   class ServiceVoiture implements IService<voiture> {
    Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(voiture v) {
        String req = "INSERT INTO voiture(id_voiture, nom, email, num_serie, marque, couleur) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, v.getId_voiture());
            ps.setString(2, v.getNom());
            ps.setString(3, v.getEmail());
            ps.setString(4, v.getNum_serie());
            ps.setString(5, v.getMarque());
            ps.setString(6, v.getCouleur());
            ps.executeUpdate();
            System.out.println("Voiture ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(voiture v) {
        String req = "UPDATE voiture SET nom=?, email=?, num_serie=?, marque=?, couleur=? WHERE id_voiture=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, v.getNom());
            ps.setString(2, v.getEmail());
            ps.setString(3, v.getNum_serie());
            ps.setString(4, v.getMarque());
            ps.setString(5, v.getCouleur());
            ps.setInt(6, v.getId_voiture());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Voiture modifiée avec succès !");
            } else {
                System.out.println("Aucune modification effectuée.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<voiture> getAll() {
        Set<voiture> voitures = new HashSet<>();

        String req = "SELECT * FROM voiture";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id_voiture = res.getInt("id_voiture");
                String nom = res.getString("nom");
                String email = res.getString("email");
                String num_serie = res.getString("num_serie");
                String marque = res.getString("marque");
                String couleur = res.getString("couleur");
                voiture voiture = new voiture(id_voiture, nom, email, num_serie, marque, couleur);
                voitures.add(voiture);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return voitures;
    }

    public voiture getvoitureById(int id) {
        voiture voitureById = null;

        try {
            String query = "SELECT * FROM voiture WHERE id_voiture=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id_voiture = resultSet.getInt("id_voiture");
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");
                String num_serie = resultSet.getString("num_serie");
                String marque = resultSet.getString("marque");
                String couleur = resultSet.getString("couleur");

                voitureById = new voiture(id_voiture, nom, email, num_serie, marque, couleur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voitureById;
    }


}
