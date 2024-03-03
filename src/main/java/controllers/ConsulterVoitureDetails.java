package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;




public class ConsulterVoitureDetails {

    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private TextField textFieldMarque; // Ajout de l'annotation @FXML
    @FXML
    private TextField textFieldModele; // Ajout de l'annotation @FXML
    @FXML
    private TextField textFieldCouleur; // Ajout de l'annotation @FXML
    @FXML
    private TextField textFieldMatricule; // Ajout de l'annotation @FXML


    @FXML
    private Button ajouterQRCodeButton;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation du contrôleur
    }

    @FXML
    public void initData(Image qrCodeImage, File qrCodeImageFile) {
        qrCodeImageView.setImage(qrCodeImage);
        // Décoder le contenu du code QR et extraire les détails de la voiture
        String qrCodeContent = decodeQRCode(qrCodeImageFile);
        String[] voitureDetails = qrCodeContent.split(";"); // Supposons que le contenu soit séparé par des points-virgules

        // Vérifier si le contenu du code QR est valide et contient les détails attendus
        if (voitureDetails.length == 4) { // Supposons que nous attendons 4 détails : marque, modèle, couleur, matricule
            textFieldMarque.setText(voitureDetails[0]); // Premier détail : marque
            textFieldModele.setText(voitureDetails[1]); // Deuxième détail : modèle
            textFieldCouleur.setText(voitureDetails[2]); // Troisième détail : couleur
            textFieldMatricule.setText(voitureDetails[3]); // Quatrième détail : matricule
        } else {
            // Afficher un message d'erreur si le contenu du code QR est invalide ou ne contient pas les détails attendus
            System.out.println("Contenu du code QR invalide.");
        }
    }


    @FXML
    private void handleAjouterQRCodeButton() {
        // Ouvrir une boîte de dialogue pour sélectionner l'image du QR code
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image du QR Code");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(qrCodeImageView.getScene().getWindow());

        if (selectedFile != null) {
            // Charger l'image du QR code sélectionnée
            Image qrCodeImage = new Image(selectedFile.toURI().toString());
            qrCodeImageView.setImage(qrCodeImage);

            // Passer l'image et le fichier sélectionnés à la méthode initData
            initData(qrCodeImage, selectedFile);
        }
    }

    private String decodeQRCode(File qrCodeImageFile) {
        // Implémentez la logique pour décoder le contenu du QR code à partir de l'image
        // Vous pouvez utiliser une bibliothèque de décodage de QR code comme zxing
        // Voici un exemple simple pour illustrer le processus, mais vous devrez utiliser une bibliothèque réelle :
        if (qrCodeImageFile != null && qrCodeImageFile.exists()){

            try {
                Scanner scanner = new Scanner(qrCodeImageFile);
                StringBuilder qrCodeContent = new StringBuilder();
                while (scanner.hasNextLine()) {
                    qrCodeContent.append(scanner.nextLine());
                }
                scanner.close();
                return qrCodeContent.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Erreur lors du décodage du QR code.";
            }
        } else {
            return "Fichier QR code invalide ou introuvable.";
        }

    }
    @FXML
    private void handleModifierButton(ActionEvent event) {
        // Récupérer les nouvelles valeurs des détails de la voiture à partir des champs de texte
        String nouvelleMarque = textFieldMarque.getText();
        String nouveauModele = textFieldModele.getText();
        String nouvelleCouleur = textFieldCouleur.getText();
        String nouveauMatricule = textFieldMatricule.getText();

        // Effectuer les modifications nécessaires dans la base de données ou ailleurs selon vos besoins
        // Vous pouvez appeler les méthodes appropriées de votre service pour mettre à jour les détails de la voiture

        // Par exemple, vous pouvez afficher un message de confirmation après la modification
        System.out.println("Détails de la voiture modifiés avec succès !");
    }

}