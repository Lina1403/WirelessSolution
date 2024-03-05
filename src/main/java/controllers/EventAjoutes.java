package controllers;

import entities.Espace;
import entities.Event;
import entities.User;
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
import java.util.Date;


public class EventAjoutes {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Event event;

    @FXML
    private Button boutonGererEspace;
    @FXML
    private TextField txtRechercheNom;
    @FXML
    private ListView<Event> listeEvents;
    @FXML
    private ChoiceBox<String> triChoiceBox;

    @FXML
    private TextField textFieldTitre;
    @FXML
    private TextField textFieldNbrPersonne;
    @FXML
    private ComboBox<Espace> comboBoxEspace;
    @FXML
    private TextArea textFieldListeInvites;
    @FXML
    private TextField textFieldDate;

    private User currentUser;


    private ObservableList<Event> eventsObservableList;

    private final ServiceEvent serviceEvent = new ServiceEvent();
    private final ServiceUser serviceUser = new ServiceUser();
    public void initData(User user) throws SQLException {
        if (currentUser == null) {
            System.out.println("user n'existe pas");
        }
        this.currentUser = user;
        System.out.println("user connectee : " + currentUser);
        initialize();
    }

    @FXML
    public void initialize() {
        try {
            if (currentUser != null) {
                // Charger les événements de l'utilisateur connecté
                chargerEvents();

                // Ajouter les événements à la liste observable pour l'affichage dans la ListView
                eventsObservableList = FXCollections.observableArrayList(serviceEvent.getEventsByUserId(currentUser));

                // Configurer la ListView pour afficher les événements
                listeEvents.setItems(eventsObservableList);

                // Ajouter un écouteur de sélection pour la liste d'événements
                listeEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        event = newValue;
                        eventSelected(newValue);
                    }
                });

                // Ajouter un écouteur pour le choix de tri
                triChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        if (newValue.equals("Nom")) {
                            trierParNom();
                        } else if (newValue.equals("Date")) {
                            trierParDate();
                        }
                    }
                });

                // Ajouter un écouteur pour la recherche par nom
                txtRechercheNom.textProperty().addListener((observable, oldValue, newValue) -> {
                    rechercherParNom(newValue.trim());
                });

                // Ajouter un gestionnaire pour le bouton de gestion de l'espace
                boutonGererEspace.setOnAction(event -> ouvrirAjouterEspace());
            } else {
                // Afficher une alerte si currentUser est null
                afficherAlerteErreurEvent("Erreur", "Utilisateur non connecté !");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public EventAjoutes() {
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
        {
            if (event != null) {
                try {
                    // Contrôle de saisie pour le titre
                    String titre = textFieldTitre.getText().trim();
                    if (titre.isEmpty() || !titre.matches("[a-zA-Z ]+")) {
                        throw new IllegalArgumentException("Le titre ne peut pas être vide et doit contenir uniquement des lettres et des espaces.");
                    }

                    // Contrôle de saisie pour le nombre de personnes
                    String nbrPersonneText = textFieldNbrPersonne.getText().trim();
                    if (nbrPersonneText.isEmpty()) {
                        throw new IllegalArgumentException("Veuillez saisir un nombre pour le nombre de personnes.");
                    }
                    int nbrPersonne = Integer.parseInt(nbrPersonneText);
                    if (nbrPersonne <= 0 || nbrPersonne > 50) {
                        throw new IllegalArgumentException("Le nombre de personnes doit être compris entre 1 et 50.");
                    }

                    // Contrôle de saisie pour la listeInvites
                    String listeInvites = textFieldListeInvites.getText().trim();
                    if (listeInvites.isEmpty()) {
                        throw new IllegalArgumentException("La listeInvites ne peut pas être vide.");
                    }

                    // Contrôle de saisie pour la date
                    String dateString = textFieldDate.getText().trim();
                    if (dateString.isEmpty()) {
                        throw new IllegalArgumentException("Veuillez saisir une date.");
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(dateString);

                    // Contrôle de saisie pour l'espace
                    Espace espace = comboBoxEspace.getValue();
                    if (espace == null) {
                        throw new IllegalArgumentException("Veuillez sélectionner un espace.");
                    }

                    // Si toutes les validations sont passées, vous pouvez effectuer la modification
                    event.setTitle(titre);
                    event.setNbrPersonne(nbrPersonne);
                    event.setEspace(espace);
                    event.setListeInvites(listeInvites);
                    event.setDate(new java.sql.Date(date.getTime()));

                    serviceEvent.modifier(event);


                } catch (NumberFormatException e) {
                    afficherAlerteErreurEvent("Erreur de format", "Veuillez saisir un nombre valide pour le nombre de personnes.");
                } catch (IllegalArgumentException | ParseException e) {
                    afficherAlerteErreurEvent("Erreur de saisie", e.getMessage());
                } catch (SQLException e) {
                    afficherAlerteErreurEvent("Erreur SQL", "Erreur lors de la modification de l'événement : " + e.getMessage());
                } catch (Exception e) {
                    afficherAlerteErreurEvent("Erreur", e.getMessage());
                }

            }}}

    private void chargerEvents() throws SQLException {
        eventsObservableList = FXCollections.observableArrayList(serviceEvent.getEventsByUserId(currentUser));

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
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur de suppression
            }
        }
    }

    public void refreshList() {
        try {
            eventsObservableList.clear();
            eventsObservableList.addAll(serviceEvent.getEventsByUserId(currentUser));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}