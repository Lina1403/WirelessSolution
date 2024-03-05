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

    public User getCurrentUserFromDatabase(String email) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        String query = "SELECT * FROM users WHERE mail=?"; // Assuming the email column is named "mail" in your database

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nom");
                    // You can retrieve other user information from the database based on your data model

                    // Create a User object with the retrieved information
                    User currentUser = new User(id, name, email); // Assuming your User has a constructor with these fields

                    return currentUser;
                } else {
                    System.out.println("User not found in the database for email: " + email);
                }
            }
        }

        return null; // If no user is found with the specified email
    }


    public void cancelbtnonaction(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    public void loginbtnonaction(ActionEvent event) {

        if (emailfield.getText().isBlank() == false && passwordfield.getText().isBlank() == false) {
            validatelogin();
        } else {
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
                            // Charger l'interface AfficherAppartement.fxml pour le concierge
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAppartement.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                            // Fermer la fenêtre de connexion
                            Stage loginStage = (Stage) loginbtn.getScene().getWindow();
                            loginStage.close();
                        }else if (role == User.Role.RESIDENT) {
                            System.out.println("Attempting to load AfficherAppartResident.fxml for resident");
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAppartResident.fxml"));
                            try {
                                Parent root = loader.load();
                                AfficherAppartResident controller = loader.getController();
                                if (controller != null) {
                                    String currentUserEmail = emailfield.getText();
                                    System.out.println("Current user email: " + currentUserEmail);
                                    User currentUser = getCurrentUserFromDatabase(currentUserEmail);
                                    if (currentUser != null) {
                                        System.out.println("Initializing data for resident: " + currentUser);
                                        controller.initData(currentUser);

                                        Scene scene = new Scene(root);
                                        Stage stage = new Stage();
                                        stage.setScene(scene);
                                        stage.show();
                                        // Fermer la fenêtre de connexion
                                        Stage loginStage = (Stage) loginbtn.getScene().getWindow();
                                        loginStage.close();
                                    } else {
                                        System.out.println("Failed to get current user from the database");
                                        loginmessagelabel.setText("Echec");
                                    }
                                } else {
                                    System.out.println("Failed to get controller instance for AfficherAppartResident.fxml");
                                    loginmessagelabel.setText("Echec");
                                }
                            } catch (IOException e) {
                                System.out.println("Failed to load AfficherAppartResident.fxml");
                                e.printStackTrace();
                                loginmessagelabel.setText("Echec");
                            }
                        } else {
                            loginmessagelabel.setText("Retry");
                        }
                    }
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}