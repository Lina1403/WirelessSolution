package controllers;

import entities.Voiture;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailsVoiture {

    @FXML
    private Label labelMarque;

    @FXML
    private Label labelModele;

    @FXML
    private Label labelCouleur;
    @FXML
    private Label labelMatricule;


    // Autres labels pour afficher les détails de la voiture...

    public void initData(Voiture voiture) {
        // Initialisez les labels avec les détails de la voiture
        labelMarque.setText(voiture.getMarque());
        labelModele.setText(voiture.getModel());
        labelCouleur.setText(voiture.getCouleur());
        labelMatricule.setText(voiture.getMatricule());

        // Initialisez d'autres labels avec les détails de la voiture...
    }
}
