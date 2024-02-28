package edu.esprit.services;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
public class ServiceReponse implements IService<Reponse> {
    private Connection cnx;
    private PreparedStatement pst;
    private Statement ste;

    public ServiceReponse() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouter(Reponse r) throws SQLException {
        String requete = "INSERT INTO reponse (id_reponse, id_rec, contenu, date_reponse) VALUES (?, ?, ?, ?)";

        pst = cnx.prepareStatement(requete);
        pst.setInt(1, r.getIdReponse());
        pst.setInt(2, r.getReclamation().getIdRec());
        pst.setString(3, r.getContenu());
        pst.setDate(4, new java.sql.Date(r.getDateReponse().getTime()));
        pst.executeUpdate();
        System.out.println("Reponse ajoutée !");
    }

    @Override
    public void modifier(Reponse r) throws SQLException {
        PreparedStatement ps = cnx.prepareStatement("UPDATE reponse SET id_rec = ?, contenu = ?, date_reponse = ? WHERE id_reponse = ?");
        ps.setInt(1, r.getReclamation().getIdRec());
        ps.setString(2, r.getContenu());
        ps.setDate(3, new java.sql.Date(r.getDateReponse().getTime()));
        ps.setInt(4, r.getIdReponse());

        ps.executeUpdate();
        System.out.println("Reponse modifiée !");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String requete = "DELETE FROM reponse WHERE id_reponse = ?";

        pst = cnx.prepareStatement(requete);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Reponse supprimée !");
    }

    @Override
    public Reponse getOneById(int id) throws SQLException {
        Reponse r = null;
        String requete = "SELECT * FROM reponse WHERE id_reponse = ?";

        pst = cnx.prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rst = pst.executeQuery();
        if (rst.next()) {
            r = new Reponse();
            r.setIdReponse(rst.getInt("id_reponse"));
            // You need to retrieve the associated Reclamation object from the database
            r.setContenu(rst.getString("contenu"));
            r.setDateReponse(rst.getDate("date_reponse"));
        }

        return r;
    }

    @Override
    public List<Reponse> getAll() throws SQLException {
        List<Reponse> reponses = new ArrayList<>();
        String requete = "SELECT * FROM reponse";

        ste = cnx.createStatement();
        ResultSet rst = ste.executeQuery(requete);
        while (rst.next()) {
            Reponse r = new Reponse();
            r.setIdReponse(rst.getInt("id_reponse"));
            // You need to retrieve the associated Reclamation object from the database
            r.setContenu(rst.getString("contenu"));
            r.setDateReponse(rst.getDate("date_reponse"));
            reponses.add(r);
        }

        return reponses;
    }
}