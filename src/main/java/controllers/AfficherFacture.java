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
        Facture selectedFacture = listViewFacture.getSelectionModel().getSelectedItem();

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
        actualiser();
    }


            @FXML
            void supprimerFacture () {
                Facture selectedFacture = listViewFacture.getSelectionModel().getSelectedItem();

                if (selectedFacture != null) {
                    try {
                        System.out.println("ID de la Facture à supprimer : " + selectedFacture.getNumFacture());
                        serviceFacture.supprimer(selectedFacture.getIdFacture());
                        System.out.println("Facture supprimée avec succès !");
                        listViewFacture.getItems().remove(selectedFacture);
                        afficherAlerteErreur("Suppression réussie", "La Facture a été supprimé avec succès.");

                    } catch (SQLException e) {
                        // Gérer l'exception pour les contraintes de clé étrangère
                        afficherAlerteErreur("Erreur de suppression", "Impossible de supprimer la facture : des factures sont associées à cet appartement. Veuillez d'abord supprimer toutes les factures associées.");
                    } catch (Exception e) {
                        // Gérer les autres exceptions
                        afficherAlerteErreur("Erreur", "Une erreur s'est produite lors de la suppression de la facture : " + e.getMessage());
                    }
                } else {
                    afficherAlerteErreur("Sélection requise", "Veuillez sélectionner un facture à supprimer.");
                }
                actualiser();
            }


            @FXML
            void actualiser () {
                try {
                    afficherFactures(); // Actualiser la liste des factures depuis la base de données
                    initialize(); // Réinitialiser l'interface utilisateur
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            @FXML
            void rechercherFactures () {
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



    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}






