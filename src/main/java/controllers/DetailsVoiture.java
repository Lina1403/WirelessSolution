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
            voiture.setMarque(textFieldMarque.getText());
            voiture.setModel(textFieldModele.getText());
            voiture.setCouleur(textFieldCouleur.getText());
            voiture.setMatricule(textFieldMatricule.getText());

            try {
                serviceVoiture.modifier(voiture);
                afficherVoitureAdmin.refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
