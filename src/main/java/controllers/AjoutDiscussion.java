package controllers;
import entities.Discussion;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.DiscussionService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AjoutDiscussion {

        @FXML
        private TextField titre ;

        User user1 = new User(2,"oussema");
        @FXML
        void ajouterEvent(ActionEvent event) {
                String title = titre.getText();
                Timestamp currentTimestamp = new Timestamp( System.currentTimeMillis());
                Discussion discussion = new Discussion(title,currentTimestamp,user1);
                if(titreValide(title)){
                        try {
                                DiscussionService ds = new DiscussionService();
                                ds.ajouter(discussion);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Success");
                                alert.setContentText("Discussion added successfully!");

                                alert.showAndWait();
                        }catch(SQLException e ){
                                System.out.println(e.getMessage());
                        }
                }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("error");
                        alert.setContentText("cant add discussion title innapropriate");
                        alert.showAndWait();
                }


        }
        public static boolean titreValide(String titre) {
                List<String> motsInterdits = null;
                try {
                        motsInterdits = Files.readAllLines(Paths.get("src/main/java/utils/motsinap.txt"));
                } catch (IOException e) {
                        System.out.println("Erreur lors de la lecture du fichier de mots inappropriés");
                        e.printStackTrace();
                }
                if (motsInterdits == null) {
                        return true;
                }

                // Convertir le titre en minuscules pour une comparaison insensible à la casse
                String titreMinuscules = titre.toLowerCase();

                // Vérifier si le titre contient un mot interdit
                for (String mot : motsInterdits) {
                        if (titreMinuscules.contains(mot)) {
                                return false;
                        }
                }

                // Si aucun mot interdit n'est trouvé, le titre est valide
                return true;
        }

}
