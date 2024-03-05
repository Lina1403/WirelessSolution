package services;

import entities.Appartement;
import entities.Facture;
import entities.User;
import utils.DataSource;

import java.sql.*;
import java.util.*;
import java.util.Set;
public class ServiceAppartemment implements IService<Appartement> {
    private List<Facture> factures = new ArrayList<>();
    Connection cnx = DataSource.getInstance().getCnx();
    User user = new User(1, "Mohamed", User.Role.RESIDENT);
        @Override
        public void ajouter(Appartement p) throws SQLException {
            String req = "INSERT INTO `appartement` (`numAppartement`, `nomResident`, `taille`, `nbrEtage`, `statutAppartement`)" +
                    " VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, p.getNumAppartement());
            preparedStatement.setString(2, p.getNomResident());
            preparedStatement.setString(3, p.getTaille());
            preparedStatement.setInt(4, p.getNbrEtage());
            preparedStatement.setString(5, p.getStatutAppartement().toString());
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                p.setIdAppartement(rs.getInt(1));
            }

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appartement added successfully.");
            } else {
                System.out.println("Failed to insert Appartement.");
            }
        }


        @Override
        public void modifier(Appartement p) throws SQLException {
            String req = "UPDATE `appartement` SET `nomResident`='" + p.getNomResident() + "'," +
                    "`taille`='" + p.getTaille() + "'," +
                    "`nbrEtage`='" + p.getNbrEtage() + "'," +
                    "`statutAppartement`='" + p.getStatutAppartement() + "' " +
                    "WHERE `numAppartement`=" + p.getNumAppartement();

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Appartement modifié !");
        }

        @Override
        public void supprimer(int id) throws SQLException {
            System.out.println("ID de l'appartement à supprimer : " + id);
            String req = "DELETE FROM `appartement` WHERE `IdAppartement`=" + id;

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Appartement supprimé !");
        }

        @Override
        public Appartement getOneById(int id) throws SQLException {
            Appartement appartement = null;
            String req = "SELECT * FROM `appartement` WHERE `idAppartement`=" + id;

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                appartement = new Appartement();
                appartement.setNumAppartement(rs.getInt("numAppartement"));
                appartement.setNomResident(rs.getString("nomResident"));
                appartement.setTaille(rs.getString("taille"));
                appartement.setNbrEtage(rs.getInt("nbrEtage"));

                Appartement.statutAppartement stat = Appartement.statutAppartement.valueOf(rs.getString("statutAppartement"));
                appartement.setStatutAppartement(stat);
            }
            return appartement;
        }

        @Override
        public Set<Appartement> getAll() throws SQLException {
            Set<Appartement> appartements = new HashSet<>();
            String req = "SELECT * FROM `appartement`";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Appartement appartement = new Appartement();
                appartement.setIdAppartement(rs.getInt("idAppartement"));
                appartement.setNumAppartement(rs.getInt("numAppartement"));
                appartement.setNomResident(rs.getString("nomResident"));
                appartement.setTaille(rs.getString("taille"));
                appartement.setNbrEtage(rs.getInt("nbrEtage"));
                Appartement.statutAppartement stat = Appartement.statutAppartement.valueOf(rs.getString("statutAppartement"));
                appartement.setStatutAppartement(stat);
                appartements.add(appartement);
            }
            return appartements;
        }









    }





