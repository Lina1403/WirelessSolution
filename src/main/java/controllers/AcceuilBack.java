package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AcceuilBack {

    @FXML
    private void allerVersBtnReservation(ActionEvent event) {
        chargerVue("/BtnReservation.fxml", event);
    }

    private void chargerVue(String cheminFxml, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFxml));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
