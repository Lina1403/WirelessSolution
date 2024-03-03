package controllers;


import entities.Voiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import services.ServiceVoiture;

import java.sql.SQLException;

public class DetailsVoiture {

    @FXML
    private TextField textFieldMarque;

    @FXML
    private TextField textFieldModele;

    @FXML
    private TextField textFieldCouleur;

    @FXML
    private TextField textFieldMatricule;

    private Voiture voiture;
    private ServiceVoiture serviceVoiture;
    private AfficherVoitureAdmin afficherVoitureAdmin;

    public void initData(Voiture voiture, AfficherVoitureAdmin afficherVoitureAdmin) {
        this.voiture = voiture;
        this.afficherVoitureAdmin = afficherVoitureAdmin;
        textFieldMarque.setText(voiture.getMarque());
        textFieldModele.setText(voiture.getModel());
        textFieldCouleur.setText(voiture.getCouleur());
        textFieldMatricule.setText(voiture.getMatricule());

        serviceVoiture = new ServiceVoiture();
    }

    @FXML
    private void modifierVoiture(ActionEvent event) {
        if (voiture != null) {
            String nouvelleMarque = textFieldMarque.getText().trim();
            String nouveauModele = textFieldModele.getText().trim();
            String nouvelleCouleur = textFieldCouleur.getText().trim();
            String nouveauMatricule = textFieldMatricule.getText().trim();

            if (validerChamps(nouvelleMarque, nouveauModele, nouvelleCouleur, nouveauMatricule)) {
                // Créer une boîte de dialogue de confirmation
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirmation");
                confirmationDialog.setHeaderText("Modifier la voiture ?");
                confirmationDialog.setContentText("Êtes-vous sûr de vouloir modifier cette voiture ?");

                // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
                confirmationDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // L'utilisateur a cliqué sur OK, modifier la voiture
                        voiture.setMarque(nouvelleMarque);
                        voiture.setModel(nouveauModele);
                        voiture.setCouleur(nouvelleCouleur);
                        voiture.setMatricule(nouveauMatricule);

                        try {
                            serviceVoiture.modifier(voiture);
                            afficherVoitureAdmin.refreshList();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // L'utilisateur a annulé, ne rien faire
                    }
                });
            }
        }
    }



    private boolean validerChamps(String marque, String modele, String couleur, String matricule) {
        return validerMarque(marque) && validerModele(modele) && validerCouleur(couleur) && validerMatricule(matricule);
    }

    private boolean validerMarque(String marque) {
        if (marque.isEmpty()) {
            afficherErreur("Erreur", "Veuillez saisir une marque pour la voiture.");
            return false;
        } else if (!marque.matches("[a-zA-Z]{1,20}")) {
            afficherErreur("Erreur", "La marque doit contenir uniquement des lettres et avoir au maximum 20 caractères.");
            return false;
        }
        return true;
    }

    private boolean validerModele(String modele) {
        if (modele.isEmpty()) {
            afficherErreur("Erreur", "Veuillez saisir un modèle pour la voiture.");
            return false;
        } else if (!modele.matches("[a-zA-Z0-9]{1,20}")) {
            afficherErreur("Erreur", "Le modèle doit contenir des lettres et des chiffres et avoir au maximum 20 caractères.");
            return false;
        }
        return true;
    }

    private boolean validerCouleur(String couleur) {
        if (couleur.isEmpty()) {
            afficherErreur("Erreur", "Veuillez saisir une couleur pour la voiture.");
            return false;
        } else if (!couleur.matches("[a-zA-Z]{1,20}")) {
            afficherErreur("Erreur", "La couleur doit contenir uniquement des lettres et avoir au maximum 20 caractères.");
            return false;
        }
        return true;
    }

    private boolean validerMatricule(String matricule) {
        if (matricule.isEmpty()) {
            afficherErreur("Erreur", "Veuillez saisir un matricule pour la voiture.");
            return false;
        } else if (!matricule.matches("[a-zA-Z0-9]{6,15}")) {
            afficherErreur("Erreur", "Le matricule doit contenir des lettres et des chiffres et avoir entre 6 et 15 caractères.");
            return false;
        }
        return true;
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    private void supprimerVoiture(ActionEvent event) {
        if (voiture != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer cette voiture ?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        serviceVoiture.supprimer(voiture.getIdVoiture());
                        afficherVoitureAdmin.refreshList();
                        // Fermer la fenêtre de détails de voiture
                        textFieldMarque.getScene().getWindow().hide();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
