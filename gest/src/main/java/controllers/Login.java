package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import entities.Role;
import entities.User;
import service.ServiceUser;
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
import utils.Datasource;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void validatelogin()
    {
        Connection cnx = Datasource.getInstance().getCnx();
String verifyLogin="SELECT count(1) FROM user WHERE mail='" + emailfield.getText()+"'AND password='"+ passwordfield.getText()+"'";
try
{
    Statement statement=cnx.createStatement();
    ResultSet queryResult = statement.executeQuery(verifyLogin);
    while (queryResult.next())
    {
        if (queryResult.getInt(1)==1)
        {
    loginmessagelabel.setText("congrats");
        }
        else
        {
loginmessagelabel.setText("retry");
        }
    }
}
catch (Exception e)
{

}
    }
}
