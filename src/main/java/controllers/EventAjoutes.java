package controllers;

import entities.Espace;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceEvent;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class EventAjoutes {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private EventAjoutes eventAjoutes;
    private Event event;

    @FXML private Button boutonGererEspace;
    @FXML private TextField txtRechercheNom;
    @FXML private ListView<Event> listeEvents;
    @FXML private ChoiceBox<String> triChoiceBox;

    @FXML private TextField textFieldTitre;
    @FXML private TextField textFieldNbrPersonne;
    @FXML private ComboBox<Espace> comboBoxEspace;
    @FXML private TextArea textFieldListeInvites;
    @FXML private TextField textFieldDate;
    private ObservableList<Event> eventsObservableList;

    private final ServiceEvent serviceEvent = new ServiceEvent();
    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    public void initialize() {
        try {
            chargerEvents();
            eventAjoutes = this;

            listeEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    event = newValue;
                    eventSelected(newValue);
                }
            });

            triChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    if (newValue.equals("Nom")) {
                        trierParNom();
                    } else if (newValue.equals("Date")) {
                        trierParDate();
                    }
                }
            });

            txtRechercheNom.textProperty().addListener((observable, oldValue, newValue) -> {
                rechercherParNom(newValue.trim());
            });

            boutonGererEspace.setOnAction(event -> ouvrirAjouterEspace());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void eventSelected(Event event) {
        textFieldTitre.setText(event.getTitle());
        textFieldNbrPersonne.setText(String.valueOf(event.getNbrPersonne()));
        textFieldListeInvites.setText(event.getListeInvites());
        textFieldDate.setText(dateFormat.format(event.getDate()));
        comboBoxEspace.setValue(event.getEspace());
    }

    @FXML
    private void trierParNom() {
        ObservableList<Event> events = listeEvents.getItems();
        events.sort((event1, event2) -> event1.getTitle().compareToIgnoreCase(event2.getTitle()));
        listeEvents.setItems(events);
    }

    @FXML
    private void trierParDate() {
        ObservableList<Event> events = listeEvents.getItems();
        events.sort(Comparator.comparing(Event::getDate));
        listeEvents.setItems(events);
    }

    @FXML
    private void rechercherParNom(String nomRecherche) {
        if (!nomRecherche.isEmpty()) {
            ObservableList<Event> resultats = FXCollections.observableArrayList();
            for (Event event : eventsObservableList) {
                if (event.getTitle().toLowerCase().contains(nomRecherche.toLowerCase())) {
                    resultats.add(event);
                }
            }
            listeEvents.setItems(resultats);
        } else {
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

    @FXML
    private void modifierEvent() {
        if (event != null) {
            try {
                // Votre logique de modification d'événement ici
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer les exceptions
            }
        }
    }

    private void chargerEvents() throws SQLException {
        eventsObservableList = FXCollections.observableArrayList(serviceEvent.getAll());

        listeEvents.setItems(eventsObservableList);

        listeEvents.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle());
                }
            }
        });
    }

    private void afficherAlerteErreurEvent(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void supprimerEvent() {
        if (event != null) {
            try {
                serviceEvent.supprimer(event.getIdEvent());
                eventAjoutes.refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur de suppression
            }
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
