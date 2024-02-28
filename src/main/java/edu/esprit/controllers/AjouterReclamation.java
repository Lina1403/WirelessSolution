package edu.esprit.controllers;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjouterReclamation {
    private final ServiceReclamation rs= new ServiceReclamation( );
    private final ServiceUser su= new ServiceUser();

    @FXML
    private TextField CategorieRecTF;

    @FXML
    private TableColumn<?, ?> CvCat;

    @FXML
    private TableColumn<?, ?> CvDate;

    @FXML
    private TableColumn<?, ?> CvDescri;

    @FXML
    private TableColumn<?, ?> CvStatut;

    @FXML
    private TableView<?> TableViewRec;

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

    public void initialize() throws IOException {
        ShowReclamation();


    }



    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Reclamation r=new Reclamation();
        User userAdd= su.getOneById(2);
        r.setCategorieRec(CategorieRecTF.getText());
        r.setStatutRec("En cours");
        r.setDescriRec(descriRecTF.getText());
        r.setUser(userAdd);
        r.setDateRec(new Date(System.currentTimeMillis()));

        try {
            rs.ajouter(r);
            ShowReclamation(); // Rafraîchir les données de la table
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Reclamation> RecList;
    public void ShowReclamation() throws IOException {
        try {
            RecList = rs.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        User userAdd = su.getOneById(2);
        List<Reclamation> filteredRecList = new ArrayList<>();

        for (Reclamation r : RecList) {
            if (r.getUser().equals(userAdd)) {
                filteredRecList.add(r);
            }
        }
        // CvClient.setCellValueFactory(new PropertyValueFactory<>("user"));

        CvDescri.setCellValueFactory(new PropertyValueFactory<>("descriRec"));
        CvDate.setCellValueFactory(new PropertyValueFactory<>("DateRec"));
        CvCat.setCellValueFactory(new PropertyValueFactory<>("CategorieRec"));
        CvStatut.setCellValueFactory(new PropertyValueFactory<>("StatutRec"));

        if (TableViewRec != null && TableViewRec instanceof TableView) {
            ((TableView<Reclamation>) TableViewRec).setItems(FXCollections.observableArrayList(filteredRecList));
        }


    }


    @FXML
    void modifier(ActionEvent event) throws IOException {
        // Obtenez la réclamation sélectionnée dans la table
        Reclamation r = (Reclamation) TableViewRec.getSelectionModel().getSelectedItem();
        if (r != null) {
            // Mettez à jour les champs de la réclamation
            r.setCategorieRec(CategorieRecTF.getText());
            r.setDescriRec(descriRecTF.getText());

            try {
                // Mettez à jour la réclamation dans la base de données
                rs.modifier(r);
                // Rafraîchir les données de la table
                ShowReclamation();
                TableViewRec.refresh(); // Ajoutez cette ligne
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @FXML
    void supprimer(ActionEvent event) throws IOException, SQLException {
        // Obtenez l'objet Reclamation sélectionné
        Reclamation selectedReclamation = (Reclamation) TableViewRec.getSelectionModel().getSelectedItem();

        // Supprimez cet objet de votre base de données
        rs.supprimer(selectedReclamation.getIdRec());

        // Supprimez cet objet de la vue de la table
        TableViewRec.getItems().remove(selectedReclamation);
    }


}