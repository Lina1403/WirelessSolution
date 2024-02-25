package controllers;

import entities.Parking;
import entities.Voiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.ServiceVoiture;

import java.sql.SQLException;

public class AjouterVoiture {

    @FXML
    private TextField marqueField;

    @FXML
    private TextField modeleField;

    @FXML
    private TextField couleurField;

    @FXML
    private TextField matriculeField;

    private Parking selectedParking; // Stocker le parking sélectionné

    public void setSelectedParking(Parking parking) {
        this.selectedParking = parking;
    }

    @FXML
    private void handleAjouterButton(ActionEvent event) {
        // Récupérer les valeurs des champs
        String marque = marqueField.getText();
        String modele = modeleField.getText();
        String couleur = couleurField.getText();
        String matricule = matriculeField.getText();

        // Créer une nouvelle voiture avec les valeurs des champs et le parking sélectionné
        Voiture voiture = new Voiture(selectedParking.getIdParking(), marque, modele, couleur, matricule, selectedParking);

        // Ajouter la voiture à la base de données ou à votre service de gestion des voitures
        try {
            ServiceVoiture serviceVoiture = new ServiceVoiture();
            serviceVoiture.ajouter(voiture);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur, par exemple afficher un message à l'utilisateur
        }

        // Fermer la fenêtre d'ajout de voiture (vous devez implémenter cette fonctionnalité)
    }
}
