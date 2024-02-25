package controllers;

import entities.Parking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.ServiceParking;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class AjouterParking {
    @FXML
    private TextField nomTextField;

    @FXML
    private TextField capaciteTextField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField nombreActuellesTextField;

    @FXML
    private Button boutonAjouter;

    private AfficherParking afficherParking;
    private ServiceParking serviceParking;

    public AjouterParking() {
        serviceParking = new ServiceParking();
    }

    @FXML
    public void initialize() {
        typeComboBox.getItems().addAll("Sous-sol","Pleine air","Couverte");
    }

    public void setAfficherParking(AfficherParking afficherParking) {
        this.afficherParking = afficherParking;
    }

    public void ajouterParking() {
        String nom = nomTextField.getText();
        int capacite = Integer.parseInt(capaciteTextField.getText());
        String type = typeComboBox.getValue(); // Récupérer la valeur sélectionnée dans le ComboBox
        int nombreActuelles = Integer.parseInt(nombreActuellesTextField.getText());

        if (nom.isEmpty() || type == null) {
            // Vérifier si les champs obligatoires sont vides
            afficherMessage("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

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
            afficherMessage("Erreur", "Une erreur est survenue lors de l'ajout du parking.");
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