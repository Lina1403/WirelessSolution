package controllers;

import entities.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceParking;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
public class DetailsParking {

    @FXML
    private TextField textFieldNom;

    @FXML
    private TextField textFieldCapacite;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private TextField textFieldNombreActuelles;

    private AfficherParking afficherParking;
    private Parking parking;
    private ServiceParking serviceParking;

    public void setAfficherParking(AfficherParking afficherParking) {
        this.afficherParking = afficherParking;
    }
    @FXML
    private void modifierParking() {
        if (parking != null) {
            try {
                // Mettre à jour les détails du parking avec les valeurs des champs de texte
                parking.setNom(textFieldNom.getText());
                parking.setCapacite(Integer.parseInt(textFieldCapacite.getText()));
                parking.setType(comboBoxType.getValue());
                parking.setNombreActuelles(Integer.parseInt(textFieldNombreActuelles.getText()));

                // Appeler le service pour modifier le parking dans la base de données
                serviceParking.modifier(parking);

                // Actualiser la liste dans la vue principale
                afficherParking.refreshList();

                // Fermer la fenêtre de détails
                fermerFenetreDetails();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur de modification
            }
        }
    }
    @FXML
    private void supprimerParking() {
        if (parking != null) {
            try {
                serviceParking.supprimer(parking.getIdParking());
                afficherParking.refreshList();
                Stage stage = (Stage) textFieldNom.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur de suppression
            }
        }
    }

    public void initData(Parking parking) {
        this.parking = parking;
        // Remplir les champs avec les détails du parking passé en paramètre
        textFieldNom.setText(parking.getNom());
        textFieldCapacite.setText(Integer.toString(parking.getCapacite()));
        textFieldNombreActuelles.setText(Integer.toString(parking.getNombreActuelles()));

        // Charger les types disponibles dans la ComboBox
        chargerTypes();

        // Sélectionner le type du parking actuel dans la ComboBox
        comboBoxType.setValue(parking.getType());

        serviceParking = new ServiceParking(); // Initialisation du serviceParking
    }

    private void chargerTypes() {
        // Vous pouvez obtenir les types à partir de votre source de données, ici je les ai définis manuellement
        List<String> types = Arrays.asList("sous-sol", "pleine air", "couverte");

        ObservableList<String> typeOptions = FXCollections.observableArrayList(types);
        comboBoxType.setItems(typeOptions);
    }

    private void fermerFenetreDetails() {
        Stage stage = (Stage) textFieldNom.getScene().getWindow();
        stage.close();
    }
}