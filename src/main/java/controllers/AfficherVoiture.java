package controllers;

import entities.Parking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import services.ServiceParking;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherVoiture {

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private ListView<String> listViewParkings;

    @FXML
    private Button ajouterButton;

    private ServiceParking serviceParking;

    public AfficherVoiture() {
        serviceParking = new ServiceParking();
    }

    @FXML
    private void handleAjouterButton(ActionEvent event) {
        // Récupérer le parking sélectionné dans la ListView
        String parkingSelectionne = listViewParkings.getSelectionModel().getSelectedItem();
        if (parkingSelectionne != null) {
            // Récupérer le parking correspondant à partir du service
            try {
                Parking parking = serviceParking.getParkingByName(parkingSelectionne);
                // Ouvrir l'interface d'ajout de voiture en passant le parking sélectionné
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVoiture.fxml"));
                Parent root = loader.load();

                AjouterVoiture controller = loader.getController();
                controller.setSelectedParking(parking); // Définir le parking sélectionné dans le contrôleur AjouterVoiture

                Stage stage = new Stage();
                stage.setTitle("Ajouter une voiture");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Veuillez sélectionner un parking avant d'ajouter une voiture.");
        }
    }

    @FXML
    public void initialize() {
        // Charger les types de parking disponibles dans la ComboBox
        chargerTypes();

        // Ajouter un gestionnaire d'événements pour le changement de sélection dans la ComboBox
        comboBoxType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    // Charger la liste des parkings correspondant au type sélectionné
                    chargerParkings(newValue);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // Désactiver le bouton "Ajouter" par défaut
        ajouterButton.setDisable(true);

        // Ajouter un gestionnaire d'événements pour le changement de sélection dans la ListView des parkings
        listViewParkings.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ajouterButton.setDisable(false); // Activer le bouton si un parking est sélectionné
            } else {
                ajouterButton.setDisable(true); // Désactiver le bouton si aucun parking n'est sélectionné
            }
        });
    }

    private void chargerTypes() {
        // Vous pouvez obtenir les types à partir de votre source de données, ici je les ai définis manuellement
        List<String> types = null;
        try {
            types = serviceParking.getTypes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        comboBoxType.getItems().addAll(types);
    }

    private void chargerParkings(String type) throws SQLException {
        // Récupérer la liste des parkings correspondant au type sélectionné
        List<String> parkings = serviceParking.getParkingsByType(type);
        listViewParkings.getItems().clear();
        listViewParkings.getItems().addAll(parkings);
    }
}