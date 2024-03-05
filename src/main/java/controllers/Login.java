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

    public void cancelbtnonaction(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    public void loginbtnonaction(ActionEvent event) throws SQLException {

        if (emailfield.getText().isBlank() == false && passwordfield.getText().isBlank() == false) {
            validatelogin();
        } else {
            loginmessagelabel.setText("fill all the spots");
        }

    }

    public User getCurrentUserFromDatabase(String email) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        String query = "SELECT * FROM user WHERE mail=?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nom");
                    // Vous pouvez récupérer d'autres informations de l'utilisateur à partir de la base de données
                    // selon votre modèle de données

                    // Créer un objet User avec les informations récupérées
                    User currentUser = new User(id, name, email); // Supposons que votre User a un constructeur avec ces champs

                    return currentUser;
                }
            }
        }

        return null; // Si aucun utilisateur n'est trouvé avec l'email spécifié
    }

    public void validatelogin() {
        Connection cnx = DataSource.getInstance().getCnx();
        String verifyLogin = "SELECT count(1), role FROM user WHERE mail=? AND password=?";
        try (PreparedStatement statement = cnx.prepareStatement(verifyLogin)) {
            statement.setString(1, emailfield.getText());
            statement.setString(2, passwordfield.getText());
            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next()) {
                    int count = queryResult.getInt(1);
                    if (count == 1) {
                        String roleString = queryResult.getString("role");
                        User.Role role = User.Role.valueOf(roleString.toUpperCase());
                        if (role == User.Role.RESIDENT) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVoiture.fxml"));
                                Parent root = loader.load();

                                AjouterVoiture controller = loader.getController();
                                if (controller != null) {
                                    String currentUserEmail = emailfield.getText();
                                    User currentUser = getCurrentUserFromDatabase(currentUserEmail);
                                    controller.setCurrentUser(currentUser);

                                    // Afficher la scène
                                    Scene scene = new Scene(root);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.show();

                                    // Fermer la fenêtre de connexion
                                    Stage loginStage = (Stage) loginbtn.getScene().getWindow();
                                    loginStage.close();
                                } else {
                                    System.out.println("Failed to retrieve AjouterVoiture controller");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (role == User.Role.CONCIERGE) {
                            // Logique pour les concierges
                            // Par exemple, ouvrir une interface spécifique pour les concierges.
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/BtnParking.fxml"));
                                Parent root = loader.load();

                                BtnParking controller = loader.getController();
                                if (controller != null) {
                                    String currentUserEmail = emailfield.getText();
                                    User currentUser = getCurrentUserFromDatabase(currentUserEmail);
                                    controller.setCurrentUser(currentUser);

                                    // Afficher la scène
                                    Scene scene = new Scene(root);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.show();

                                    // Fermer la fenêtre de connexion
                                    Stage loginStage = (Stage) loginbtn.getScene().getWindow();
                                    loginStage.close();
                                } else {
                                    System.out.println("Failed to retrieve InterfaceConcierge controller");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loginmessagelabel.setText("Retry");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
