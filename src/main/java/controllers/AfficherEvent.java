package controllers;

import entities.Espace;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.IService;
import services.ServiceEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

public class AfficherEvent {

    @FXML
    private TableView<Event> tableEvent;

    @FXML
    private TableColumn<Event, String> colName;

    @FXML
    private TableColumn<Event, String> colEmail;

    @FXML
    private TableColumn<Event, String> colTitle;

    @FXML
    private TableColumn<Event, Date> colDate;

    @FXML
    private TableColumn<Event, Integer> colNbrPersonne;

    @FXML
    private TableColumn<Event, String> colDescription;

    @FXML
    private TableColumn<Event, Espace> colEspace;

    private final IService<Event> serviceEvent = new ServiceEvent();

    @FXML
    void initialize() throws SQLException  {

        Set<Event> events = serviceEvent.getAll();
        ObservableList<Event> eventList = FXCollections.observableArrayList(events);

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNbrPersonne.setCellValueFactory(new PropertyValueFactory<>("nbrPersonne"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colEspace.setCellValueFactory(new PropertyValueFactory<>("espace"));

        tableEvent.setItems(eventList);

    }

}
