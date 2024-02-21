package controllers;
import entities.Message;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import services.DiscussionService;
import services.MessageService;
import utils.MessageCell;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class MessageController {
    @FXML
    private Button attachButton;

    @FXML
    private TextField messageField;

    @FXML
    private ListView<Message> messageList;

    @FXML
    private Button sendButton;

    public static int discuId ;
    User user1 = new User(2,"chiheb");
    MessageService ms = new MessageService();
    DiscussionService ds = new DiscussionService();

    public void initialize() throws SQLException {
        ObservableList<Message> messages = FXCollections.observableList(ms.afficherByDiscussionId(discuId));
        messageList.setItems(messages);
        messageList.setCellFactory(param -> new MessageCell());

        sendButton.setOnAction(e -> {
            try {
                sendMessage();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


    }
    public void supprimerDiscussion()  {
      try {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Confirmation ");
          alert.setHeaderText("Look, a Confirmation Dialog");
          alert.setContentText("Are you ok with deleting the discussion?");
          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK){
              ds.supprimer(discuId);
              Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
              alert1.setTitle("success ");
              alert1.setContentText("discussion deleted successfully");
              alert1.showAndWait();
              changeScene("/ListeDiscussion.fxml");
          }
      }catch(SQLException e){
          System.out.println(e.getMessage());
      }
    }
    public void changeScene(String s) {
        try {
            // Chargez le fichier FXML pour la nouvelle scène
            Parent root = FXMLLoader.load(getClass().getResource(s));
            messageList.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendMessage() throws SQLException {
        // Get the text from the messageField
        String text = messageField.getText();
        Timestamp currentTimestamp = new Timestamp( System.currentTimeMillis());
        // Create a new Message object
        Message message = new Message(text,currentTimestamp,user1);
        // add message to the database
        ms.ajouter(message);
        // Add the message to the messageList
        messageList.getItems().add(message);

        // Clear the messageField
        messageField.clear();
    }
    public void modifierTitre(){
        changeScene("/ModifierTitre.fxml");
    }

}
