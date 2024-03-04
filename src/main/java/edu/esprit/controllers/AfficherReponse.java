package edu.esprit.controllers;

import edu.esprit.entities.Reponse;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReponse;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class AfficherReponse {
    private final ServiceReponse serviceReponse = new ServiceReponse();
    private Reclamation selectedReclamation;

    @FXML
    private TextField comTF;

    @FXML
    private ListView<Reponse> listViewReponses;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    public void setReclamation(Reclamation reclamation) {
        this.selectedReclamation = reclamation;
        loadResponses();
    }

    private void loadResponses() {
        if (selectedReclamation != null) {
            try {
                List<Reponse> reponses = serviceReponse.getAllByReclamationId(selectedReclamation.getIdRec());
                listViewReponses.setItems(FXCollections.observableArrayList(reponses));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        Reponse selectedReponse = listViewReponses.getSelectionModel().getSelectedItem();
        if (selectedReponse != null && !comTF.getText().isEmpty()) {
            selectedReponse.setContenu(comTF.getText());
            selectedReponse.setDateReponse(new Date());
            try {
                serviceReponse.modifier(selectedReponse);
                loadResponses(); // Refresh the list view
                comTF.clear(); // Clear the input field
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        Reponse selectedReponse = listViewReponses.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            try {
                serviceReponse.supprimer(selectedReponse.getIdReponse());
                loadResponses(); // Refresh the list view
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}