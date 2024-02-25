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
import javafx.stage.Stage;
import services.ServiceParking;

import java.io.IOException;
import java.sql.SQLException;

public class AfficherParking {

    @FXML
    private ListView<Parking> listeParkings;

    @FXML
    private Button boutonAjouter;

    @FXML
    private Button boutonModifier;

    @FXML
    private Button boutonSupprimer;

    private ObservableList<Parking> parkingsObservableList;
    private ServiceParking serviceParking;

    public AfficherParking() {
        serviceParking = new ServiceParking();
        parkingsObservableList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        try {
            parkingsObservableList.addAll(serviceParking.getAll());
            listeParkings.setItems(parkingsObservableList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        boutonAjouter.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterParking.fxml"));
            try {
                Parent root = loader.load();
                AjouterParkingController controller = loader.getController();
                controller.setAfficherParking(this);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Ajouter un parking");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Autres gestionnaires d'événements...
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