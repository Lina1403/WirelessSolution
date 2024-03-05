package controllers;

import entities.Appartement;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceAppartemment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AfficherAppartResident {

    private final ServiceAppartemment serviceAppartemment = new ServiceAppartemment();

    @FXML
    private ListView<Appartement> listView;



    private List<Appartement> appartementList;

    private User resident; // Le résident spécifié

    // Méthode pour afficher les appartements du résident spécifié
    public void afficherAppartementsResident(User resident) throws SQLException {

    }

    @FXML
    void initialize() {
        // Ajout d'un écouteur de changement de texte pour le champ de recherche

    }

    @FXML
    void modiferAppartement(ActionEvent actionEvent) {
        // Le reste du code pour modifier l'appartement
    }

    @FXML
    void ajouterAppartement(ActionEvent actionEvent) {
        // Le reste du code pour ajouter un appartement
    }

    // Autres méthodes du contrôleur...

}
