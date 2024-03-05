package controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.Datasource;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    @FXML
    private ToggleButton toggleButton;

    @FXML
    private Label ShownPassword;
    private String captchaChallenge;
    public void cancelbtnonaction(ActionEvent event)
    {
        Stage stage =(Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    public void loginbtnonaction(ActionEvent event){

        if(emailfield.getText().isBlank()==false && passwordfield.getText().isBlank()==false){
           validateLogin(event);
        }
        else {
            loginmessagelabel.setText("fill all the spots");
        }

    }
    public void validateLogin(ActionEvent event) {
        try {
            // Connect to the database
            Connection cnx = Datasource.getInstance().getCnx();

            // Prepare the login verification query
            String verifyLogin = "SELECT * FROM user WHERE mail='" + emailfield.getText() + "' AND password='" + passwordfield.getText() + "'";

            // Create a prepared statement for security (preventing SQL injection)
            PreparedStatement statement = cnx.prepareStatement(verifyLogin);

            // Execute the query
            ResultSet queryResult = statement.executeQuery();

            // Check if a user exists with the provided credentials
            if (queryResult.next()) {
                String role = queryResult.getString("role"); // Get the user's role from the result set
            try{
                if (role.equals("ADMIN")) {
                    Parent page1 = FXMLLoader.load(getClass().getResource("/add.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } else if (role.equals("RESIDENT")) {
                    // Load the jaw.fxml scene for the resident user
                    Parent page2 = FXMLLoader.load(getClass().getResource("/resident.fxml"));
                    Scene scene = new Scene(page2);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }
                else if (role.equals("CONCIERGE")) {
                    // Load the jaw.fxml scene for the resident user
                    Parent page2 = FXMLLoader.load(getClass().getResource("/concierge.fxml"));
                    Scene scene = new Scene(page2);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    // Handle invalid role (optional: log error or display a message)
                    System.err.println("Invalid user role: " + role);
                    loginmessagelabel.setText("Invalid role. Please contact support.");
                }
            }catch (IOException e) {  // Catch FXML loading exceptions
                System.out.println("Error loading FXML: " + e.getMessage());
            }
            } else {  // User not found
                System.out.println("Invalid credentials.");  // Display error message
                // ... handle incorrect credentials (e.g., display error message to user)
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
    @FXML
    private void handlesignup(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/addr.fxml"));

        // Create a Scene with the root node
        Scene scene = new Scene(root);

        // Create a new Stage
        Stage stage = new Stage();

        // Set the Scene to the Stage
        stage.setScene(scene);

        // Set the title of the Stage
        stage.setTitle("FXML Example");

        // Show the Stage
        stage.show();
    }
    @FXML
    void toggleButton(ActionEvent event) {
        if(toggleButton.isSelected()){
            ShownPassword.setVisible(true);
            ShownPassword.textProperty().bind(Bindings.concat(passwordfield.getText()));
            toggleButton.setText("Hide");
        }else {
            ShownPassword.setVisible(false);
            toggleButton.setText("Show");
        }
    }
    public void OnForget(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgetPassword.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void opt(MouseEvent event) {

    }
}
