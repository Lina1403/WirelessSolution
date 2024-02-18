package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import entities.Event;
import services.ServiceEvent;
import java.sql.SQLException;
import java.util.Date;

public class AjouterEvent {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField placeField;

    @FXML
    private TextField numPeopleField;

    @FXML
    private TextField dateHourField;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button submitButton;

    @FXML
    void initialize() {
        // Ajoutez ici le code d'initialisation, si nécessaire
    }

    @FXML
    void ajouterEvent() {
        String name = nameField.getText();
        String email = emailField.getText();
        String place = placeField.getText();
        int numPeople = Integer.parseInt(numPeopleField.getText());
        String dateHour = dateHourField.getText();
        String title = titleField.getText();
        String description = descriptionArea.getText();

        // Créez un nouvel événement avec les informations saisies
        Event event = new Event(name, email, title, new Date(), numPeople,  description, null); // Vous devez gérer l'espace

        // Ajoutez l'événement à la base de données
        ServiceEvent serviceEvent = new ServiceEvent();
        serviceEvent.ajouter(event);
    }
}
