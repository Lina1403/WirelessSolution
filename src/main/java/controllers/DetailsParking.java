// DetailsParking.java
package controllers;

import entities.Parking;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailsParking {

    @FXML
    private Label labelNom;

    @FXML
    private Label labelCapacite;

    @FXML
    private Label labelType;

    @FXML
    private Label labelNombreActuelles;

    public void initData(Parking parking) {
        labelNom.setText(parking.getNom());
        labelCapacite.setText(Integer.toString(parking.getCapacite()));
        labelType.setText(parking.getType());
        labelNombreActuelles.setText(Integer.toString(parking.getNombreActuelles()));
    }
}
