package controllers;

import entities.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
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

    @FXML
    private TableColumn<Parking, Void> colAction;

    @FXML
    private TableColumn<Parking, Void> colDelete;

    private final IService<Parking> serviceParking = new ServiceParking();

    @FXML
    void initialize() throws SQLException  {

        Set<Parking> parkings = serviceParking.getAll();
        ObservableList<Parking> parkingList = FXCollections.observableArrayList(parkings);

        colPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
        colNumPlace.setCellValueFactory(new PropertyValueFactory<>("numPlace"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        // Ajout de la colonne de suppression
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    deleteButton.setOnAction(event -> {
                        Parking parking = getTableView().getItems().get(getIndex());
                        // Supprimer l'élément
                        try {
                            serviceParking.supprimer(parking.getIdParking());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        tableParking.getItems().remove(parking);
                    });


                    setGraphic(deleteButton);
                }
            }
        });

        tableParking.setItems(parkingList);
    }

}
