package controllers;

import entities.Appartement;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import services.ServiceAppartemment;

import java.sql.SQLException;
import java.util.Set;

public class AfficherAppartResident {

    private final ServiceAppartemment serviceAppartemment = new ServiceAppartemment();

    @FXML
    private ListView<Appartement> listView;


    private User currentUser;

    public void initData(User user) throws SQLException {
        if (currentUser == null) {
            System.out.println("user n'existe pas");
        }
        this.currentUser = user;
        System.out.println("user connectee : " + currentUser);
        initialize();
    }


    @FXML
    private void initialize() {
        // Assurez-vous que connectedUser n'est pas null avant d'appeler la méthode
        if (currentUser != null) {
            try {
                Set<Appartement> appartements = serviceAppartemment.getAppartementsForUser(currentUser);
                System.out.println(appartements);
                updateListView(appartements);
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception selon vos besoins
            }
        }

    }


    private void updateListView(Set<Appartement> appartements) {
        try {
            ObservableList<Appartement> observableList = FXCollections.observableArrayList(appartements);
            listView.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating the list of apartments.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
