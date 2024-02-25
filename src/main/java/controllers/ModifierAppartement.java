package controllers;

import entities.Appartement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.ServiceAppartemment;

import java.sql.SQLException;

public class ModifierAppartement {

    @FXML
    private TextField id_NomResident_modifier;

    @FXML
    private TextField id_numAppartement_modifier;

    @FXML
    private TextField id_taille_modifier;

    @FXML
    private ComboBox<String> typeComboBox;

    private final ServiceAppartemment serviceAppartement = new ServiceAppartemment();

    private Appartement appartementSelectionne;

    public void setAppartementSelectionne(Appartement appartement) {
        this.appartementSelectionne = appartement;
        if (appartement != null) {
            // Afficher les données de l'appartement sélectionné dans les champs de texte et de ComboBox
            id_NomResident_modifier.setText(appartement.getNomResident());
            id_numAppartement_modifier.setText(String.valueOf(appartement.getNumAppartement()));
            id_taille_modifier.setText(appartement.getTaille());
            // Assurez-vous que le statut de l'appartement est sélectionné dans le ComboBox
            typeComboBox.setValue(appartement.getStatutAppartement().toString());
        }

    }

    @FXML
    void modifierAppartement(ActionEvent event) {
        if (appartementSelectionne != null) {
            try {
                // Récupérer les nouvelles valeurs des champs de texte et de ComboBox
                String nomResident = id_NomResident_modifier.getText();
                String taille = id_taille_modifier.getText();
                Appartement.statutAppartement statut = Appartement.statutAppartement.valueOf(typeComboBox.getValue());

                // Mettre à jour les attributs de l'appartement sélectionné
                appartementSelectionne.setNomResident(nomResident);
                appartementSelectionne.setTaille(taille);
                appartementSelectionne.setStatutAppartement(statut);

                // Appeler la méthode pour modifier l'appartement dans la base de données
                serviceAppartement.modifier(appartementSelectionne);

                System.out.println("Appartement modifié avec succès !");
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer SQLException de manière appropriée
            }
        } else {
            System.out.println("Aucun appartement sélectionné.");
        }
    }
}
