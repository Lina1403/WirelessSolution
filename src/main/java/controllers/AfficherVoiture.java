/*package controllers;

import entities.Parking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceParking;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherVoiture {


    @FXML
    private Button ajouterButton;

    @FXML
    private Button consulterButton;

    private ServiceParking serviceParking;

    public AfficherVoiture() {
        serviceParking = new ServiceParking();
    }

    @FXML
    private void handleAjouterButton(ActionEvent event) {

        // Récupérer le parking correspondant à partir du service
        try {
            // Ouvrir l'interface d'ajout de voiture en passant le parking sélectionné
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVoiture.fxml"));
            Parent root = loader.load();

            AjouterVoiture controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Ajouter une voiture");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleConsulterButton(ActionEvent event) {
        // Afficher une boîte de dialogue pour sélectionner un fichier image du code QR
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir le code QR");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File qrCodeFile = fileChooser.showOpenDialog(null);

        if (qrCodeFile != null) {
            // Si un fichier est sélectionné, charger l'image du code QR
            Image qrCodeImage = new Image(qrCodeFile.toURI().toString());

            // Afficher la fenêtre des détails de la voiture avec les informations correspondantes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterVoitureDetails.fxml"));
            Parent root;
            try {
                root = loader.load();
                ConsulterVoitureDetails controller = loader.getController();
                controller.initData(qrCodeImage, qrCodeFile);
                // Passer l'image du code QR au contrôleur des détails de la voiture


                Stage stage = new Stage();
                stage.setTitle("Détails de la voiture");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();


            }
        }
    }


    @FXML
    public void initialize() {

    }
}


 */
