package controllers;

import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.ServiceEvent;
//import services.ServiceUser;

import java.sql.SQLException;
import java.util.Set;

public class EventAjoutes {

    @FXML
    private TableView<Event> eventTableView;

    @FXML
    private TableColumn<Event, String> titleColumn;

    @FXML
    private TableColumn<Event, String> dateColumn;

    @FXML
    private TableColumn<Event, Integer> nbrPersonneColumn;

    @FXML
    private TableColumn<Event, String> listeInvitesColumn;

    private final ServiceEvent serviceEvent = new ServiceEvent();
 //   private final ServiceUser serviceUser = new ServiceUser();


 /*   void initialize() {
        // Initialise les colonnes de la table
   //     titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
     //   dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
       // nbrPersonneColumn.setCellValueFactory(cellData -> cellData.getValue().nbrPersonneProperty().asObject());
        //listeInvitesColumn.setCellValueFactory(cellData -> cellData.getValue().listeInvitesProperty());

        try {
            // Récupère les événements ajoutés par l'utilisateur
      //      Set<Event> events = serviceUser.getEventsByUser(serviceUser.getCurrentUser());

            // Convertit l'ensemble d'événements en une liste observable pour l'afficher dans TableView
 //           ObservableList<Event> eventList = FXCollections.observableArrayList(events);

            // Remplit la table avec les événements
            eventTableView.setItems(eventList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Affiche une alerte en cas d'erreur lors de la récupération des événements
            // Vous pouvez ajouter votre propre logique pour gérer cette situation
        }
    }
*/}
