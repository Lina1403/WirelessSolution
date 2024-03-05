package controllers;

import entities.Event;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import services.ServiceEvent;

import java.sql.SQLException;
import java.util.Set;

public class EventAjoutes {

    @FXML
    private ListView<Event> listViewEvents;

    private User currentUser;
    private final ServiceEvent serviceEvent = new ServiceEvent();

    public void initData(User user) {
        this.currentUser = user;
        afficherEvenementsUtilisateur();
    }

    private void afficherEvenementsUtilisateur() {
        try {
            Set<Event> events = serviceEvent.getEventsByUserId(currentUser);
            ObservableList<Event> observableEvents = FXCollections.observableArrayList(events);
            listViewEvents.setItems(observableEvents);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void afficherDetailsEvent(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double clic
            Event selectedEvent = listViewEvents.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                // Afficher les détails de l'événement
                afficherDetails(selectedEvent);
            }
        }
    }

    private void afficherDetails(Event event) {
        // Ajoutez ici le code pour afficher les détails de l'événement
    }
}