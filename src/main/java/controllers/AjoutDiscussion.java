package controllers;
import entities.Discussion;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.DiscussionService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AjoutDiscussion {

        @FXML
        private TextField titre ;

        User user1 = new User(2,"oussema");
        @FXML
        void ajouterEvent(ActionEvent event) {
                String title = titre.getText();
                Timestamp currentTimestamp = new Timestamp( System.currentTimeMillis());
                Discussion discussion = new Discussion(title,currentTimestamp,user1);

               try {
                       DiscussionService ds = new DiscussionService();
                       ds.ajouter(discussion);
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("Success");
                       alert.setContentText("Discussion added successfully!");

                       alert.showAndWait();
               }catch(SQLException e ){
                       System.out.println(e.getMessage());
               }

        }

}
