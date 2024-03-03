package service;
import entities.Admin;
import entities.User;
import entities.Concierge;
import entities.Resident;
import utils.Datasource;
import entities.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {

    Connection cnx = Datasource.getInstance().getCnx();


    @Override
    public void ajouter(User r) throws SQLException {
        if (!r.getMail().contains("@")) {
            System.out.println("Erreur : L'email doit contenir un '@'.");
            return;
        }

        String sql = "INSERT INTO user (id, nom, prenom, mail, password, number, num_urgence, date_of_arrival, horraire_service, service, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, r.getId());
            pstmt.setString(2, r.getNom());
            pstmt.setString(3, r.getPrenom());
            pstmt.setString(4, r.getMail());
            pstmt.setString(5, r.getPassword());
            pstmt.setInt(6, r.getNumber());
            pstmt.setString(11, r.getRole().toString());

            if (r.getRole() == Role.RESIDENT && r instanceof Resident) {
                pstmt.setInt(7, ((Resident) r).getNum_urgence());
                pstmt.setDate(8, new java.sql.Date(((Resident) r).getDate_of_arrival().getTime()));
            } else if (r.getRole() == Role.CONCIERGE && r instanceof Concierge) {
                pstmt.setString(9, ((Concierge) r).getHorraire_service());
                pstmt.setString(10, ((Concierge) r).getService());
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
                pstmt.setNull(8, java.sql.Types.DATE);
                pstmt.setNull(9, java.sql.Types.VARCHAR);
                pstmt.setNull(10, java.sql.Types.VARCHAR);
            }



            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public  void modifier(User r)throws SQLException {
        String role;
        if (r instanceof Admin) {
            role = "Admin";
        } else if (r instanceof Resident) {
            role = "Resident";
        } else if (r instanceof Concierge) {
            role = "Concierge";
        } else {
            role = "User";
        }

        String sql = "UPDATE user SET nom = ?, prenom = ?, mail = ?, password = ?,number=?, num_urgence = ?, date_of_arrival = ?,horraire_service=?,service=?, role = ? WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, r.getNom());
            pstmt.setString(2, r.getPrenom());
            pstmt.setString(3, r.getMail());
            pstmt.setString(4, r.getPassword());
            pstmt.setInt(5,r.getNumber());
            if (r instanceof Resident) {
                pstmt.setInt(6, ((Resident) r).getNum_urgence());
                pstmt.setDate(7, new java.sql.Date(((Resident) r).getDate_of_arrival().getTime()));
            } else if (r instanceof Concierge) {
                pstmt.setString(8, ((Concierge) r).getHorraire_service());
                pstmt.setString(9, (((Concierge) r).getService()));
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
                pstmt.setNull(7, java.sql.Types.DATE);
                pstmt.setNull(8, java.sql.Types.VARCHAR);
                pstmt.setNull(9, java.sql.Types.VARCHAR);
            }
            pstmt.setString(10, role);
            pstmt.setInt(11 , r.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with the given id.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(int id)throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("No user found with the given id.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public User getOneById(int id) throws SQLException{
        User user = null;
        String sql = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                if ("Admin".equals(role)) {
                    user = new Admin();
                } else if ("Resident".equals(role)) {
                    user = new Resident();
                } else if ("Concierge".equals(role)) {
                    user = new Concierge();
                } else {
                    user = new User();
                }
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setMail(rs.getString("mail"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setMail(rs.getString("mail"));
                user.setPassword(rs.getString("password"));
                user.setNumber(rs.getInt("number"));

                //Role role = Role.valueOf();
                //rs.getString("role")
                user.setRole(Role.valueOf(rs.getString("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

}