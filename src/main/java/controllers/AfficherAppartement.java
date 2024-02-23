package controllers;

import entities.Appartement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceAppartemment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AfficherAppartement {

    @FXML
    private TableColumn<Appartement, Integer> numAppartementColumn;

    @FXML
    private TableColumn<Appartement, String> nomResidentColumn;

    @FXML
    private TableColumn<Appartement, String> tailleColumn;

    @FXML
    private TableColumn<Appartement, Integer> nbrEtageColumn;
    @FXML
    private TextField searchNumField;

    @FXML
    private TextField searchNomResidentField;

    @FXML
    private TableColumn<Appartement, Appartement.statutAppartement> statutAppartementColumn;

    @FXML
    private TableView<Appartement> tableAppartements;

    private final ServiceAppartemment serviceAppartemment = new ServiceAppartemment();


    @FXML
    void supprimerAppartement() {
        Appartement appartementSelectionne = tableAppartements.getSelectionModel().getSelectedItem();
        if (appartementSelectionne != null) {
            try {
                System.out.println("ID de l'appartement à supprimer : " + appartementSelectionne.getNumAppartement());
                serviceAppartemment.supprimer(appartementSelectionne.getIdAppartement());
                System.out.println("Appartement supprimé avec succès !");

                // Supprimer l'appartement de la liste affichée dans la table
                tableAppartements.getItems().remove(appartementSelectionne);
            } catch (SQLException e) {
                e.printStackTrace(); // Handle SQLException appropriately
            }
        } else {
            System.out.println("Veuillez sélectionner un appartement à supprimer.");
        }
    }




    private void afficherAppartements() throws SQLException {
        Set<Appartement> appartements = serviceAppartemment.getAll();
        List<Appartement> appartementList = new ArrayList<>(appartements);
        ObservableList<Appartement> observableList = FXCollections.observableList(appartementList);
        tableAppartements.setItems(observableList);
    }
    @FXML
    void initialize() {
        try {
            afficherAppartements();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
        }

        // Assurez-vous que les colonnes sont correctement initialisées
        numAppartementColumn.setCellValueFactory(new PropertyValueFactory<>("numAppartement"));
        nomResidentColumn.setCellValueFactory(new PropertyValueFactory<>("nomResident"));
        tailleColumn.setCellValueFactory(new PropertyValueFactory<>("taille"));
        nbrEtageColumn.setCellValueFactory(new PropertyValueFactory<>("nbrEtage"));
        statutAppartementColumn.setCellValueFactory(new PropertyValueFactory<>("statutAppartement"));
    }
    @FXML
    void actualiserBaseDeDonnees() {
        try {
            afficherAppartements(); // Actualiser la table après chaque modification
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
        }
    }
    @FXML
    public void ajouterAppartement(ActionEvent actionEvent) {


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAppartement.fxml"));
                Parent root = loader.load();

                // Create a new stage
                Stage stage = new Stage();
                stage.setTitle("Ajouter Appartement");
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
    void rechercherAppartements() {
        try {
            int numAppartement = 0; // Valeur par défaut
            if (!searchNumField.getText().isEmpty()) {
                numAppartement = Integer.parseInt(searchNumField.getText());
            }

            String nomResident = searchNomResidentField.getText();

            Set<Appartement> appartements = serviceAppartemment.rechercherAppartements(numAppartement, nomResident);
            ObservableList<Appartement> observableList = FXCollections.observableArrayList(new ArrayList<>(appartements));
            tableAppartements.setItems(observableList);
        } catch (NumberFormatException e) {
            System.out.println("Veuillez saisir un numéro d'appartement valide.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gererFacture() {
        Appartement appartementSelectionne = tableAppartements.getSelectionModel().getSelectedItem();
        if (appartementSelectionne != null) {
            // Vous pouvez ouvrir une nouvelle fenêtre pour afficher les factures associées à cet appartement
            // Vous pouvez également utiliser un dialogue modal ou une autre méthode de votre choix pour afficher les factures.
            // Par exemple :
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFacture.fxml"));
            Parent root;
            try {
                root = loader.load();
                AfficherFacture controller = loader.getController();
                controller.initData(appartementSelectionne);
                Stage stage = new Stage();
                stage.setTitle("Liste des Factures");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception de manière appropriée
            }
        } else {
            System.out.println("Veuillez sélectionner un appartement pour gérer les factures.");
        }
    }

}

