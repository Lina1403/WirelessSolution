package controllers;

import entities.Discussion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import services.DiscussionService;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ListeDiscussion {
    @FXML
    private TableView<Discussion> table;
    @FXML
    private TableColumn<Discussion, String> titre = new TableColumn<>("titre");
    @FXML
    private TableColumn<Discussion, String> createur = new TableColumn<>("createur");
    @FXML
    private TableColumn<Discussion, Timestamp> dateCreation= new TableColumn<>("TimeStampCreation");


    public void initialize() throws SQLException {
        DiscussionService ds = new DiscussionService();
        ObservableList<Discussion> discussions = FXCollections.observableList(ds.afficher());
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        createur.setCellValueFactory(cellData -> {
            String creatorName = cellData.getValue().getCreateur().getName();
            return new SimpleStringProperty(creatorName);
        });
        dateCreation.setCellValueFactory(new PropertyValueFactory<>("TimeStampCreation"));
        table.setItems(discussions);
        onClick();
    }
    public void onClick(){
        table.setRowFactory(tv -> {
            TableRow<Discussion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Discussion clickedRow = row.getItem();
                    // Récupérez l'ID de la discussion
                    int discussionId = clickedRow.getId();


                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Discussion added successfully!"+ discussionId);

                    alert.showAndWait();
                }
            });
            return row ;
        });
    }
}
