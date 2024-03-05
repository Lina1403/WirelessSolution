package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.awt.*;
import java.io.IOException;

public class AcceuilFront {
    @FXML
    private Button eventButton;
    private User currentUser;

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


    // Méthode pour gérer le clic sur le bouton "Event"
    @FXML
    private void handleEventButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
            Parent root = loader.load();

            AjouterEvent controller = loader.getController();

            if (controller != null) {
                controller.setCurrentUser(currentUser);

                // Afficher la scène
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                // Obtenir la scène actuelle à partir de l'événement

                // Définir la nouvelle scène
                stage.setScene(scene);
                stage.show();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
