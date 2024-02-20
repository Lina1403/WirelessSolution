package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import entities.Event;
import services.ServiceEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterEvent {

    @FXML
    private TextField NameField;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField titleFieldField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField NombrePersonne;

    @FXML
    private TextField Description;

    @FXML
    private Text title;

    @FXML
    void ajouterEvent() {
        String name = NameField.getText();
        String email = EmailField.getText();
        String title = titleFieldField.getText();
        String dateString = dateField.getText();
        int numPeople = Integer.parseInt(NombrePersonne.getText());
        String description = Description.getText();

        // Convertir la chaîne de date en un objet Date
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Spécifiez le format de la date
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // Gérer l'exception de format de date invalide ici
        }

        // Créez un nouvel événement avec les informations saisies
        Event event = new Event(name, email, title, date, numPeople, description, null); // Vous devez gérer l'espace

        // Ajoutez l'événement à la base de données
        ServiceEvent serviceEvent = new ServiceEvent();
        serviceEvent.ajouter(event);
    }
}