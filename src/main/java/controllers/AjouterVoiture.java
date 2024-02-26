package controllers;

import entities.Parking;
import entities.Voiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceParking;
import services.ServiceVoiture;

import java.sql.SQLException;
import java.util.regex.Pattern;

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
    private Button supprimerButton;

    @FXML
    private Button ajouterButton;

    private Parking selectedParking;
    private int idVoitureAjoutee;

    private AfficherVoitureAdmin afficherVoitureAdmin;

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
        // Désactiver le bouton d'ajout pour éviter les clics multiples
        ajouterButton.setDisable(true);

        String marque = marqueField.getText();
        String modele = modeleField.getText();
        String couleur = couleurField.getText();
        String matricule = matriculeField.getText();

        // Validation des champs
        if (!validateMarque(marque) || !validateModele(modele) || !validateCouleur(couleur) || !validateMatricule(matricule)) {
            return;
        }

        Voiture voiture = new Voiture(selectedParking.getIdParking(), marque, modele, couleur, matricule, selectedParking);

        try {
            ServiceVoiture serviceVoiture = new ServiceVoiture();
            int idVoitureAjoutee = serviceVoiture.ajouter(voiture);

            if (idVoitureAjoutee != -1) {
                // Mettre à jour le nombre actuel de voitures dans le parking
                selectedParking.setNombreActuelles(selectedParking.getNombreActuelles() + 1);
                ServiceParking serviceParking = new ServiceParking();
                serviceParking.modifier(selectedParking);
                supprimerButton.setDisable(false);
                this.idVoitureAjoutee = idVoitureAjoutee;
                afficherMessageSucces("Voiture ajoutée avec succès!");
            } else {
                afficherMessageErreur("Impossible d'ajouter la voiture.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            // Réactiver le bouton d'ajout après le processus d'ajout
            ajouterButton.setDisable(false);
        }
    }

    @FXML
    private void handleSupprimerButton(ActionEvent event) {
        if (idVoitureAjoutee != -1) {
            try {
                ServiceVoiture serviceVoiture = new ServiceVoiture();
                serviceVoiture.supprimer(idVoitureAjoutee);
                afficherMessageSucces("Voiture supprimée avec succès!");
                marqueField.clear();
                modeleField.clear();
                couleurField.clear();
                matriculeField.clear();
                supprimerButton.setDisable(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            afficherMessageErreur("Aucune voiture ajoutée récemment pour être supprimée.");
        }
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessageSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateMarque(String marque) {
        if (marque.isEmpty()) {
            afficherMessageErreur("Veuillez saisir la marque de la voiture.");
            return false;
        }
        if (marque.length() > 20) {
            afficherMessageErreur("La marque ne peut pas dépasser 20 caractères.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z]*$", marque)) {
            afficherMessageErreur("La marque doit contenir uniquement des lettres.");
            return false;
        }
        return true;
    }

    private boolean validateModele(String modele) {
        if (modele.isEmpty()) {
            afficherMessageErreur("Veuillez saisir le modèle de la voiture.");
            return false;
        }
        if (modele.length() > 20) {
            afficherMessageErreur("Le modèle ne peut pas dépasser 20 caractères.");
            return false;
        }
        if (!modele.matches("^[a-zA-Z0-9\\s]+$")) {
            afficherMessageErreur("Le modèle doit contenir uniquement des lettres, des espaces ou des chiffres.");
            return false;
        }
        return true;
    }

    private boolean validateCouleur(String couleur) {
        if (couleur.isEmpty()) {
            afficherMessageErreur("Veuillez saisir la couleur de la voiture.");
            return false;
        }
        if (couleur.length() > 20) {
            afficherMessageErreur("La couleur ne peut pas dépasser 20 caractères.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z]*$", couleur)) {
            afficherMessageErreur("La couleur doit contenir uniquement des lettres.");
            return false;
        }
        return true;
    }

    private boolean validateMatricule(String matricule) {
        if (matricule.isEmpty()) {
            afficherMessageErreur("Veuillez saisir le numéro de matricule de la voiture.");
            return false;
        }
        if (matricule.length() < 6 || matricule.length() > 15) {
            afficherMessageErreur("Le numéro de matricule doit contenir entre 6 et 15 caractères.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z0-9]*$", matricule)) {
            afficherMessageErreur("Le numéro de matricule doit contenir uniquement des lettres et des chiffres.");
            return false;
        }
        return true;
    }
}
