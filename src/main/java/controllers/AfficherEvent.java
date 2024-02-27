package controllers;

import entities.Espace;
import entities.Event;
import services.ServiceEvent;
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
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Desktop;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
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
            ouvrirPDFListeEvents();
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
                PDType0Font font = PDType0Font.load(document, getClass().getResourceAsStream("/fonts/CairoPlay-VariableFont_slnt,wght.ttf"));

                float margin = 0;

                // Charger l'image de bordure
                PDImageXObject borderImage = PDImageXObject.createFromFile("src/main/resources/image/bordure.png", document);

                // Dessiner l'image de bordure sur la page
                contentStream.drawImage(borderImage, margin, margin, page.getMediaBox().getWidth() - 2 * margin, page.getMediaBox().getHeight() - 2 * margin);

                // Charger l'image du logo
                PDImageXObject logoImage = PDImageXObject.createFromFile("src/main/resources/image/logo.png", document);
                float logoWidth = 125; // Largeur du logo réduite de 10 unités
                float logoHeight = logoWidth * logoImage.getHeight() / logoImage.getWidth(); // Calculer la hauteur du logo en préservant les proportions

                // Dessiner le logo légèrement à gauche et un peu plus haut
                contentStream.drawImage(logoImage, page.getMediaBox().getWidth() - margin - logoWidth - 10, page.getMediaBox().getHeight() - margin - logoHeight - 15, logoWidth, logoHeight);

                float titleFontSize = 25;
                contentStream.setNonStrokingColor(Color.BLACK); // Couleur du texte noir
                contentStream.setFont(font, titleFontSize);

                float titleX = (page.getMediaBox().getWidth() - font.getStringWidth("Détails de l'événement") / 1000 * titleFontSize) / 2+40; // Centrer le texte sur l'axe des x
                float titleY = page.getMediaBox().getHeight() - 3 * (margin+30);
                contentStream.setNonStrokingColor(new Color(0, 0, 139)); // Bleu foncé pour le lieu de l'événement

                writeText(contentStream, "Détails de l'événement", titleX , titleY , font);


                float normalFontSize = 14;
                contentStream.setFont(font, normalFontSize);

                float infoX = (margin+30) * 3;
                float infoY = titleY - normalFontSize * 6; // Décaler un peu plus vers le haut
                float infoSpacing = normalFontSize * 2; // Ajouter un peu d'espace entre les lignes

                // Définir la couleur du texte pour les informations de l'événement
                contentStream.setNonStrokingColor(Color.BLACK); // Noir pour les informations de l'événement

                writeText(contentStream,"Titre : " + selectedEvent.getTitle(),infoX,infoY,font);
                infoY -= infoSpacing;
                writeText(contentStream,"Description : " + selectedEvent.getDescription(),infoX,infoY,font);
                infoY -= infoSpacing;
                writeText(contentStream,"Date : " + selectedEvent.getDate(),infoX,infoY,font);
                infoY -= infoSpacing;

                writeText(contentStream,"Lieu : " + selectedEvent.getEspace().getName(),infoX,infoY,font);

                contentStream.close();

                File file = new File(selectedEvent.getTitle() + ".pdf");
                document.save(file);
                document.close();

                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void writeText(PDPageContentStream contentStream, String text, float x, float y, PDType0Font font) throws IOException {
        contentStream.beginText();
        // Ajouter un décalage vers la droite (par exemple, 10 unités)
        contentStream.newLineAtOffset(x + 10, y);
        contentStream.setFont(font, 14);
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
