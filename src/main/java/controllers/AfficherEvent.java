package controllers;

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
import javafx.util.Callback;
import services.ServiceEvent;

import java.io.IOException;
import java.sql.SQLException;

public class AfficherEvent {

    @FXML
    private ListView<Event> listeEvents;

    @FXML
    private Button boutonGerer;

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
            listeEvents.setCellFactory(new Callback<ListView<Event>, javafx.scene.control.ListCell<Event>>() {
                @Override
                public javafx.scene.control.ListCell<Event> call(ListView<Event> listView) {
                    return new javafx.scene.control.ListCell<Event>() {
                        @Override
                        protected void updateItem(Event event, boolean empty) {
                            super.updateItem(event, empty);
                            if (event != null) {
                                setText(event.getTitle() + " - " + event.getEspace().getName());
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });
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
