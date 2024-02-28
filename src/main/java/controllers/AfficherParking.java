// AfficherParking.java
package controllers;

import entities.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import services.ServiceParking;

import java.io.IOException;
import java.sql.SQLException;

public class AfficherParking {

    @FXML
    private ListView<Parking> listeParkings;

    @FXML
    private Button boutonAjouter;

    @FXML
    private Button boutonGererVoitures;
    @FXML
    private TextField searchField;



    private ObservableList<Parking> parkingsObservableList;
    private ServiceParking serviceParking;

    public AfficherParking() {
        serviceParking = new ServiceParking();
        parkingsObservableList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        try {
            // Initialisation de la liste des parkings...
            parkingsObservableList.addAll(serviceParking.getAll());
            listeParkings.setItems(parkingsObservableList);

            // Ajout d'un écouteur d'événements pour détecter les modifications dans le champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> rechercher());

            // Initialiser le nombre actuel de voitures à 0 pour chaque parking
            for (Parking parking : parkingsObservableList) {
                parking.setNombreActuelles(0);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        boutonAjouter.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterParking.fxml"));
            try {
                Parent root = loader.load();
                AjouterParking controller = loader.getController();
                controller.setAfficherParking(this);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Ajouter un parking");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        boutonGererVoitures.setOnAction(event -> {
            ouvrirAffichervoiture();
        });

        // Gestionnaire d'événements pour le double-clic sur un élément de la ListView
        listeParkings.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Parking selectedParking = listeParkings.getSelectionModel().getSelectedItem();
                if (selectedParking != null) {
                    ouvrirDetailsParking(selectedParking);
                }
            }
        });

    }
    @FXML
    void ouvrirAffichervoiture() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherVoitureAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Liste des voitures");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void rechercher() {
        String recherche = searchField.getText().toLowerCase();
        ObservableList<Parking> parkingsFiltres = FXCollections.observableArrayList();
        for (Parking parking : parkingsObservableList) {
            if (parking.getNom().toLowerCase().contains(recherche)) {
                parkingsFiltres.add(parking);
            }
        }
        listeParkings.setItems(parkingsFiltres);
    }

    private void ouvrirDetailsParking(Parking parking) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsParking.fxml"));
            Parent root = loader.load();
            DetailsParking controller = loader.getController();
            controller.initData(parking);
            controller.setAfficherParking(this); // Passer une référence à AfficherParking

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Détails du parking");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshList() {
        try {
            parkingsObservableList.clear();
            parkingsObservableList.addAll(serviceParking.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}