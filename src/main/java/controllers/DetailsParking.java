package controllers;

import entities.Parking;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.ServiceParking;
import java.sql.SQLException;

public class DetailsParking {

    @FXML
    private Label labelNom;

    @FXML
    private Label labelCapacite;

    @FXML
    private Label labelType;

    @FXML
    private Label labelNombreActuelles;

    private AfficherParking afficherParking;
    private Parking parking;
    private ServiceParking serviceParking;

    public void setAfficherParking(AfficherParking afficherParking) {
        this.afficherParking = afficherParking;
    }

    @FXML
    private void supprimerParking() {
        if (parking != null) {
            try {
                serviceParking.supprimer(parking.getIdParking());
                afficherParking.refreshList();
                Stage stage = (Stage) labelNom.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur de suppression
            }
        }
    }

    public void initData(Parking parking) {
        this.parking = parking;
        labelNom.setText(parking.getNom());
        labelCapacite.setText(Integer.toString(parking.getCapacite()));
        labelType.setText(parking.getType());
        labelNombreActuelles.setText(Integer.toString(parking.getNombreActuelles()));
        serviceParking = new ServiceParking(); // Initialisation du serviceParking
    }
}
