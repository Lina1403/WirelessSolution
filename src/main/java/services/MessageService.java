package services;

import entities.Discussion;
import entities.Message;
import entities.User;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
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
            Timestamp currentTimestamp = new Timestamp( System.currentTimeMillis());
            pst.setTimestamp(2, currentTimestamp);
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
        List<Message> messages  = new ArrayList<>();
        String req = "SELECT message.contenu, message.date_envoi, user.nom AS emetteur  " +
                "FROM message" +
                " JOIN user ON message.emetteur_id = user.id " +
                "JOIN discussion ON message.discussion_id = discussion.id;";


        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            String contenu =  rst.getString(1);
            Timestamp date = rst.getTimestamp(2);
            User user1 = new User(rst.getString(3));

            Message message = new Message(contenu,date,user1);
            messages.add(message);
        }
        return messages;
    }
    public List<Message> afficherByDiscussionId(int id) throws SQLException {
        List<Message> messages  = new ArrayList<>();
        String req = "SELECT message.contenu, message.date_envoi, user.nom AS emetteur  " +
                "FROM message" +
                " JOIN user ON message.emetteur_id = user.id " +
                "Where message.discussion_id = ?;";
        PreparedStatement pre = connexion.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rst = pre.executeQuery();

        while(rst.next()) {
            String contenu =  rst.getString(1);
            Timestamp date = rst.getTimestamp(2);
            User user1 = new User(rst.getString(3));
            Message message = new Message(contenu,date,user1);
            messages.add(message);
        }

        return messages;

    }
}
