package edu.esprit.services;
import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;
import edu.esprit.entities.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IService<Reclamation> {
    private Connection cnx;

    public ServiceReclamation() {
        cnx = DataSource.getInstance().getCnx();
    }
    public static ServiceReclamation getInstance() {
        ServiceReclamation instance = new ServiceReclamation();
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }

    @Override
    public void ajouter(Reclamation r) throws SQLException {
        if (r == null || r.getUser() == null) {
            System.out.println("Reclamation or associated user is null. Cannot add to database.");
            return;
        }
        String requete = "INSERT INTO reclamation (idU, descriRec, DateRec, CategorieRec, StatutRec) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, r.getUser().getId_user());
            pst.setString(2, r.getDescriRec());
            pst.setDate(3, new java.sql.Date(r.getDateRec().getTime()));
            pst.setString(4, r.getCategorieRec());
            pst.setString(5, r.getStatutRec());
            pst.executeUpdate();
            System.out.println("Reclamation ajoutée!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Reclamation r) throws SQLException {
        String sql = "UPDATE reclamation SET idU=?, descriRec=?, DateRec=?, CategorieRec=?, StatutRec=? WHERE idRec=?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, r.getUser().getId_user());
            ps.setString(2, r.getDescriRec());
            ps.setDate(3, new java.sql.Date(r.getDateRec().getTime()));
            ps.setString(4, r.getCategorieRec());
            ps.setString(5, r.getStatutRec());
            ps.setInt(6, r.getIdRec());
            ps.executeUpdate();
            System.out.println("Reclamation modifiée!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int idRec) throws SQLException {
        String requete = "DELETE FROM Reclamation WHERE idRec=?";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, idRec);
            pst.executeUpdate();
            System.out.println("Reclamation supprimée!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reclamation getOneById(int id) throws SQLException {
        Reclamation r = null;
        String req = "SELECT * FROM Reclamation WHERE idRec = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            if (rst.next()) {
                r = new Reclamation();
                User u = new User();
                u.setId_user(rst.getInt("IdU"));
                r.setUser(u);
                r.setIdRec(rst.getInt("idRec"));
                r.setDescriRec(rst.getString("DescriRec"));
                r.setDateRec(rst.getDate("dateRec"));
                r.setCategorieRec(rst.getString("categorieRec"));
                r.setStatutRec(rst.getString("statutRec"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public List<Reclamation> getAll() throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT * FROM Reclamation";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                Reclamation r = new Reclamation();
                User user = new User();
                user.setId_user(rst.getInt("IdU"));
                r.setUser(user);
                r.setIdRec(rst.getInt("idRec"));
                r.setDescriRec(rst.getString("DescriRec"));
                r.setDateRec(rst.getDate("dateRec"));
                r.setCategorieRec(rst.getString("categorieRec"));
                r.setStatutRec(rst.getString("statutRec"));
                reclamations.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamations;
    }
}