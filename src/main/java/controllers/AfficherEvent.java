package controllers;

import entities.Espace;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import services.ServiceEvent;
import java.io.IOException;
import java.sql.SQLException;

public class AfficherEvent {

    @FXML
    private ListView<Event> listeEvents;

    @FXML
    private Button boutonGerer;
    @FXML
    private Button boutonGererEspace;

    private ObservableList<Event> eventsObservableList;
    private ServiceEvent serviceEvent;

    public AfficherEvent() {
        serviceEvent = new ServiceEvent();
        eventsObservableList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        try {
            eventsObservableList.addAll(serviceEvent.getAll());
            listeEvents.setItems(eventsObservableList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        boutonGerer.setOnAction(event -> {
            Event selectedEvent = listeEvents.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                ouvrirDetailsEvent(selectedEvent);
            } else {
                // Afficher un message d'erreur ou une notification si aucun événement n'est sélectionné
            }
        });

        listeEvents.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Event selectedEvent = listeEvents.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    ouvrirDetailsEvent(selectedEvent);
                }
            }
        });
        boutonGererEspace.setOnAction(event -> {
            ouvrirAjouterEspace(); // Méthode pour ouvrir la vue d'ajout d'espace
        });

    }
@FXML
    private void ouvrirAjouterEspace() {   try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEspace.fxml"));
        Parent root = loader.load();
        AjouterEspace controller = loader.getController(); // Assurez-vous d'importer la classe AjouterEspace
        // Vous devez appeler initData() avec des données appropriées, comme un nouvel objet Espace à ajouter
        // Par exemple, vous pouvez passer un nouvel objet Espace vide pour l'initialiser dans la vue AjouterEspace
        controller.initData(new Espace()); // Remplacez new Espace() par les données appropriées si nécessaire

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Ajoutez un espace");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    private void ouvrirDetailsEvent(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEvent.fxml"));
            Parent root = loader.load();
            DetailsEvent controller = loader.getController();
            controller.initData(event);
            controller.setAfficherEvent(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Détails de l'événement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshList() {
        try {
            eventsObservableList.clear();
            eventsObservableList.addAll(serviceEvent.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
