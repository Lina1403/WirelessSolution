package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.DataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class Login {

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField emailfield;

    @FXML
    private Button loginbtn;

    @FXML
    private PasswordField passwordfield;
    @FXML
    private Label loginmessagelabel;
    public void cancelbtnonaction(ActionEvent event)
    {
        Stage stage =(Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    public void loginbtnonaction(ActionEvent event){

        if(emailfield.getText().isBlank()==false && passwordfield.getText().isBlank()==false){
            validatelogin();
        }
        else {
            loginmessagelabel.setText("fill all the spots");
        }

    }

    public void validatelogin() {
        Connection cnx = DataSource.getInstance().getCnx();
        String verifyLogin = "SELECT count(1), role FROM users WHERE mail=? AND password=?";
        try (PreparedStatement statement = cnx.prepareStatement(verifyLogin)) {
            statement.setString(1, emailfield.getText());
            statement.setString(2, passwordfield.getText());
            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next()) {
                    int count = queryResult.getInt(1);
                    if (count == 1) {
                        String roleString = queryResult.getString("role");
                        User.Role role = User.Role.valueOf(roleString.toUpperCase());
                        if (role == User.Role.CONCIERGE) {
                            // Charger l'interface AfficherAppartementfxml pour le concierge
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParking.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                            // Fermer la fenêtre de connexion
                            Stage loginStage = (Stage) loginbtn.getScene().getWindow();
                            loginStage.close();
                        } else if (role == User.Role.RESIDENT) {
                            // Charger l'interface pour l'utilisateur normal
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVoiture.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                            // Fermer la fenêtre de connexion
                            Stage loginStage = (Stage) loginbtn.getScene().getWindow();
                            loginStage.close();
                        } else {
                            loginmessagelabel.setText("Congrats"); // Cela peut être modifié en fonction de l'interface utilisateur
                        }
                    } else {
                        loginmessagelabel.setText("Retry");
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }




}
