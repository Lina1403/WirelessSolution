package controllers;

import entities.Appartement;
import javafx.scene.control.ListCell;

public class AppartementCell extends ListCell<Appartement> {

    @Override
    protected void updateItem(Appartement appartement, boolean empty) {
        super.updateItem(appartement, empty);

        if (empty || appartement == null) {
            setText(null);
        } else {
            // Personnalisez l'affichage de la cellule ici
            setText("Numéro d'appartement : " + appartement.getNumAppartement() +
                    ", Résident : " + appartement.getNomResident() +
                    ", Etage : " + appartement.getNbrEtage()+
                    ", Statut : " + appartement.getStatutAppartement()+
                    ", Taille : " + appartement.getTaille());
        }
    }
}
