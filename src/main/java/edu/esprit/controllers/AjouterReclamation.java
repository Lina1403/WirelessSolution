package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceReponse;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AjouterReclamation {
    private final ServiceReclamation rs = new ServiceReclamation();
    private final ServiceUser su = new ServiceUser();

    @FXML
    private TextField CategorieRecTF;

    @FXML
    private TextField descriRecTF;

    @FXML
    void ajouter(ActionEvent event) {
        if (CategorieRecTF.getText().isEmpty() || descriRecTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields.");
            return;
        }
        Reclamation r = new Reclamation();
        r.setCategorieRec(CategorieRecTF.getText());
        r.setStatutRec("En cours");
        r.setDescriRec(descriRecTF.getText());
        r.setUser(su.getOneById(2)); // Assuming the user ID is obtained dynamically in real scenarios
        r.setDateRec(new Date(System.currentTimeMillis()));

        try {
            rs.ajouter(r);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Reclamation added successfully.");
            // Here, you might want to call a method to refresh the TableView in AfficherReclamation, if it's already open.
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add reclamation:\n" + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}