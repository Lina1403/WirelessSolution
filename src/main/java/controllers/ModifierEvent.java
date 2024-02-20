package controllers;

import entities.Espace;
import entities.Event;
import javafx.scene.control.DatePicker;
import services.ServiceEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.Date;

public class ModifierEvent {

    private final ServiceEvent eventService = new ServiceEvent();
    private Event eventToModify;

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField nbrPersonneField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField EspaceField;

    public void initData(Event event) {
        this.eventToModify = event;
        // Afficher les détails de l'événement à modifier dans les champs de texte
        titleField.setText(event.getTitle());

        // Formater la date en chaîne de caractères avec le format souhaité
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(event.getDate());
        dateField.getEditor().setText(formattedDate);

        nbrPersonneField.setText(String.valueOf(event.getNbrPersonne()));
        descriptionField.setText(event.getDescription());
        EspaceField.setText(String.valueOf(event.getEspace()));
    }

    @FXML
    void modifierEvent(ActionEvent event) {
        Espace espace = new Espace();

        try {
            // Mettre à jour les données de l'événement avec les nouvelles valeurs
            eventToModify.setTitle(titleField.getText());
            eventToModify.setDate(Date.valueOf(dateField.getValue())); // Utilisez getValue() pour obtenir la date sélectionnée du DatePicker
            eventToModify.setNbrPersonne(Integer.parseInt(nbrPersonneField.getText()));
            eventToModify.setDescription(descriptionField.getText());
// Supposons que EspaceField contienne l'identifiant de l'espace en tant que chaîne de caractères
            String espaceId = EspaceField.getText();
// Maintenant, utilisez cet identifiant pour créer un objet Espace approprié
            espace.setIdEspace(Integer.parseInt(espaceId));
// Vous devrez peut-être attribuer d'autres propriétés à l'objet Espace en fonction des données disponibles

            // Appeler la méthode de mise à jour dans le service d'événement en passant l'ID et l'événement à modifier
            eventService.modifier(eventToModify);

            // Afficher une alerte pour confirmer la modification
            showAlert("Modification réussie", "L'événement a été modifié avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la modification de l'événement : " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
