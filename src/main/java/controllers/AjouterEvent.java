package controllers;

import entities.Espace;
import entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

public class AjouterEvent {

    private final ServiceEvent serviceEvent = new ServiceEvent();

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField nbrPersonneField;

    @FXML
    private TextField titleField;

    @FXML
    private ComboBox<String> espaceComboBox;

    @FXML
    void initialize() {
        try {
            Set<String> espacesNames = serviceEvent.getAllEspacesNames();
            espaceComboBox.getItems().addAll(espacesNames);
        } catch (SQLException e) {
            afficherAlerteErreurEvent("Erreur SQL", "Erreur lors de la récupération des noms d'espaces : " + e.getMessage());
        }
    }

    @FXML
    void ajouterEvent(ActionEvent event) {
        try {
            LocalDate date = datePicker.getValue();
            if (date == null) {
                throw new IllegalArgumentException("Veuillez sélectionner une date.");
            }

            String title = titleField.getText();
            String description = descriptionField.getText();
            String espaceName = espaceComboBox.getValue(); // Récupérer le nom de l'espace sélectionné

            if (title.isEmpty() || description.isEmpty() || espaceName == null) {
                throw new IllegalArgumentException("Veuillez remplir tous les champs.");
            }

            int nbrPersonne = Integer.parseInt(nbrPersonneField.getText());

            // Rechercher l'objet Espace correspondant au nom sélectionné
            Espace espaceObj = serviceEvent.getEspaceByName(espaceName);

            // Vérifier si l'espace existe
            if (espaceObj == null) {
                throw new IllegalArgumentException("L'espace sélectionné n'existe pas.");
            }

            Event eventObj = new Event(title, Date.valueOf(date), nbrPersonne, description, espaceObj);

            serviceEvent.ajouter(eventObj);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Événement ajouté avec succès");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            afficherAlerteErreurEvent("Erreur de format", "Veuillez saisir un nombre valide pour le nombre de personnes.");
        } catch (IllegalArgumentException e) {
            afficherAlerteErreurEvent("Erreur de saisie", e.getMessage());
        } catch (SQLException e) {
            afficherAlerteErreurEvent("Erreur SQL", "Erreur lors de l'ajout de l'événement : " + e.getMessage());
        } catch (Exception e) {
            afficherAlerteErreurEvent("Erreur", e.getMessage());
        }
    }


    private void afficherAlerteErreurEvent(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
