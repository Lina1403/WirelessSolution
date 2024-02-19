package controllers;

import entities.Parking;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.IService;
import services.ServiceParking;
import java.sql.SQLException;

public class AjouterParking {

    @FXML
    private TextField PlaceId;

    @FXML
    private TextField NumPlaceId;

    @FXML
    private TextField CapaciteId;

    private final IService<Parking> serviceParking = new ServiceParking();

    @FXML
    void ajouterParking() {
        String place = PlaceId.getText();
        int numPlace = Integer.parseInt(NumPlaceId.getText());
        int capacite = Integer.parseInt(CapaciteId.getText());

        Parking parking = new Parking(place, numPlace, capacite);
        try {
            serviceParking.ajouter(parking);
            System.out.println("Parking ajouté !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
