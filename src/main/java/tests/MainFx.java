package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterEvent.fxml"));

        // Créer une nouvelle scène avec le contenu du fichier FXML
        Scene scene = new Scene(root);

        // Définir la scène sur la scène principale
        primaryStage.setScene(scene);

        // Définir le titre de la fenêtre
        primaryStage.setTitle("Ajout");

        // Afficher la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
