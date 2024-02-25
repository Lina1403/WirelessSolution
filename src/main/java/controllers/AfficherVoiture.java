package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import services.ServiceParking;

import javafx.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class AfficherVoiture {

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private ListView<String> listViewParkings;

    private ServiceParking serviceParking;

    public AfficherVoiture() {
        serviceParking = new ServiceParking();
    }

    @FXML
    private void handleAjouterButton(ActionEvent event) {
        // Insérez votre logique de gestion de clic ici
        System.out.println("Le bouton 'Ajouter' a été cliqué !");
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
