package controllers;

import entities.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.IService;
import services.ServiceParking;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class AfficherParking {

    @FXML
    private TableView<Parking> tableParking;

    @FXML
    private TableColumn<Parking, String> colPlace;

    @FXML
    private TableColumn<Parking, Integer> colNumPlace;

    @FXML
    private TableColumn<Parking, Integer> colCapacite;

    private final IService<Parking> serviceParking = new ServiceParking();

    @FXML
    void initialize() throws SQLException  {

            Set<Parking> parkings = serviceParking.getAll();
            ObservableList<Parking> parkingList = FXCollections.observableArrayList(parkings);

            colPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
            colNumPlace.setCellValueFactory(new PropertyValueFactory<>("numPlace"));
            colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));

            tableParking.setItems(parkingList);

    }
}
