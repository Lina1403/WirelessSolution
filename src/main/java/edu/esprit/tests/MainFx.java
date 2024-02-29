package edu.esprit.tests;

import edu.esprit.controllers.AjouterReclamation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load AjouterReclamation.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
        Parent root = loader.load();

        // Set the controller for AjouterReclamation.fxml
        AjouterReclamation controller = loader.getController();
        controller.setStage(stage); // Pass the stage to the controller

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ajouter Réclamation");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}