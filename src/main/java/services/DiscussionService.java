package services;

import entities.Discussion;
import entities.User;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscussionService implements IService<Discussion> {

    Connection connexion ;

    public DiscussionService(){
        connexion = DataSource.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Discussion discussion) throws SQLException {
        String req = "INSERT INTO `discussion` (`titre`, `date_creation`, `createur_id`)"
                + "VALUES (?,?,?)";
        try{
            PreparedStatement pst = connexion.prepareStatement(req);
            pst.setString(1, discussion.getTitre());
            Timestamp currentTimestamp = new Timestamp( System.currentTimeMillis());

            pst.setTimestamp(2, currentTimestamp);
            pst.setInt(3, discussion.getCreateur().getId());
            pst.executeUpdate();


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Discussion p) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {
        try {
            PreparedStatement pre = connexion.prepareStatement("Delete from discussion where id=? ;");
            pre.setInt(1, id);
            if (pre.executeUpdate() != 0) {
                System.out.println("discussion Deleted");

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Discussion getOneById(int id) throws SQLException {
        return null;
    }



    @Override
    public List<Discussion> afficher() throws SQLException {
        List<Discussion> discussions  = new ArrayList<>();
        String req = "select * from discussion";
        try {
            Statement stm = connexion.createStatement();

            ResultSet rst = stm.executeQuery(req);

            while (rst.next()) {
                Discussion discussion = new Discussion(
                        rst.getInt("id"),
                        rst.getString("titre"),
                        rst.getTimestamp(3)
                );
                discussions.add(discussion);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return discussions;
    }
}
