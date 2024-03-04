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
    private Reclamation selectedReclamation;

    @FXML
    private TextField comTF;

    public void setReclamation(Reclamation reclamation) {
        this.selectedReclamation = reclamation;
    }

    @FXML
    void ajouter(ActionEvent event) {
        if (selectedReclamation != null && !comTF.getText().isEmpty()) {
            Reponse reponse = new Reponse();
            reponse.setReclamation(selectedReclamation);
            reponse.setContenu(comTF.getText());
            reponse.setDateReponse(new Date());

            try {
                serviceReponse.ajouter(reponse);
                comTF.clear(); // Clear the input field after adding
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No reclamation selected or content is empty.");
        }
    }
}