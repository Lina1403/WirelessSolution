package controllers;

import entities.Voiture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import services.ServiceVoiture;

import java.io.IOException;
import java.sql.SQLException;

public class AfficherVoitureAdmin {

    @FXML
    private ListView<Voiture> listeVoitures;

    private ObservableList<Voiture> voituresObservableList;
    private ServiceVoiture serviceVoiture;

    public AfficherVoitureAdmin() {
        serviceVoiture = new ServiceVoiture();
        voituresObservableList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        try {
            voituresObservableList.addAll(serviceVoiture.getAll());
            listeVoitures.setItems(voituresObservableList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Gestionnaire d'événements pour le double-clic sur un élément de la ListView
        listeVoitures.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Voiture selectedVoiture = listeVoitures.getSelectionModel().getSelectedItem();
                if (selectedVoiture != null) {
                    ouvrirDetailsVoiture(selectedVoiture);
                }
            }
        });
    }


    private void ouvrirDetailsVoiture(Voiture voiture) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsVoiture.fxml"));
            Parent root = loader.load();
            DetailsVoiture controller = loader.getController();
            controller.initData(voiture);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Détails de la voiture");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshList() {
        try {
            voituresObservableList.clear();
            voituresObservableList.addAll(serviceVoiture.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
