package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Appartement;
import entities.Facture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import services.ServiceFacture;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Set;

public class AfficherFactureResident {
    private Facture factureSelectionnee ;
    private Appartement appartementSelectionne;
    @FXML
    private Button boutonPDF;
    @FXML
    private ListView<Facture> listViewFacture;
    private ServiceFacture serviceFacture = new ServiceFacture();
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
        listViewFacture.setItems(observableList); // Correction 3

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
        listViewFacture.refresh();
    }
    @FXML
    void genererPDF() {
        Facture factureSelectionnee = listViewFacture.getSelectionModel().getSelectedItem();

        if (factureSelectionnee != null) {
            try {
                Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                PdfWriter.getInstance(document, new FileOutputStream("invoice.pdf"));
                document.open();

                // Ajouter l'image de fond
                Image background = Image.getInstance("C:\\Users\\Ali\\IdeaProjects\\GestionEnergie\\src\\main\\resources\\src\\Facturepdf.png");
                background.setAbsolutePosition(0, 0);
                background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
                background.setBorder(Image.BOX);
                background.setBorderWidth(0);
                background.setBorderColor(new GrayColor(0));
                document.add(background);

                // Ajout du titre
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
                Paragraph title = new Paragraph("Facture", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));

                document.add(title);
                document.add(new Paragraph(" "));

                // Ajouter les détails de la facture
                Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK);
                document.add(new Paragraph("Numéro de facture : " + factureSelectionnee.getNumFacture(), contentFont));
                document.add(new Paragraph("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(factureSelectionnee.getDate()), contentFont));
                document.add(new Paragraph("Type : " + factureSelectionnee.getType().toString(), contentFont));
                document.add(new Paragraph("Montant : " + factureSelectionnee.getMontant(), contentFont));
                document.add(new Paragraph("Description : " + factureSelectionnee.getDescriptionFacture(), contentFont));

                // Ajouter la signature
                Paragraph signature = new Paragraph("Signature : _______________________", contentFont);
                signature.setAlignment(Element.ALIGN_RIGHT);
                signature.setSpacingBefore(20f);
                document.add(signature);

                document.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF généré");
                alert.setContentText("Les détails de la facture ont été enregistrés dans invoice.pdf");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                afficherAlerteErreur("Erreur lors de la génération du PDF", "Une erreur s'est produite lors de la génération du PDF : " + e.getMessage()); // Correction 4
            }
        } else {
            System.out.println("Aucune facture sélectionnée.");
            afficherAlerteErreur("Sélection requise", "Veuillez sélectionner une facture pour générer le PDF.");
        }
    }
    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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


}
