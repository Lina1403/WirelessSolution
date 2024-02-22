/*
package services;


import entities.Voiture;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceVoiture implements IService<Voiture>{
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Voiture v) throws SQLException {
        String req = "INSERT INTO voiture (marque, couleur, matricule, nomParking) VALUES (?, ?, ?, ?)";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, v.getMarque());
        st.setString(2, v.getCouleur());
        st.setString(3, v.getMatricule());
        st.setString(4, v.getNomParking());

        st.executeUpdate();
        System.out.println("Voiture added !");
    }


    @Override
    public void modifier(Voiture v) throws SQLException {
        String req = "UPDATE voiture SET " +
                "marque=?, " +
                "model=?, " +
                "couleur=?, " +
                "matricule=?, " +
                "nomParking=? " +
                "WHERE idVoiture=?";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, v.getMarque());
        st.setString(2, v.getModel());
        st.setString(3, v.getCouleur());
        st.setString(4, v.getMatricule());
        st.setString(5, v.getNomParking());
        st.setInt(6, v.getIdVoiture());

        st.executeUpdate();
        System.out.println("Voiture modifiée !");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM voiture WHERE idVoiture=?";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);

        st.executeUpdate();
        System.out.println("Voiture supprimée !");
    }

    @Override
    public Voiture getOneById(int id) throws SQLException {
        Voiture voiture = null;
        String req = "SELECT * FROM voiture WHERE idVoiture=?";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            voiture = new Voiture();
            voiture.setIdVoiture(rs.getInt("idVoiture"));
            voiture.setMarque(rs.getString("marque"));
            voiture.setModel(rs.getString("model"));
            voiture.setCouleur(rs.getString("couleur"));
            voiture.setMatricule(rs.getString("matricule"));
            voiture.setNomParking(rs.getString("nomParking"));
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
            Voiture voiture = new Voiture();
            voiture.setIdVoiture(rs.getInt("idVoiture"));
            voiture.setMarque(rs.getString("marque"));
            voiture.setModel(rs.getString("model"));
            voiture.setCouleur(rs.getString("couleur"));
            voiture.setMatricule(rs.getString("matricule"));
            voiture.setNomParking(rs.getString("nomParking"));
            voitures.add(voiture);
        }
        return voitures;
    }
}

 */
