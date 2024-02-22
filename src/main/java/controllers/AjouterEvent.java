package controllers;

import entities.Event;
import entities.Espace;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.ServiceEvent;
import java.sql.Date;

import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterEvent {

    private final ServiceEvent serviceEvent = new ServiceEvent();

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField nbrPersonneField;

    @FXML
    private TextField nameField;

    @FXML
    private Text title;

   @FXML
 private ComboBox<String> espaceComboBox;

    @FXML
    private TextField emailField;

    @FXML
    private TextField titleField;


    @FXML
    void ajouterEvent(ActionEvent event) {
        try {
            LocalDate date = datePicker.getValue();
            if (date == null) {
                throw new IllegalArgumentException("Please select a date.");
            }

            // Vérifier que les autres champs sont remplis
            if (nameField.getText().isEmpty()) {
                throw new IllegalArgumentException("Nom is null or empty");
            }
            if (emailField.getText().isEmpty()) {
                throw new IllegalArgumentException("Email is null or empty");
            }
            if (titleField.getText().isEmpty()) {
                throw new IllegalArgumentException("Titre is null or empty");
            }
            int nbrPersonne = Integer.parseInt(nbrPersonneField.getText());
            String description = descriptionField.getText();
            String espaceString = espaceComboBox.getValue();
        // Event.Espace espace = Event.Espace.valueOf(espaceString);

            // Créer l'objet Event
            Event Event = new Event(nameField.getText(), emailField.getText(), titleField.getText(), java.sql.Date.valueOf(date), nbrPersonne, description, null);

            // Appeler la méthode pour ajouter l'événement
            serviceEvent.ajouter(Event);

            // Afficher une confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Événement ajouté avec succès");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Gérer l'erreur pour le format de numéro invalide
            afficherAlerteErreurEvent("Format Error", "Please enter a valid number of people.");
        } catch (IllegalArgumentException e) {
            // Gérer l'erreur pour la date ou le nom nulle
            afficherAlerteErreurEvent("Input Error", e.getMessage());
        } catch (Exception e) {
            // Gérer les autres exceptions
            afficherAlerteErreurEvent("Error", e.getMessage());
        }
    }

    private void afficherAlerteErreurEvent(String error, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
