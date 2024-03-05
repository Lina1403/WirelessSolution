package controllers;

import entities.User;
import entities.Voiture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import services.ServiceVoiture;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class ConsulterVoitureUser {

    @FXML
    private ListView<Voiture> voituresListView;

    private User currentUser;

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        System.out.println(currentUser);
        afficherVoituresUtilisateur();
    }

    private void afficherVoituresUtilisateur() {
        try {
            if (currentUser != null) {

                ServiceVoiture serviceVoiture = new ServiceVoiture();
                System.out.println(currentUser);
                Set<Voiture> voitures = serviceVoiture.getAllByUserId(currentUser.getId());
                System.out.println(voitures);
                ObservableList<Voiture> observableVoitures = FXCollections.observableArrayList(voitures);
                voituresListView.setItems(observableVoitures);

            } else {
                System.out.println(currentUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
