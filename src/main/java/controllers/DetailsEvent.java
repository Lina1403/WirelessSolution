package controllers;

import controllers.AfficherEvent;
import entities.Event;
import entities.Espace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
                event.setTitle(textFieldTitre.getText());
                event.setNbrPersonne(Integer.parseInt(textFieldNbrPersonne.getText()));
                event.setEspace(comboBoxEspace.getValue());
                event.setDescription(textFieldDescription.getText());

                // Conversion des champs de texte en date
                Date date = dateFormat.parse(textFieldDate.getText());
                event.setDate(date);

                serviceEvent.modifier(event);

                afficherEvent.refreshList();

                fermerFenetreDetails();
            } catch (SQLException | NumberFormatException | ParseException e) {
                e.printStackTrace();
                // Gérer l'erreur de modification
            }
        }
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
