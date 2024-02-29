package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjouterReclamation {
    private final ServiceReclamation rs = new ServiceReclamation();
    private final ServiceUser su = new ServiceUser();
    private Stage stage; // Reference to the stage

    @FXML
    private TextField CategorieRecTF;

    @FXML
    private TableColumn<Reclamation, String> CvCat;

    @FXML
    private TableColumn<Reclamation, Date> CvDate;

    @FXML
    private TableColumn<Reclamation, String> CvDescri;

    @FXML
    private TableColumn<Reclamation, String> CvStatut;

    @FXML
    private TableView<Reclamation> TableViewRec;

    @FXML
    private Button btn_add;

    @FXML
    private Label cate;

    @FXML
    private Label desc;

    @FXML
    private TextField descriRecTF;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    public void initialize() {
        try {
            ShowReclamation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set the stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Reclamation r = new Reclamation();
        r.setCategorieRec(CategorieRecTF.getText());
        r.setStatutRec("En cours");
        r.setDescriRec(descriRecTF.getText());
        r.setUser(su.getOneById(2)); // Assuming the user ID is fixed or obtained from somewhere
        r.setDateRec(new Date(System.currentTimeMillis()));

        try {
            rs.ajouter(r);
            ShowReclamation(); // Refresh table data
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ShowReclamation() throws IOException {
        try {
            List<Reclamation> RecList = rs.getAll();
            User userAdd = su.getOneById(2); // Assuming the user ID is fixed or obtained from somewhere
            List<Reclamation> filteredRecList = new ArrayList<>();

            for (Reclamation r : RecList) {
                if (r.getUser().equals(userAdd)) {
                    filteredRecList.add(r);
                }
            }

            CvDescri.setCellValueFactory(new PropertyValueFactory<>("descriRec"));
            CvDate.setCellValueFactory(new PropertyValueFactory<>("DateRec"));
            CvCat.setCellValueFactory(new PropertyValueFactory<>("CategorieRec"));
            CvStatut.setCellValueFactory(new PropertyValueFactory<>("StatutRec"));

            TableViewRec.setItems(FXCollections.observableArrayList(filteredRecList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        Reclamation selectedReclamation = TableViewRec.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            selectedReclamation.setCategorieRec(CategorieRecTF.getText());
            selectedReclamation.setDescriRec(descriRecTF.getText());

            try {
                rs.modifier(selectedReclamation);
                ShowReclamation();
                TableViewRec.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void supprimer(ActionEvent event) throws IOException, SQLException {
        Reclamation selectedReclamation = TableViewRec.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            rs.supprimer(selectedReclamation.getIdRec());
            TableViewRec.getItems().remove(selectedReclamation);
        }
    }

    @FXML
    void goToAjouterReponse(ActionEvent event) throws IOException {
        Reclamation selectedReclamation = TableViewRec.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReponse.fxml"));
            Parent root = loader.load();
            AjouterReponse ajouterReponseController = loader.getController();
            ajouterReponseController.setReclamation(selectedReclamation);

            // Set the stage and show the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Réponse");
            stage.show();
        } else {
            System.out.println("No reclamation selected.");
        }
    }
}