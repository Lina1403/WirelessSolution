package controllers;
import entities.Message;
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
    MessageService ms = new MessageService();
    DiscussionService ds = new DiscussionService();

    public void initialize() throws SQLException {
        ObservableList<Message> messages = FXCollections.observableList(ms.afficherByDiscussionId(discuId));
        messageList.setItems(messages);
        messageList.setCellFactory(param -> new MessageCell());
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
              changeScene();
          }
      }catch(SQLException e){
          System.out.println(e.getMessage());
      }
    }
    public void changeScene() {
        try {
            // Chargez le fichier FXML pour la nouvelle scène
            Parent root = FXMLLoader.load(getClass().getResource("/ListeDiscussion.fxml"));
            messageList.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
