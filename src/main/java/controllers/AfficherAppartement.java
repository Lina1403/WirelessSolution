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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceAppartemment;
import services.ServiceFacture;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AfficherAppartement {

    private AfficherAppartement afficherAppartementController;
    private final ServiceAppartemment serviceAppartemment = new ServiceAppartemment();

    private final ServiceFacture serviceFacture = new ServiceFacture();
    AjouterFacture controler;


    @FXML
    private ListView<Appartement> listView;

    @FXML
    private TextField searchTF;

    private List<Appartement> appartementList;







    private void afficherAppartements() throws SQLException {
        Set<Appartement> appartements = serviceAppartemment.getAll();
        appartementList = new ArrayList<>(appartements); // Initialiser la liste appartementList
        ObservableList<Appartement> observableList = FXCollections.observableList(appartementList);
        listView.setItems(observableList);
        listView.setItems(FXCollections.observableList(appartementList));

        listView.refresh();
    }


    @FXML
    void initialize() {
        try {
            afficherAppartements();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
        }
        listView.refresh();

        // Ajout d'un écouteur de changement de texte pour le champ de recherche
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchAppartement(newValue); // Appel de la méthode de recherche avec le nouveau texte
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void modiferAppartement(ActionEvent actionEvent) {
        Appartement appartementSelectionne = listView.getSelectionModel().getSelectedItem();
        System.out.println("Appartement sélectionné pour modifier : " + appartementSelectionne);
        if (appartementSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAppartement.fxml"));
                Parent root = loader.load();
                System.out.println("FXML file loaded successfully.");
                ModifierAppartement controller = loader.getController();
                System.out.println("Controller initialized.");

                controller.setAppartementSelectionne(appartementSelectionne);
                System.out.println("Data initialized in controller.");

                Stage stage = new Stage();
                stage.setTitle("Liste des Factures");
                stage.setScene(new Scene(root));

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun appartement sélectionné.");
        }
listView.refresh();
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
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
listView.refresh();
    }


    @FXML
    void gererFacture(ActionEvent actionEvent) {
        Appartement appartementSelectionne = listView.getSelectionModel().getSelectedItem();
        System.out.println("Appartement sélectionné pour afficher les factures : " + appartementSelectionne);

        if (appartementSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFacture.fxml"));
                Parent root = loader.load();

                System.out.println("FXML file loaded successfully.");

                AfficherFacture controller = loader.getController();
                System.out.println("Controller initialized.");

                controller.initData(appartementSelectionne);
                System.out.println("Data initialized in controller.");

                Stage stage = new Stage();
                stage.setTitle("Liste des Factures");
                stage.setScene(new Scene(root));

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Aucun appartement sélectionné.");
        }
        actualiser();
    }

    @FXML
    void actualiser() {
        try {
            afficherAppartements(); // Actualiser la liste des factures depuis la base de données
            initialize(); // Réinitialiser l'interface utilisateur
            System.out.println("Base de données et interface actualisées avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'actualisation de la base de données et de l'interface : " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void supprimerAppartement() {
        Appartement appartementSelectionne = listView.getSelectionModel().getSelectedItem();
        if (appartementSelectionne != null) {
            try {
                System.out.println("ID de l'appartement à supprimer : " + appartementSelectionne.getNumAppartement());
                serviceAppartemment.supprimer(appartementSelectionne.getIdAppartement());
                System.out.println("Appartement supprimé avec succès !");

                // Supprimer l'appartement de la liste affichée dans la table
                listView.getItems().remove(appartementSelectionne);

                // Afficher une confirmation à l'utilisateur
                afficherAlerteErreur("Suppression réussie", "L'appartement a été supprimé avec succès.");
            } catch (SQLException e) {
                // Gérer l'exception pour les contraintes de clé étrangère
                afficherAlerteErreur("Erreur de suppression", "Impossible de supprimer l'appartement : des factures sont associées à cet appartement. Veuillez d'abord supprimer toutes les factures associées.");
            } catch (Exception e) {
                // Gérer les autres exceptions
                afficherAlerteErreur("Erreur", "Une erreur s'est produite lors de la suppression de l'appartement : " + e.getMessage());
            }
        } else {
            afficherAlerteErreur("Sélection requise", "Veuillez sélectionner un appartement à supprimer.");
        }
        actualiser();
    }

    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    private void searchAppartement(String searchText) throws SQLException {
        List<Appartement> searchResult = appartementList.stream()
                .filter(appartement -> {
                    String numAppartementString = String.valueOf(appartement.getNumAppartement());
                    return numAppartementString.contains(searchText.toLowerCase()) ||
                            appartement.getNomResident().toLowerCase().contains(searchText.toLowerCase());
                })
                .collect(Collectors.toList());

        listView.setItems(FXCollections.observableArrayList(searchResult));
    }



}