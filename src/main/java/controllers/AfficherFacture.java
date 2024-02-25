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
    private final ServiceFacture serviceFacture = new ServiceFacture();
    @FXML
    private TextField searchNumField;



    private Appartement appartementSelectionne;

    @FXML
    private ListView<Facture> listViewFacture;

    public void initData(Appartement appartement) throws SQLException {
        if (appartement == null) {
            System.out.println("L'objet appartementSelectionne est null !");
        }
        this.appartementSelectionne = appartement;
        System.out.println("Appartement sélectionné : " + appartementSelectionne);
        initialize();
        supprimerFacture();
    }

    void afficherFactures() throws SQLException {
        // Obtenez les factures pour l'appartement sélectionné
        if (appartementSelectionne == null) {
            System.out.println("L'appartement sélectionné est null !");
            return;
        }
        Set<Facture> factures = serviceFacture.getAllForAppartement(appartementSelectionne);
        ObservableList<Facture> observableList = FXCollections.observableArrayList(factures);
        listViewFacture.setItems(observableList);
    }

    @FXML
    void initialize() {
        try {
            afficherFactures(); // Actualiser la table après chaque modification
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer SQLException de manière appropriée
        }
    }

    @FXML
    void modifierFacture() {
       Facture  selectedFacture = listViewFacture.getSelectionModel().getSelectedItem();
        System.out.println(selectedFacture);


        if (selectedFacture != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierFacture.fxml"));
                Parent root = loader.load();
                ModifierFacture controller = loader.getController();
                controller.setFactureSelectionne(selectedFacture);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
                actualiser();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Veuillez sélectionner une facture à modifier.");
        }
    }
    @FXML
    public void ajouterFacture(ActionEvent actionEvent) {
        if (appartementSelectionne != null) {
            System.out.println("Appartement selected: " + appartementSelectionne);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFacture.fxml"));
                Parent root = loader.load();

                // Get the controller and pass the selected Appartement
                AjouterFacture controller = loader.getController();
                controller.setAppartementSelectionne(appartementSelectionne);
                // Create a new stage
                Stage stage = new Stage();
                stage.setTitle("Ajouter Facture");
                stage.setScene(new Scene(root));

                // Show the new stage
                System.out.println("Showing new stage...");
                stage.show();
                System.out.println("New stage should be visible now.");
            } catch (IOException e) {
                System.out.println("IOException occurred:");
                e.printStackTrace();
            }
        } else {
            System.out.println("No Appartement selected.");
        }
        listViewFacture.refresh();
    }


    @FXML
    void supprimerFacture() {
        Facture factureSelectionnee = listViewFacture.getSelectionModel().getSelectedItem();
        if (factureSelectionnee != null) {
            try {
                System.out.println("Facture sélectionnée : " + factureSelectionnee); // Vérifiez la facture sélectionnée
                System.out.println(factureSelectionnee.getIdFacture());

                int idFacture = factureSelectionnee.getIdFacture();
                System.out.println("ID de la facture à supprimer : " + idFacture); // Vérifiez l'ID de la facture

                // Ajoutez d'autres instructions de débogage au besoin...

                serviceFacture.supprimer(idFacture);
                System.out.println("Facture supprimée avec succès !");

                listViewFacture.getItems().remove(factureSelectionnee);

                afficherAlerteErreur("Suppression réussie", "La facture a été supprimée avec succès.");
            } catch (SQLException e) {
                afficherAlerteErreur("Erreur de suppression", "Impossible de supprimer la facture : des contraintes de clé étrangère sont violées.");
            } catch (Exception e) {
                afficherAlerteErreur("Erreur", "Une erreur s'est produite lors de la suppression de la facture : " + e.getMessage());
            }
        } else {
            afficherAlerteErreur("Sélection requise", "Veuillez sélectionner une facture à supprimer.");
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


    @FXML
            void actualiser() {
                try {
                    afficherFactures(); // Actualiser la liste des factures depuis la base de données
                    initialize(); // Réinitialiser l'interface utilisateur
                } catch (SQLException e) {
                    e.printStackTrace();
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
                    listViewFacture.setItems(observableList);
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez saisir un numéro de facture valide.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }




}






