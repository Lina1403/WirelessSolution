package services;

import entities.Message;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MessageService implements IService<Message>{
    Connection connexion;
    public MessageService(){
        connexion = DataSource.getInstance().getConnexion();
    }
    public int getDisId(String titre) throws SQLException {
        String req = "SELECT id FROM discussion WHERE titre = ?";
        try (PreparedStatement statement = connexion.prepareStatement(req)) {
            statement.setString(1, titre);

            try  {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new IllegalArgumentException("Discussion not found for titre: " + titre);
                }
            }catch(Exception e ){
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            // Log or re-throw the exception
            throw new SQLException("Error finding discussion ID: " + e.getMessage(), e);
        }
        return 0 ;
    }
    @Override
    public void ajouter(Message message) throws SQLException {
        String req = "INSERT INTO `message` (`contenu`, `date_envoi`, `emetteur_id`,`discussion_id`)"
                + "VALUES (?,?,?,?)";
        try{
            PreparedStatement pst = connexion.prepareStatement(req);
            pst.setString(1, message.getContenu());
            pst.setDate(2,new java.sql.Date(message.getDate_envoi().getTime()));
            pst.setInt(3,message.getEmetteur().getId());
            pst.setInt(4,14);
            pst.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Message p) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {
        try {
            PreparedStatement pre = connexion.prepareStatement("Delete from message where id=? ;");
            pre.setInt(1, id);
            if (pre.executeUpdate() != 0) {
                System.out.println("message Deleted");

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Message getOneById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Message> afficher() throws SQLException {
        return null;
    }
}
