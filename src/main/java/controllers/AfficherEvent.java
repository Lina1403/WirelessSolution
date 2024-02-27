package controllers;

import entities.Espace;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import services.ServiceEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class AfficherEvent {
    @FXML
    private Button boutonPDF;

    @FXML
    private ListView<Event> listeEvents;

    @FXML
    private Button boutonGerer;
    @FXML
    private Button boutonGererEspace;

    private ObservableList<Event> eventsObservableList;
    private ServiceEvent serviceEvent;

    public AfficherEvent() {
        serviceEvent = new ServiceEvent();
        eventsObservableList = FXCollections.observableArrayList();
    }


    @FXML
    public void initialize() {
        try {
            eventsObservableList.addAll(serviceEvent.getAll());
            listeEvents.setItems(eventsObservableList);
            // Personnalisez la façon dont les éléments sont rendus dans la ListView
            listeEvents.setCellFactory(param -> new ListCell<Event>() {
                @Override
                protected void updateItem(Event item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Définissez le texte à afficher pour chaque élément de la liste
                        setText(item.getTitle() + " - " + item.getEspace().getName());
                    }
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        boutonPDF.setOnAction(event -> {
            ouvrirPDFListeEvents(); // Appel de la méthode pour ouvrir le PDF
        });
        boutonGerer.setOnAction(event -> {
            Event selectedEvent = listeEvents.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                ouvrirDetailsEvent(selectedEvent);
            } else {
                // Afficher un message d'erreur ou une notification si aucun événement n'est sélectionné
            }
        });

        listeEvents.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Event selectedEvent = listeEvents.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    ouvrirDetailsEvent(selectedEvent);
                }
            }
        });
        boutonGererEspace.setOnAction(event -> {
            ouvrirAjouterEspace(); // Méthode pour ouvrir la vue d'ajout d'espace
        });

    }



    @FXML
    private void ouvrirPDFListeEvents() {
        Event selectedEvent = listeEvents.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Charger la police Cairoplay
                PDType0Font font = PDType0Font.load(document, new File("src/main/resources/fonts/CairoPlay-VariableFont_slnt,wght.ttf"));

                contentStream.setNonStrokingColor(Color.BLUE); // Définir la couleur de remplissage en bleu

                float margin = 50; // Marge
                float yStart = page.getMediaBox().getHeight() - margin;
                float yPosition = yStart;
                float lineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 10;

                // Dessiner une bordure autour de la page
                contentStream.addRect(margin, margin, page.getMediaBox().getWidth() - 2 * margin, page.getMediaBox().getHeight() - 2 * margin);
                contentStream.stroke();

                // Écrire le titre en gras et plus grand
                float titleFontSize = 20;
                contentStream.setFont(font, titleFontSize);
                writeText(contentStream, "Détail de l'événement", margin, yPosition, font);

                yPosition -= titleFontSize + 2 * lineHeight; // Déplacer la position Y pour le texte suivant

                // Écrire le contenu du PDF avec une taille de police plus petite
                float normalFontSize = 12;
                contentStream.setFont(font, normalFontSize);
                writeText(contentStream, "Titre : " + selectedEvent.getTitle(), margin, yPosition, font);
                yPosition -= lineHeight;
                writeText(contentStream, "Description : " + selectedEvent.getDescription(), margin, yPosition, font);
                yPosition -= lineHeight * 2; // Augmenter l'espace entre les lignes de description
                writeText(contentStream, "Date : " + selectedEvent.getDate(), margin, yPosition, font);
                yPosition -= lineHeight;
                writeText(contentStream, "Lieu : " + selectedEvent.getEspace().getName(), margin, yPosition, font);

                contentStream.close();

                File file = new File(selectedEvent.getTitle() + ".pdf");
                document.save(file);
                document.close();

                // Ouvrir le fichier PDF généré
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeText(PDPageContentStream contentStream, String text, float x, float y, PDType0Font font) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }


    private void writeText(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }



    @FXML
    private void ouvrirAjouterEspace() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEspace.fxml"));
            Parent root = loader.load();
            AjouterEspace controller = loader.getController();
            controller.initData(new Espace());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter un espace");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ouvrirDetailsEvent(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEvent.fxml"));
            Parent root = loader.load();
            DetailsEvent controller = loader.getController();
            controller.initData(event);
            controller.setAfficherEvent(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Détails de l'événement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshList() {
        try {
            eventsObservableList.clear();
            eventsObservableList.addAll(serviceEvent.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
