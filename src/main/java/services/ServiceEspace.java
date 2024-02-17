package services;

import entities.Event;
import utils.DataSource;
import entities.Espace;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEspace implements IService<Espace> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Espace espace) {
        String req = "INSERT INTO `espace` (`name`, `etat`, `capacite`, `description`, `numEspace`) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, espace.getName());
            st.setString(2, espace.getEtat());
            st.setInt(3, espace.getCapacite());
            st.setString(4, espace.getDescription());
            st.setInt(5, espace.getNumEspace());
            st.executeUpdate();
            System.out.println("Espace added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Set<Espace> getAll() throws SQLException {
        Set<Espace> espaces = new HashSet<>();

        String req = "SELECT * FROM espace";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {
            int idEspace = res.getInt("idEspace");
            String name = res.getString("name");
            String etat = res.getString("etat");
            int capacite = res.getInt("capacite");
            String description = res.getString("description");
            int numEspace = res.getInt("numEspace");
            Espace e = new Espace(name, etat, capacite, description, numEspace);
            e.setIdEspace(idEspace);
            espaces.add(e);
        }

        return espaces;
    }

    @Override
    public void modifier(Espace e) throws SQLException {
        String req = "UPDATE `espace` SET " +
                "`name`=?," +
                "`etat`=?," +
                "`capacite`=?," +
                "`description`=?," +
                "`numEspace`=?" +
                " WHERE `idEspace`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, e.getName());
            st.setString(2, e.getEtat());
            st.setInt(3, e.getCapacite());
            st.setString(4, e.getDescription());
            st.setInt(5, e.getNumEspace());
            st.setInt(6, e.getIdEspace());
            st.executeUpdate();
            System.out.println("Espace updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public Espace getOneById(int id) throws SQLException {
        Espace espaceById = null;

        PreparedStatement PS = cnx.prepareStatement("SELECT * FROM espace WHERE idEspace=?");
        PS.setInt(1, id);
        ResultSet res = PS.executeQuery();

        if (res.next()) {
            String name = res.getString("name");
            String EtatEspace = res.getString("etat");
            int capacite = res.getInt("capacite");
            String description = res.getString("description");
            int numEspace = res.getInt("numEspace");
            espaceById = new Espace(name, EtatEspace, capacite, description, numEspace);
            espaceById.setIdEspace(id);
        }

        return espaceById;
    }

    @Override
    public void supprimer(int idEspace) throws SQLException {
        PreparedStatement PS = cnx.prepareStatement("DELETE FROM espace WHERE idEspace=?");
        PS.setInt(1, idEspace);
        PS.executeUpdate();
        System.out.println("Espace supprimé");
    }
}
