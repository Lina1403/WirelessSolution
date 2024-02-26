package controllers;

import entities.Voiture;
import javafx.event.ActionEvent; // Correction de l'import
import javafx.fxml.FXML;
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
    private AfficherVoitureAdmin afficherVoitureAdmin;


    private Voiture voiture;
    private ServiceVoiture serviceVoiture;

    public void initData(Voiture voiture, AfficherVoitureAdmin afficherVoitureAdmin) {
        this.voiture = voiture;
        this.afficherVoitureAdmin = afficherVoitureAdmin; // Ajouter un champ pour stocker l'instance de AfficherVoitureAdmin
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
                afficherVoitureAdmin.refreshList(); // Utiliser l'instance de AfficherVoitureAdmin pour rafraîchir la liste
                // Vous pouvez également afficher un message de confirmation ici
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur de modification
            }
        }
    }

}
