package controllers;
import entities.Discussion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.DiscussionService;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierTitre {
        @FXML
        private TextField titreMod;
        @FXML
        private TextField descriptionMod;
        @FXML
        private Label error;

        @FXML
        private Label error2;
        boolean isTitreValid = true;
        boolean isDescriptionValid = true;
        DiscussionService ds = new DiscussionService();

        public void initialize() throws SQLException {
            Discussion discussion = ds.getOneById(MessageController.discuId);
            titreMod.setText(discussion.getTitre());
            descriptionMod.setText(discussion.getDescription());
        }
        @FXML
        void modifierTitre(ActionEvent event) throws SQLException {
            String titre = titreMod.getText();
            String description = descriptionMod.getText();
            int id = MessageController.discuId;
            if (titre.isEmpty()) {
                error.setText("Le champ titre est vide !");
                isTitreValid = false;
            } else if (titre.length() > 10) {
                error.setText("Le titre ne doit pas dépasser 10 caractères !");
                isTitreValid = false;
            }else{
                error.setText("");
                isTitreValid = true;
            }
            if (description.isEmpty()) {
                error2.setText("Le champ description est vide !");
                isDescriptionValid = false;
            }else{
                error2.setText("");
                isDescriptionValid = true;
            }
            if(AjoutDiscussion.titreValide(titre) && !AjoutDiscussion.titreExist(titre) && isTitreValid && isDescriptionValid) {
                Discussion discussion = new Discussion(id, titre, description);
                try {
                    ds.modifier(discussion);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("success ");
                    alert.setContentText("discussion updated successfully");
                    alert.showAndWait();
                    changeScene("/ListeDiscussion.fxml");
                } catch (SQLException e) {
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("error ");
                    alert2.setContentText(e.getMessage());
                    alert2.showAndWait();
                    changeScene("/ListeDiscussion.fxml");

                }
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

    public void retour() {
        changeScene("/Message.fxml");
    }



}
