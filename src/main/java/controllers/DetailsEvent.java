package controllers;

import controllers.AfficherEvent;
import entities.Event;
import entities.Espace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceEspace;
import services.ServiceEvent;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class DetailsEvent {

    @FXML
    private TextField textFieldTitre;

    @FXML
    private TextField textFieldNbrPersonne;

    @FXML
    private ComboBox<Espace> comboBoxEspace;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldDate; // Ajout de l'attribut date

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format de date

    private AfficherEvent afficherEvent;
    private Event event;
    private ServiceEvent serviceEvent;

    public void setAfficherEvent(AfficherEvent afficherEvent) {
        this.afficherEvent = afficherEvent;
    }

    @FXML
    private void modifierEvent() {
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

                // Contrôle de saisie pour la description
                String description = textFieldDescription.getText().trim();
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("La description ne peut pas être vide.");
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
                event.setDescription(description);
                event.setDate(date);

                serviceEvent.modifier(event);

                afficherEvent.refreshList();

                fermerFenetreDetails();
            } catch (NumberFormatException e) {
                afficherAlerteErreurEvent("Erreur de format", "Veuillez saisir un nombre valide pour le nombre de personnes.");
            } catch (IllegalArgumentException | ParseException e) {
                afficherAlerteErreurEvent("Erreur de saisie", e.getMessage());
            } catch (SQLException e) {
                afficherAlerteErreurEvent("Erreur SQL", "Erreur lors de la modification de l'événement : " + e.getMessage());
            } catch (Exception e) {
                afficherAlerteErreurEvent("Erreur", e.getMessage());
            }
        }
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
                afficherEvent.refreshList();
                fermerFenetreDetails();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur de suppression
            }
        }
    }

    public void initData(Event event) {
        this.event = event;
        textFieldTitre.setText(event.getTitle());
        textFieldNbrPersonne.setText(Integer.toString(event.getNbrPersonne()));
        textFieldDescription.setText(event.getDescription());

        // Conversion de la date en texte
        textFieldDate.setText(dateFormat.format(event.getDate()));

        chargerEspaces();

        comboBoxEspace.setValue(event.getEspace());

        serviceEvent = new ServiceEvent();
    }


    private void chargerEspaces() {
        try {
            ServiceEspace serviceEspace = new ServiceEspace();
            Set<Espace> espaces = serviceEspace.getAll();
            ObservableList<Espace> espaceOptions = FXCollections.observableArrayList(espaces);
            comboBoxEspace.setItems(espaceOptions);

            comboBoxEspace.setConverter(new StringConverter<Espace>() {
                public String toString(Espace espace) {
                    return espace != null ? espace.getName() : "";
                }

                public Espace fromString(String string) {
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur lors de la récupération des espaces depuis la base de données
        }
    }

    private void fermerFenetreDetails() {
        Stage stage = (Stage) textFieldTitre.getScene().getWindow();
        stage.close();
    }
}
