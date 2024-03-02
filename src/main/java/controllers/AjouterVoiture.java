package controllers;

import com.google.zxing.common.BitMatrix;
import entities.Parking;
import entities.Voiture;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import services.ServiceParking;
import services.ServiceVoiture;

import java.sql.SQLException;
import java.util.regex.Pattern;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ProgressBar;

public class AjouterVoiture {

    @FXML
    private TextField marqueField;

    @FXML
    private TextField modeleField;

    @FXML
    private TextField couleurField;

    @FXML
    private TextField matriculeField;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button ajouterButton;

    @FXML
    private Parking selectedParking;

    @FXML
    private ImageView qrCodeImageView;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Timeline timeline;

    private AfficherVoitureAdmin afficherVoitureAdmin;

    private int idVoitureAjoutee;

    public void setSelectedParking(Parking parking) {
        System.out.println(parking.getCapacite());
        this.selectedParking = parking;
    }

    public void setAfficherVoitureAdmin(AfficherVoitureAdmin afficherVoitureAdmin) {
        this.afficherVoitureAdmin = afficherVoitureAdmin;
    }

    @FXML
    public void initialize() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            progressBar.setVisible(false);
            generateQRCode(matriculeField.getText());
        }));
        timeline.setCycleCount(1);
    }

    @FXML
    private void handleAjouterButton(ActionEvent event) {
        String marque = marqueField.getText();
        String modele = modeleField.getText();
        String couleur = couleurField.getText();
        String matricule = matriculeField.getText();

        if (!validateMarque(marque) || !validateModele(modele) || !validateCouleur(couleur) || !validateMatricule(matricule)) {
            ajouterButton.setDisable(false);
            return;
        }

        supprimerButton.setDisable(true);
        ajouterButton.setDisable(true);
        progressBar.setVisible(true);
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        Timeline delayTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            try {
                ServiceVoiture serviceVoiture = new ServiceVoiture();
                if (serviceVoiture.existeMatricule(matricule)) {
                    afficherMessageErreur("La matricule de la voiture existe déjà dans le parking.");
                    ajouterButton.setDisable(false);
                    return;
                }

                ServiceParking serviceParking = new ServiceParking();
                selectedParking = serviceParking.getOneById(selectedParking.getIdParking());

                if (selectedParking.getNombreActuelles() >= selectedParking.getCapacite()) {
                    afficherMessageErreur("Le parking est plein. Impossible d'ajouter plus de voitures.");
                    ajouterButton.setDisable(false);
                    return;
                }

                Voiture voiture = new Voiture(selectedParking.getIdParking(), marque, modele, couleur, matricule, selectedParking);

                idVoitureAjoutee = serviceVoiture.ajouter(voiture);

                if (idVoitureAjoutee != -1) {
                    generateQRCode(matricule);
                    progressBar.setVisible(false);
                    progressBar.setProgress(0);

                    selectedParking.setNombreActuelles(selectedParking.getNombreActuelles() + 1);
                    serviceParking.modifier(selectedParking);

                    afficherQRCode(matricule); // Affichage du code QR après le délai

                    afficherMessageSucces("Voiture ajoutée avec succès!");
                } else {
                    // Traitement en cas d'échec de l'ajout de la voiture
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                ajouterButton.setDisable(false);
            }

            System.out.println("Capacité du parking après ajout : " + selectedParking.getNombreActuelles() + "/" + selectedParking.getCapacite());
        }
        ));
        delayTimeline.setCycleCount(1);
        delayTimeline.play();
    }



    private void generateQRCode(String matricule) {
        int width = 300;
        int height = 300;
        String fileType = "png";
        String filePath = "C:\\Users\\hp\\Desktop\\Nouveau dossier";

        String data = "Matricule: " + matricule;

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, fileType, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();

            File qrFile = new File(filePath + "QRCode_" + matricule + "." + fileType);
            ImageIO.write(bufferedImage, fileType, qrFile);

            System.out.println("QR code généré avec succès.");

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherQRCode(String matricule) {
        String filePath = "C:\\Users\\hp\\Desktop\\Nouveau dossier";
        String fileName = "QRCode_" + matricule + ".png";

        File file = new File(filePath + fileName);
        if (file.exists()) {
            Image qrCodeImage = new Image(file.toURI().toString());
            qrCodeImageView.setImage(qrCodeImage);
        } else {
            System.out.println("Le fichier QR code n'existe pas : " + fileName);
        }
    }

    @FXML
    private void handleSupprimerButton(ActionEvent event) {
        if (idVoitureAjoutee != -1) {
            try {
                ServiceVoiture serviceVoiture = new ServiceVoiture();
                serviceVoiture.supprimer(idVoitureAjoutee);
                afficherMessageSucces("Voiture supprimée avec succès!");

                selectedParking.setNombreActuelles(selectedParking.getNombreActuelles() - 1);

                ServiceParking serviceParking = new ServiceParking();
                serviceParking.modifier(selectedParking);

                marqueField.clear();
                modeleField.clear();
                couleurField.clear();
                matriculeField.clear();

                supprimerButton.setDisable(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            afficherMessageErreur("Aucune voiture ajoutée récemment pour être supprimée.");
        }
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessageSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private boolean validateMarque(String marque) {
        if (marque.isEmpty()) {
            afficherMessageErreur("Veuillez saisir la marque de la voiture.");
            return false;
        }
        if (marque.length() > 20) {
            afficherMessageErreur("La marque ne peut pas dépasser 20 caractères.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z]*$", marque)) {
            afficherMessageErreur("La marque doit contenir uniquement des lettres.");
            return false;
        }
        return true;
    }

    private boolean validateModele(String modele) {
        if (modele.isEmpty()) {
            afficherMessageErreur("Veuillez saisir le modèle de la voiture.");
            return false;
        }
        if (modele.length() > 20) {
            afficherMessageErreur("Le modèle ne peut pas dépasser 20 caractères.");
            return false;
        }
        if (!modele.matches("^[a-zA-Z0-9\\s]+$")) {
            afficherMessageErreur("Le modèle doit contenir uniquement des lettres, des espaces ou des chiffres.");
            return false;
        }

        return true;
    }

    private boolean validateCouleur(String couleur) {
        if (couleur.isEmpty()) {
            afficherMessageErreur("Veuillez saisir la couleur de la voiture.");
            return false;
        }
        if (couleur.length() > 20) {
            afficherMessageErreur("La couleur ne peut pas dépasser 20 caractères.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z]*$", couleur)) {
            afficherMessageErreur("La couleur doit contenir uniquement des lettres.");
            return false;
        }
        return true;
    }

    private boolean validateMatricule(String matricule) {
        if (matricule.isEmpty()) {
            afficherMessageErreur("Veuillez saisir le numéro de matricule de la voiture.");
            return false;
        }
        if (matricule.length() < 6 || matricule.length() > 15) {
            afficherMessageErreur("Le numéro de matricule doit contenir entre 6 et 15 caractères.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z0-9]*$", matricule)) {
            afficherMessageErreur("Le numéro de matricule doit contenir uniquement des lettres et des chiffres.");
            return false;
        }
        return true;
    }
}
