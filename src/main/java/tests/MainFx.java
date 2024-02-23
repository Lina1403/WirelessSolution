package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.IOException;

public class MainFx extends Application {
        @Override
        public void start(Stage stage) throws IOException {
              // FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterAppartement.fxml"));
               // FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterFacture.fxml"));
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherAppartement.fxml"));
         // FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherFacture.fxml"));
              //  FXMLLoader loader= new FXMLLoader(getClass().getResource("/ModifierFacture.fxml"));
           //   FXMLLoader loader= new FXMLLoader(getClass().getResource("/ModifierApapartment.fxml"));

                Parent root=loader.load();
                Scene scene=new Scene(root);
                stage.setScene(scene);
                stage.setTitle("ajout");
                stage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}
