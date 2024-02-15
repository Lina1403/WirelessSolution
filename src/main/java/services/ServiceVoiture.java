package services;

import entities.Voiture;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceVoiture implements IService<Voiture> {
    Connection cnx = DataSource.getInstance().getCnx();

    public ServiceVoiture() {
    }
    @Override
    public void ajouter(Voiture v) {
        String req = "INSERT INTO Voiture(nom, email, num_serie, marque, couleur) VALUES (?, ?, ?, ?, ?)";
        try {
            if (v.getNom() != null) { // Vérifie que la valeur de la propriété "nom" n'est pas nulle
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, v.getNom());
                ps.setString(2, v.getEmail());
                ps.setString(3, v.getNum_serie());
                ps.setString(4, v.getMarque());
                ps.setString(5, v.getCouleur());
                ps.executeUpdate();
                System.out.println("Voiture ajoutée avec succès !");
            } else {
                System.out.println("La valeur de la propriété \"nom\" ne peut pas être nulle.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(Voiture v) {
        String req = "UPDATE Voiture SET nom=?, email=?, num_serie=?, marque=?, couleur=? WHERE id_voiture=?";
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
    public Voiture getOneById(int id) {
        Voiture voiture = null;
        String req = "SELECT * FROM `voiture` WHERE `id_voiture`=" + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                voiture = new Voiture("Voiture de test", "test@mail.com", "123456789", "TestMarque", "Noir");
                voiture .setCouleur (rs.getString("couleur "));
                voiture .setNom(rs.getString("nom"));
                voiture .setEmail(rs.getString("taille"));
                voiture .setNum_serie(rs.getString("Num_serie"));
                voiture .setMarque(rs.getString("Marque"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return voiture;
    }
    @Override
    public Set<Voiture> getAll() {
        Set<Voiture> voitures = new HashSet<>();

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
                Voiture voiture = new Voiture(id_voiture, nom, email, num_serie, marque, couleur);
                voitures.add(voiture);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return voitures;
    }

    public Voiture getvoitureById(int id) {
        Voiture voitureById = null;

        try {
            String query = "SELECT * FROM Voiture WHERE id_voiture=?";
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

                voitureById = new Voiture(id_voiture, nom, email, num_serie, marque, couleur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voitureById;
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `voiture` WHERE `id_voiture`=" + id;
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("voiture supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




}
