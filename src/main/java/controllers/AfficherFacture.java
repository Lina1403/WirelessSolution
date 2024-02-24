package controllers;

import entities.Appartement;
import entities.Facture;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceFacture;
import javafx.scene.control.cell.PropertyValueFactory; // Add this import

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AfficherFacture  {

    @FXML
    private TableColumn<Facture, Integer> numFactureColumn;

    @FXML
    private TableColumn<Facture, Date> dateColumn;

    @FXML
    private TableColumn<Facture, Facture.Type> typeColumn;

    @FXML
    private TableColumn<Facture, Float> montantColumn;

    @FXML
    private TableColumn<Facture, String> descriptionColumn;

    @FXML
    private TableView<Facture> tableFactures;

    private final ServiceFacture serviceFacture = new ServiceFacture();
    @FXML
    private TextField searchNumField;
    AfficherAppartement afficherAppartement ;



    @FXML
    private Button supprimerButton;

    @FXML
    private Button modifierButton;
    private Appartement appartementSelectionne;

    public void initData(Appartement appartement) throws SQLException, IOException {
        this.appartementSelectionne = appartement;
        System.out.println(appartementSelectionne);
        try {
            afficherFactures(appartementSelectionne);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
        initialize(); // Déplacer l'appel à initialize ici
    }


    void afficherFactures(Appartement appartement) throws SQLException {
        Set<Facture> factures = serviceFacture.getAllForAppartement(appartementSelectionne);
        System.out.println(factures);

    }

    @FXML
    void initialize() throws IOException, SQLException {
        if (appartementSelectionne != null) {
            System.out.println("Appartement sélectionné : " + appartementSelectionne); // Ajoutez ce message

            afficherFactures(appartementSelectionne);
        } else {
            System.out.println("Appartement is null.");
            System.out.println(afficherAppartement);
        }
        numFactureColumn.setCellValueFactory(new PropertyValueFactory<>("numFacture"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionFacture"));


    }
    private void actualiserTableFactures() {
        try {
            Set<Facture> factures = serviceFacture.getAll();
            ObservableList<Facture> observableList = FXCollections.observableArrayList(new ArrayList<>(factures));
            tableFactures.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

  /*

    @FXML
    public void ajouterFacture(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFacture.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Ajouter Facture");
            stage.setScene(new Scene(root));

            // Show the new stage
            stage.show();

            // Close the current stage (optional)
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    void supprimerFacture() {
        Facture selectedFacture = tableFactures.getSelectionModel().getSelectedItem();

        if (selectedFacture != null) {
            try {
                serviceFacture.supprimer(selectedFacture.getIdFacture());
                System.out.println("Facture supprimée avec succès !");
                tableFactures.getItems().remove(selectedFacture);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la suppression de la facture : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Veuillez sélectionner une facture à supprimer.");
        }
    }
   /* @FXML
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
   void rechercherFactures() {
       try {
           int numFacture = 0; // Valeur par défaut
           if (!searchNumField.getText().isEmpty()) {
               numFacture = Integer.parseInt(searchNumField.getText());
           }

           // Supprimez la partie liée à la recherche par date

           Set<Facture> factures = serviceFacture.rechercherFactures(numFacture);
           ObservableList<Facture> observableList = FXCollections.observableArrayList(new ArrayList<>(factures));
           tableFactures.setItems(observableList);
       } catch (NumberFormatException e) {
           System.out.println("Veuillez saisir un numéro de facture valide.");
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
    @FXML
    void modifierFacture() {
        Facture selectedFacture =(Facture) tableFactures.getSelectionModel().getSelectedItem();

        if (selectedFacture != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierFacture.fxml"));
                Parent root = loader.load();
                ModifierFacture controller = loader.getController();
                controller.initData(selectedFacture);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

                actualiserTableFactures();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Veuillez sélectionner une facture à modifier.");
        }
    }
           */

