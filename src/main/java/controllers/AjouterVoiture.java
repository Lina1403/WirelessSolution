// AjouterVoiture.java
package controllers;

import entities.Parking;
import entities.Voiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    @FXML
    private Button supprimerButton; // Ajout du bouton de suppression

    private Parking selectedParking; // Stocker le parking sélectionné
    private int idVoitureAjoutee;

    private AfficherVoitureAdmin afficherVoitureAdmin; // Déclaration de la variable afficherVoitureAdmin

    public void setSelectedParking(Parking parking) {
        this.selectedParking = parking;
    }
    public void setAfficherVoitureAdmin(AfficherVoitureAdmin afficherVoitureAdmin) {
        this.afficherVoitureAdmin = afficherVoitureAdmin;
    }

    @FXML
    private void handleAjouterButton(ActionEvent event) {
        // Désactiver le bouton de suppression avant d'ajouter la voiture
        supprimerButton.setDisable(true);

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
            // Ajouter la voiture et stocker son identifiant
            int idVoitureAjoutee = serviceVoiture.ajouter(voiture);

            // Vérifier si l'ajout s'est bien passé et l'identifiant est valide
            if (idVoitureAjoutee != -1) {
                // Activer le bouton de suppression après un ajout réussi
                supprimerButton.setDisable(false);

                // Stocker l'identifiant de la voiture ajoutée pour une utilisation ultérieure
                this.idVoitureAjoutee = idVoitureAjoutee;

                // Afficher un message de succès
                afficherMessageSucces("Voiture ajoutée avec succès!");
            } else {
                // Afficher un message d'erreur si l'ajout a échoué
                afficherMessageErreur("Impossible d'ajouter la voiture.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur, par exemple afficher un message à l'utilisateur
        }
    }

    @FXML
    private void handleSupprimerButton(ActionEvent event) {
        // Vérifier d'abord si une voiture a été ajoutée récemment et a un identifiant valide
        if (idVoitureAjoutee != -1) {
            // Supprimer la voiture ajoutée récemment en utilisant son identifiant
            try {
                ServiceVoiture serviceVoiture = new ServiceVoiture();
                serviceVoiture.supprimer(idVoitureAjoutee);

                // Afficher un message de succès
                afficherMessageSucces("Voiture supprimée avec succès!");

                // Réinitialiser les champs après la suppression
                marqueField.clear();
                modeleField.clear();
                couleurField.clear();
                matriculeField.clear();

                // Désactiver le bouton de suppression après la suppression réussie
                supprimerButton.setDisable(true);
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur, par exemple afficher un message à l'utilisateur
            }
        } else {
            // Afficher un message d'erreur indiquant qu'aucune voiture n'a été ajoutée récemment
            afficherMessageErreur("Aucune voiture ajoutée récemment pour être supprimée.");
        }
    }

    private void afficherMessageErreur(String message) {
        // Afficher un message d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessageSucces(String message) {
        // Afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
