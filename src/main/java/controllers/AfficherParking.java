package controllers;

import entities.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import services.IService;
import services.ServiceParking;

import java.sql.SQLException;
import java.util.Optional;

public class AfficherParking {

    @FXML
    private ListView<Parking> listViewParkings;

    private final IService<Parking> serviceParking = new ServiceParking();
    private ObservableList<Parking> parkingList;

    @FXML
    void initialize() {
        try {
            refreshListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listViewParkings.setOnMouseClicked(event -> {
            Parking selectedParking = listViewParkings.getSelectionModel().getSelectedItem();
            if (selectedParking != null) {
                modifyParking(selectedParking);
            }
        });
    }

    private void refreshListView() throws SQLException {
        parkingList = FXCollections.observableArrayList(serviceParking.getAll());
        listViewParkings.setItems(parkingList);
    }

    private void modifyParking(Parking parking) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modifier ou supprimer le parking");
        alert.setHeaderText("Que voulez-vous faire avec ce parking ?");
        alert.setContentText("Choisissez une option :");

        ButtonType btnModify = new ButtonType("Modifier");
        ButtonType btnDelete = new ButtonType("Supprimer");
        ButtonType btnCancel = new ButtonType("Annuler");

        alert.getButtonTypes().setAll(btnModify, btnDelete, btnCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == btnModify) {
                // Ouvrir une nouvelle fenêtre ou une boîte de dialogue pour modifier le parking
                // Vous pouvez appeler une méthode de modification dans votre service ServiceParking
                System.out.println("Modifier le parking : " + parking.getNom());
            } else if (result.get() == btnDelete) {
                // Demander confirmation pour supprimer le parking
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation de suppression");
                confirmationAlert.setHeaderText("Supprimer le parking ?");
                confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce parking ?");

                Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
                if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                    // Supprimer le parking
                    try {
                        serviceParking.supprimer(parking.getIdParking());
                        refreshListView(); // Rafraîchir la liste après la suppression
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Gérer l'exception
                    }
                }
            } else {
                // Annuler
                alert.close();
            }
        }
    }

}
