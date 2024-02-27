package controllers;

import entities.Espace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.IService;
import services.ServiceEspace;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class AfficherEspace {

    @FXML
    private ListView<Espace> listEspace;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtCapacite;

    @FXML
    private TextField txtDescription;

    @FXML
    private ComboBox<String> comboEtat;

    @FXML
    private Label lblTitleError;

    @FXML
    private Label lblDateError;

    @FXML
    private Label lblNbrPersonneError;

    @FXML
    private Label lblDescriptionError;
    @FXML
    private Button boutonPDF;

    private final IService<Espace> serviceEspace = new ServiceEspace();

    @FXML

    void initialize() {
        try {
            boutonPDF.setOnAction(event -> {
                genererPDF();
            });
            chargerListeEspaces();

            // Configurer la façon dont les éléments sont rendus dans la ListView
            listEspace.setCellFactory(param -> new ListCell<Espace>() {
                @Override
                protected void updateItem(Espace espace, boolean empty) {
                    super.updateItem(espace, empty);
                    if (empty || espace == null) {
                        setText(null);
                    } else {
                        setText(espace.getName() + " - " + espace.getEtat());
                    }
                }
            });

            // Ajouter un écouteur d'événements pour la sélection dans la liste
            listEspace.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Remplir le formulaire avec les valeurs de l'espace sélectionné
                    remplirFormulaire(newValue);
                }
            });
        } catch (SQLException e) {
            afficherAlerteErreur("Erreur lors du chargement des espaces : " + e.getMessage());
        }
    }


    private void remplirFormulaire(Espace espace) {
        txtNom.setText(espace.getName());
        comboEtat.setValue(espace.getEtat().toString());
        txtCapacite.setText(String.valueOf(espace.getCapacite()));
        txtDescription.setText(espace.getDescription());
    }

    @FXML
    void modifier() {
        Espace espace = listEspace.getSelectionModel().getSelectedItem();
        if (espace != null) {
            try {
                // Contrôle de saisie pour le nom
                String nom = txtNom.getText().trim();
                if (nom.isEmpty()) {
                    throw new IllegalArgumentException("Le nom de l'espace ne peut pas être vide.");
                }
                if (!nom.matches("[a-zA-Z ]+")) {
                    throw new IllegalArgumentException("Le nom doit contenir uniquement des lettres et des espaces.");
                }

                // Contrôle de saisie pour la capacité
                int capacite = Integer.parseInt(txtCapacite.getText().trim());
                if (capacite <= 0 || capacite > 50) {
                    throw new IllegalArgumentException("La capacité de l'espace doit être un entier compris entre 1 et 50.");
                }

                // Contrôle de saisie pour la description
                String description = txtDescription.getText().trim();
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("La description ne peut pas être vide.");
                }

                // Mettre à jour les propriétés de l'espace avec les nouvelles valeurs
                espace.setName(nom);
                espace.setEtat(Espace.Etat.valueOf(comboEtat.getValue()));
                espace.setCapacite(capacite);
                espace.setDescription(description);

                // Appeler la méthode pour mettre à jour l'espace dans la base de données
                serviceEspace.modifier(espace);
                listEspace.refresh(); // Rafraîchir l'affichage de la liste

                afficherConfirmation("Espace modifié avec succès !");
            } catch (NumberFormatException e) {
                afficherAlerteErreur("Erreur de format", "Veuillez saisir un nombre valide pour la capacité.");
            } catch (IllegalArgumentException e) {
                afficherAlerteErreur("Erreur de saisie", e.getMessage());
            } catch (SQLException e) {
                afficherAlerteErreur("Erreur SQL", "Erreur lors de la modification de l'espace : " + e.getMessage());
            } catch (Exception e) {
                afficherAlerteErreur("Erreur", e.getMessage());
            }
        } else {
            afficherAlerteErreur("Erreur", "Veuillez sélectionner un espace à modifier.");
        }
    }

    @FXML
    void supprimer() {
        Espace espace = listEspace.getSelectionModel().getSelectedItem();
        if (espace != null) {
            if (afficherConfirmation("Êtes-vous sûr de vouloir supprimer cet espace ?")) {
                try {
                    serviceEspace.supprimer(espace.getIdEspace());
                    listEspace.getItems().remove(espace);
                } catch (SQLException e) {
                    afficherAlerteErreur("Erreur lors de la suppression de l'espace : " + e.getMessage());
                }
            }
        } else {
            afficherAlerteErreur("Veuillez sélectionner un espace à supprimer.");
        }
    }

    @FXML
    void gererEvenements() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvent.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Gérer les événements");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterEspace() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEspace.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter un espace");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void chargerListeEspaces() throws SQLException {
        Set<Espace> espaces = serviceEspace.getAll();
        ObservableList<Espace> espaceList = FXCollections.observableArrayList(espaces);

        // Afficher la liste dans la ListView
        listEspace.setItems(espaceList);
    }
    @FXML
    private void genererPDF() {
        Espace selectedEspace = listEspace.getSelectionModel().getSelectedItem();
        if (selectedEspace != null) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Charger la police Cairoplay
                PDType0Font font = PDType0Font.load(document, getClass().getResourceAsStream("/fonts/CairoPlay-VariableFont_slnt,wght.ttf"));

                float margin = 30;

                // Charger l'image de bordure
                PDImageXObject borderImage = PDImageXObject.createFromFile("src/main/resources/bordure.png", document);

                // Dessiner l'image de bordure sur la page
                contentStream.drawImage(borderImage, margin, margin, page.getMediaBox().getWidth() - 2 * margin, page.getMediaBox().getHeight() - 2 * margin);
                // Charger l'image du logo
                PDImageXObject logoImage = PDImageXObject.createFromFile("src/main/resources/logo.png", document);
                float logoWidth = 125; // Largeur du logo réduite de 10 unités
                float logoHeight = logoWidth * logoImage.getHeight() / logoImage.getWidth(); // Calculer la hauteur du logo en préservant les proportions

                // Dessiner le logo légèrement à gauche et un peu plus haut
                contentStream.drawImage(logoImage, page.getMediaBox().getWidth() - margin - logoWidth - 10, page.getMediaBox().getHeight() - margin - logoHeight - 15, logoWidth, logoHeight);

                // Dessiner le titre
                float titleFontSize = 25;
                contentStream.setNonStrokingColor(Color.BLACK); // Couleur du texte noir
                contentStream.setFont(font, titleFontSize);
                float titleX = (page.getMediaBox().getWidth() - font.getStringWidth("Détails de l'espace") / 1000 * titleFontSize) / 2 + 40; // Centrer le texte sur l'axe des x
                float titleY = page.getMediaBox().getHeight() - 3 * margin;
                contentStream.setNonStrokingColor(new Color(0, 0, 139)); // Bleu foncé pour le titre
                writeText(contentStream, "Détails de l'espace", titleX, titleY, font);

                float normalFontSize = 14;
                contentStream.setFont(font, normalFontSize);

                // Dessiner les informations de l'espace
                float infoX = margin * 3;
                float infoY = titleY - normalFontSize * 6; // Décaler un peu plus vers le haut
                float infoSpacing = normalFontSize * 2; // Ajouter un peu d'espace entre les lignes
                contentStream.setNonStrokingColor(Color.BLACK); // Noir pour les informations de l'espace
                writeText(contentStream, "Nom : " + selectedEspace.getName(), infoX, infoY, font);
                infoY -= infoSpacing;
                writeText(contentStream, "État : " + selectedEspace.getEtat().toString(), infoX, infoY, font);
                infoY -= infoSpacing;
                writeText(contentStream, "Capacité : " + selectedEspace.getCapacite(), infoX, infoY, font);
                infoY -= infoSpacing;
                writeText(contentStream, "Description : " + selectedEspace.getDescription(), infoX, infoY, font);

                contentStream.close();

                File file = new File(selectedEspace.getName() + ".pdf");
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
        contentStream.setFont(font, 12);
        contentStream.showText(text);
        contentStream.endText();
    }


    private void afficherAlerteErreur(String message) {
        showAlert(Alert.AlertType.ERROR, "Erreur", message);
    }

    private void afficherAlerteErreur(String titre, String message) {
        showAlert(Alert.AlertType.ERROR, titre, message);
    }

    private boolean afficherConfirmation(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(message);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
