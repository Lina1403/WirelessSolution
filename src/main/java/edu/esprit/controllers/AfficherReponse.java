package edu.esprit.controllers;

import edu.esprit.entities.Reponse;
import edu.esprit.services.ServiceReponse;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class AfficherReponse {
    private final ServiceReponse serviceReponse = new ServiceReponse();

    @FXML
    private TableColumn<Reponse, String> columnContenu;

    @FXML
    private TableColumn<Reponse, String> columnDateReponse;

    @FXML
    private TableView<Reponse> tableViewReponses;

    @FXML
    public void initialize() {
        initializeTableColumns();
        loadReponses();
    }

    private void initializeTableColumns() {
        columnContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        columnDateReponse.setCellValueFactory(new PropertyValueFactory<>("dateReponse"));
    }

    private void loadReponses() {
        try {
            List<Reponse> reponseList = serviceReponse.getAll();
            tableViewReponses.setItems(observableArrayList(reponseList));
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception accordingly
        }
    }
}