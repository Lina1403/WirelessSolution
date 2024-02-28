package controllers;

import entities.Appartement;
import entities.Facture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class AfficherFacture {
    private final ServiceFacture serviceFacture = new ServiceFacture();
    @FXML
    private TextField searchNumField;
    private Facture factureSelectionnee ;
    private Appartement appartementSelectionne;
    @FXML
    private ListView<Facture> listViewFacture;
    @FXML
    private Button boutonPDF;


    public void initData(Appartement appartement) throws SQLException {
        if (appartement == null) {
            System.out.println("L'objet appartementSelectionne est null !");
        }
        this.appartementSelectionne = appartement;
        System.out.println("Appartement sélectionné : " + appartementSelectionne);
        initialize();
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
            // Add a ChangeListener to the ListView's selection model
            listViewFacture.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Facture>() {
                @Override
                public void changed(ObservableValue<? extends Facture> observable, Facture oldValue, Facture newValue) {
                    if (newValue != null) {
                        System.out.println("Facture selected: " + newValue);
                    }
                }
            });
            boutonPDF.setOnAction(event -> {
                genererPDF();
            });
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer SQLException de manière appropriée
        }
    }
    @FXML
    void genererPDF() {
        Facture factureSelectionnee = listViewFacture.getSelectionModel().getSelectedItem();

        if (factureSelectionnee != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("invoice.pdf"));
                document.open();

                Font font = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.CYAN);
                Chunk chunk = new Chunk("Invoice Details", font);
                Paragraph p = new Paragraph(chunk);
                p.setAlignment(Element.ALIGN_CENTER);
                document.add(p);

                document.add(new Paragraph("\n\n"));

                // Display invoice details
                document.add(new Paragraph("Invoice Number: " + factureSelectionnee.getNumFacture()));
                document.add(new Paragraph("Date: " + new SimpleDateFormat("dd-MM-yyyy").format(factureSelectionnee.getDate())));
                document.add(new Paragraph("Type: " + factureSelectionnee.getType()));
                document.add(new Paragraph("Montant: " + factureSelectionnee.getMontant()));
                document.add(new Paragraph("Description: " + factureSelectionnee.getDescriptionFacture()));

                document.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF Generated");
                alert.setContentText("Invoice details have been saved to invoice.pdf");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucune facture sélectionnée.");
            afficherAlerteErreur("Sélection requise", "Veuillez sélectionner une facture pour générer le PDF.");
        }
    }

    @FXML
    void retournerPagePrecedente(ActionEvent actionEvent) {
        // Récupérer la source de l'événement
        Node source = (Node) actionEvent.getSource();
        // Récupérer la scène de la source
        Scene scene = source.getScene();
        // Récupérer la fenêtre parente de la scène
        Stage stage = (Stage) scene.getWindow();
        // Fermer la fenêtre parente pour revenir à la page précédente
        stage.close();
    }



    @FXML
    void supprimerFacture() {
        factureSelectionnee = listViewFacture.getSelectionModel().getSelectedItem();
        if (factureSelectionnee != null) {
            try {
                System.out.println("Facture sélectionnée : " + factureSelectionnee); // Check the selected invoice
                System.out.println("ID de la facture sélectionnée : " + factureSelectionnee.getIdFacture());

                int idFacture = factureSelectionnee.getIdFacture();
                System.out.println("ID de la facture à supprimer : " + idFacture); // Check the ID of the invoice
                // Add more debug statements as needed...

                serviceFacture.supprimer(idFacture);
                System.out.println("Facture supprimée avec succès !");

                listViewFacture.getItems().remove(factureSelectionnee);

                afficherAlerteErreur("Suppression réussie", "La facture a été supprimée avec succès.");
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
                afficherAlerteErreur("Erreur de suppression", "Impossible de supprimer la facture : des contraintes de clé étrangère sont violées.");
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
                afficherAlerteErreur("Erreur", "Une erreur s'est produite lors de la suppression de la facture : " + e.getMessage());
            }
        } else {
            System.out.println("Aucune facture sélectionnée.");
            afficherAlerteErreur("Sélection requise", "Veuillez sélectionner une facture à supprimer.");
        }
        listViewFacture.refresh();
    }

    @FXML
    void modifierFacture(ActionEvent actionEvent) {
        Facture factureSelectionne = listViewFacture.getSelectionModel().getSelectedItem();
        System.out.println("Facture sélectionnée pour modification : " + factureSelectionne);
        if (factureSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierFacture.fxml"));
                Parent root = loader.load();
                System.out.println("FXML file loaded successfully.");
                ModifierFacture controller = loader.getController();
                System.out.println("Controller initialized.");

                controller.setFactureSelectionne(factureSelectionne);
                System.out.println("Data initialized in controller.");

                Stage stage = new Stage();
                stage.setTitle("Liste des Factures");
                stage.setScene(new Scene(root));

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listViewFacture.refresh();
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
        listViewFacture.refresh();;
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