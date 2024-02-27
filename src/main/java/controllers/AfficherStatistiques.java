package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import services.ServiceFacture;

import java.sql.SQLException;
import java.util.Date;

public class AfficherStatistiques {
    @FXML
    private ComboBox<String> choixComboBox;

    @FXML
    private ComboBox<String> optionComboBox;

    @FXML
    private DatePicker datePicker;
    ServiceFacture serviceFacture = new ServiceFacture();

    @FXML
    public void afficherStatistiques(ActionEvent actionEvent) {
        String choix = choixComboBox.getValue();
        String option = optionComboBox.getValue();
        Date date = null;

        if (choix == null || option == null) {
            // Gérer le cas où aucune option n'est sélectionnée
            return;
        }

        if (choix.equals("Date")) {
            // Vérifier si la date est sélectionnée
            if (datePicker.getValue() == null) {
                // Gérer le cas où aucune date n'est sélectionnée
                return;
            }
            // Récupérer la date sélectionnée
            date = java.sql.Date.valueOf(datePicker.getValue());
        }

        try {
            // Utiliser les valeurs sélectionnées pour afficher les statistiques
            if (choix.equals("Date")) {
                // Afficher les statistiques en fonction de la date
                serviceFacture.afficherStatistiquesParDate(date);
            } else if (choix.equals("Type")) {
                // Afficher les statistiques en fonction du type
                serviceFacture.afficherStatistiquesParType(option);
            } else if (choix.equals("Étage")) {
                // Afficher les statistiques en fonction de l'étage
                serviceFacture.afficherStatistiquesParEtage(Integer.parseInt(option));
            }
        } catch (NumberFormatException e) {
            // Gérer le cas où l'option pour l'étage n'est pas un nombre valide
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
