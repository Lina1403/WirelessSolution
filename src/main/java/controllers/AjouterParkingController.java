package controllers;

import entities.Parking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceParking;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class AjouterParkingController {
    @FXML
    private TextField nomTextField;

    @FXML
    private TextField capaciteTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField nombreActuellesTextField;

    @FXML
    private Button boutonAjouter;

    private AfficherParking afficherParking;
    private ServiceParking serviceParking;

    public AjouterParkingController() {
        serviceParking = new ServiceParking();
    }

    @FXML
    public void initialize() {
        // Vous pouvez retirer ce code de la méthode initialize
    }

    public void setAfficherParking(AfficherParking afficherParking) {
        this.afficherParking = afficherParking;
    }

    public void ajouterParking() {
        String nom = nomTextField.getText();
        int capacite = Integer.parseInt(capaciteTextField.getText());
        String type = typeTextField.getText();
        int nombreActuelles = Integer.parseInt(nombreActuellesTextField.getText());

        Parking nouveauParking = new Parking(nom, capacite, type, nombreActuelles);

        try {
            serviceParking.ajouter(nouveauParking);

            // Affichage d'un message de succès
            afficherMessage("Parking ajouté", "Le parking a été ajouté avec succès.");

            if (afficherParking != null) {
                afficherParking.refreshList();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void afficherMessage(String titre, String contenu) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}