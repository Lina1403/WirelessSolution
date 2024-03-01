package controllers;

import entities.Espace;
import entities.Event;
import javafx.scene.control.TextField;
import services.ServiceEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Desktop;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class AfficherEvent {
    @FXML private Button boutonGerer;
    @FXML private Button boutonGererEspace;
    @FXML private TextField txtRechercheNom;
    @FXML private ListView<Event> listeEvents;
    private ObservableList<Event> eventsObservableList;
    private ServiceEvent serviceEvent;

    // Constructeur
    public AfficherEvent() {
        serviceEvent = new ServiceEvent();
        eventsObservableList = FXCollections.observableArrayList();
    }

    // Méthode appelée après l'initialisation de la vue
    @FXML
    public void initialize() {
        try {
            // Charger la liste des événements
            eventsObservableList.addAll(serviceEvent.getAll());
            listeEvents.setItems(eventsObservableList);

            // Personnaliser l'affichage des éléments dans la ListView
            listeEvents.setCellFactory(param -> new ListCell<Event>() {
                @Override
                protected void updateItem(Event item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Afficher le titre de l'événement et son lieu dans la liste
                        setText(item.getTitle() + " - " + item.getEspace().getName());
                    }
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Écouter le clic sur le bouton PDF

        // Écouter le clic sur le bouton de gestion d'événements
        boutonGerer.setOnAction(event -> {
            Event selectedEvent = listeEvents.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                ouvrirDetailsEvent(selectedEvent);
            } else {
                // Afficher un message d'erreur si aucun événement n'est sélectionné
            }
        });

        // Écouter le double-clic sur un élément de la liste
        listeEvents.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Event selectedEvent = listeEvents.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    ouvrirDetailsEvent(selectedEvent);
                }
            }
        });

        // Écouter le clic sur le bouton de gestion des espaces
        boutonGererEspace.setOnAction(event -> ouvrirAjouterEspace());
    }
    @FXML
    void trierParNom() {
        ObservableList<Event> events = listeEvents.getItems();
        events.sort((event1, event2) -> event1.getTitle().compareToIgnoreCase(event2.getTitle()));
        listeEvents.setItems(events);
    }

    @FXML
    void rechercherParNom() {
        String nomRecherche = txtRechercheNom.getText().trim();
        if (!nomRecherche.isEmpty()) {
            ObservableList<Event> resultats = FXCollections.observableArrayList();
            for (Event event : eventsObservableList) {
                if (event.getTitle().toLowerCase().contains(nomRecherche.toLowerCase())) {
                    resultats.add(event);
                }
            }
            listeEvents.setItems(resultats);
        } else {
            // Si le champ de recherche est vide, affichez tous les événements
            listeEvents.setItems(eventsObservableList);
        }
    }

    @FXML
    private void ouvrirAjouterEspace() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEspace.fxml"));
            Parent root = loader.load();
            AjouterEspace controller = loader.getController();
            controller.initData(new Espace());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter un espace");
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
