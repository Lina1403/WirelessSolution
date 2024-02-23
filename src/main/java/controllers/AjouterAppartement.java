package controllers;

import entities.Appartement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.ServiceAppartemment;

public class AjouterAppartement {

    @FXML
    private TextField nbrEtageField;

    @FXML
    private TextField nomResidentField;

    @FXML
    private TextField numAppartementField;

    @FXML
    private ComboBox<?> statutComboBox; // Modifié le type de ComboBox

    @FXML
    private TextField tailleField;

    @FXML
    private Text title;

    // Instance de ServiceAppartement pour gérer les opérations sur les appartements
    private final ServiceAppartemment serviceAppartement = new ServiceAppartemment();

    @FXML
    void AjouterAppartement(ActionEvent event) {
        try {
            // Récupérer les valeurs des champs du formulaire
            int numAppartement = Integer.parseInt(numAppartementField.getText());
            String nomResident = nomResidentField.getText();
            String taille = tailleField.getText();
            int nbrEtage = Integer.parseInt(nbrEtageField.getText());
           // Récupérer la valeur sélectionnée du ComboBox directement comme String
            String statut = (String) statutComboBox.getSelectionModel().getSelectedItem();

            // Créer un nouvel objet Appartement avec les valeurs récupérées
            Appartement appartement = new Appartement();
            appartement.setNumAppartement(numAppartement);
            appartement.setNomResident(nomResident);
            appartement.setTaille(taille);
            appartement.setNbrEtage(nbrEtage);
            appartement.setStatutAppartement(Appartement.statutAppartement.valueOf(statut)); // Plus besoin de convertir en enum

            // Appeler la méthode d'ajout d'appartement du service approprié
            serviceAppartement.ajouter(appartement);

            // Afficher une confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Appartement ajouté avec succès.");
            alert.showAndWait();

            // Effacer les champs après l'ajout réussi
            effacerChamps();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de format numérique
            afficherAlerteErreur("Erreur de format", "Veuillez entrer des valeurs numériques valides.");
        } catch (Exception e) {
            // Gérer les autres exceptions
            afficherAlerteErreur("Erreur", "Une erreur s'est produite lors de l'ajout de l'appartement : " + e.getMessage());
        }
    }

    // Méthode utilitaire pour effacer les champs après l'ajout réussi
    private void effacerChamps() {
        numAppartementField.clear();
        nomResidentField.clear();
        tailleField.clear();
        nbrEtageField.clear();
        statutComboBox.getSelectionModel().clearSelection();
    }

    // Méthode utilitaire pour afficher une alerte d'erreur
    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
