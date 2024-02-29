package edu.esprit.controllers;

import edu.esprit.entities.Reponse;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReponse;
import edu.esprit.services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AjouterReponse {
    private final ServiceReponse serviceReponse = new ServiceReponse();
    private final ServiceReclamation serviceReclamation = new ServiceReclamation();
    private Reclamation selectedReclamation;

    @FXML
    private TextField comTF;

    @FXML
    private TableColumn<Reponse, String> CvCom;

    @FXML
    private TableColumn<Reponse, String> CvNomA;

    @FXML
    private TableView<Reponse> TableViewComA;

    @FXML
    private Button ajouter;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    public void initialize() {
        // Initialize table columns
        CvCom.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        CvNomA.setCellValueFactory(new PropertyValueFactory<>("user"));

        // Load responses for selected reclamation
        loadResponses();
    }

    public void setReclamation(Reclamation reclamation) {
        this.selectedReclamation = reclamation;
        loadResponses();
    }

    private void loadResponses() {
        if (selectedReclamation != null) {
            try {
                List<Reponse> reponses = serviceReponse.getAllByReclamationId(selectedReclamation.getIdRec());
                TableViewComA.setItems(FXCollections.observableArrayList(reponses));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void ajouter(ActionEvent event) {
        if (selectedReclamation != null) {
            Reponse reponse = new Reponse();
            reponse.setReclamation(selectedReclamation);
            reponse.setContenu(comTF.getText());
            reponse.setDateReponse(new Date());

            try {
                serviceReponse.ajouter(reponse);
                loadResponses(); // Refresh table data
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No reclamation selected.");
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        Reponse selectedReponse = TableViewComA.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            // Update the response content with the text from the TextField
            selectedReponse.setContenu(comTF.getText());
            selectedReponse.setDateReponse(new Date());
            try {
                // Call the modify method to update the response in the database
                serviceReponse.modifier(selectedReponse);
                // Refresh table data
                loadResponses();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        Reponse selectedReponse = TableViewComA.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            try {
                // Delete the response from the database
                serviceReponse.supprimer(selectedReponse.getIdReponse());
                // Refresh table data
                loadResponses();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}