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
     // Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvent.fxml"));
      Parent root = FXMLLoader.load(getClass().getResource("/AfficherEspace.fxml"));

        // Créer une nouvelle scène avec le contenu du fichier FXML
        Scene scene = new Scene (root);
        stage.setScene (scene);
        stage.show ();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
