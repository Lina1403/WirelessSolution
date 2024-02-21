package controllers;
import entities.Discussion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.DiscussionService;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierTitre {
        @FXML
        private TextField titreMod;
        DiscussionService ds = new DiscussionService();

        public void initialize() throws SQLException {
            Discussion discussion = ds.getOneById(MessageController.discuId);
            titreMod.setText(discussion.getTitre());
        }
        @FXML
        void modifierTitre(ActionEvent event) {
            String titre = titreMod.getText();
            int id = MessageController.discuId;
            Discussion discussion = new Discussion(id,titre);
           try {
               ds.modifier(discussion);
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("success ");
               alert.setContentText("discussion updated successfully");
               alert.showAndWait();
               changeScene("/ListeDiscussion.fxml");
           }catch(SQLException e){
               Alert alert2 = new Alert(Alert.AlertType.WARNING);
               alert2.setTitle("error ");
               alert2.setContentText(e.getMessage());
               alert2.showAndWait();
               changeScene("/ListeDiscussion.fxml");

           }
        }
    public void changeScene(String s) {
        try {
            // Chargez le fichier FXML pour la nouvelle scène
            Parent root = FXMLLoader.load(getClass().getResource(s));
            titreMod.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
