package edu.esprit.services;
import edu.esprit.entities.Admin;
import edu.esprit.entities.User;
import edu.esprit.entities.Concierge;
import edu.esprit.entities.Resident;
import edu.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {

    Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(User r) {
        if (!r.getEmail().contains("@")) {
            System.out.println("Erreur : L'email doit contenir un '@'.");
            return;
        }
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

        String sql = "INSERT INTO user (id_user, nom_user, prenom_user, email, mdp, num_tel, date_de_naissance, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, r.getId_user());
            pstmt.setString(2, r.getNom_user());
            pstmt.setString(3, r.getPrenom_user());
            pstmt.setString(4, r.getEmail());
            pstmt.setString(5, r.getMdp());
            if (r instanceof Resident) {
                pstmt.setInt(6, ((Resident) r).getNum_tel());
                pstmt.setDate(7, new java.sql.Date(((Resident) r).getDate_de_naissance().getTime()));
            } else if (r instanceof Concierge) {
                pstmt.setInt(6, ((Concierge) r).getNum_tel());
                pstmt.setDate(7, new java.sql.Date(((Concierge) r).getDate_de_naissance().getTime()));
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
                pstmt.setNull(7, java.sql.Types.DATE);
            }
            pstmt.setString(8, role);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public  void modifier(User r) {
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

        String sql = "UPDATE user SET nom_user = ?, prenom_user = ?, email = ?, mdp = ?, num_tel = ?, date_de_naissance = ?, role = ? WHERE id_user = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, r.getNom_user());
            pstmt.setString(2, r.getPrenom_user());
            pstmt.setString(3, r.getEmail());
            pstmt.setString(4, r.getMdp());
            if (r instanceof Resident) {
                pstmt.setInt(5, ((Resident) r).getNum_tel());
                pstmt.setDate(6, new java.sql.Date(((Resident) r).getDate_de_naissance().getTime()));
            } else if (r instanceof Concierge) {
                pstmt.setInt(5, ((Concierge) r).getNum_tel());
                pstmt.setDate(6, new java.sql.Date(((Concierge) r).getDate_de_naissance().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
                pstmt.setNull(6, java.sql.Types.DATE);
            }
            pstmt.setString(7, role);
            pstmt.setInt(8, r.getId_user());

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
    public void supprimer(int id) {
        String sql = "DELETE FROM user WHERE id_user = ?";

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
    public User getOneById(int id) {
        User user = null;
        String sql = "SELECT * FROM user WHERE id_user = ?";

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
                user.setId_user(rs.getInt("id_user"));
                user.setNom_user(rs.getString("nom_user"));
                user.setPrenom_user(rs.getString("prenom_user"));
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user;
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
                user.setNom_user(rs.getString("nom_user"));
                user.setPrenom_user(rs.getString("prenom_user"));
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

}