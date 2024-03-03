package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Charger le fichier FXML

        // ------------------------- A   D   M   I   N ------------------------
        // Parent root = FXMLLoader.load(getClass().getResource("/AfficherParking.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/AjouterParking.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/DetailsParking.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoitureAdmin.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/DetailsVoiture.fxml"));

        //-------------------------- U   S   E   R -----------------------------
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoiture.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/AjouterVoiture.fxml"));


        // Créer la scène
        Scene scene = new Scene(root);

        // Définir la scène sur la fenêtre de l'application
        stage.setScene(scene);

        // Définir le titre de la fenêtre
        stage.setTitle("Gestion des parkings");

        // Afficher la fenêtre
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
