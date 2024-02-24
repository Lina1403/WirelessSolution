package controllers;

import entities.Facture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceFacture;

import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierFacture {

    @FXML
    private TextField id_numFacture_modifier;

    @FXML
    private TextField id_montant_modifier;
    @FXML
    private ComboBox<String> typeComboBox; // Ajoutez cette ligne
    @FXML
    private DatePicker id_date_modifier;

    @FXML
    private TextField id_description_modifier;

    private final ServiceFacture serviceFacture = new ServiceFacture();
    private Facture selectedFacture;
    @FXML
    void modifierFacture(ActionEvent event) {
        try {
            int idFacture = Integer.parseInt(id_numFacture_modifier.getText());

            float montant = Float.parseFloat(id_montant_modifier.getText());
            LocalDate date = id_date_modifier.getValue();
            String description = id_description_modifier.getText();
            String type = typeComboBox.getValue(); // Récupérez la valeur sélectionnée du ComboBox

            // Récupérer la facture à modifier
            Facture facture = serviceFacture.getOneById(idFacture);

            if (facture != null) {
                // Mettre à jour les attributs de la facture
                facture.setMontant(montant);
                facture.setDate(java.sql.Date.valueOf(date));
                facture.setDescriptionFacture(description);
                facture.setType(Facture.Type.valueOf(type)); // Définissez le type de facture

                // Appeler la méthode pour modifier la facture dans la base de données
                serviceFacture.modifier(facture);

                System.out.println("Facture modifiée avec succès !");
            } else {
                System.out.println("Facture non trouvée !");
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez saisir des valeurs valides pour les champs numériques !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la facture : " + e.getMessage());
        }
    }


  /*  @FXML
    void modifierFacture(ActionEvent event) {
        try {
            int idFacture = Integer.parseInt(id_numFacture_modifier.getText());

            int numFacture = Integer.parseInt(id_numFacture_modifier.getText());
            float montant = Float.parseFloat(id_montant_modifier.getText());
            LocalDate date = id_date_modifier.getValue();
            String description = id_description_modifier.getText();
            String type = typeComboBox.getValue(); // Récupérez la valeur sélectionnée du ComboBox
            // Récupérer la facture à modifier
            Facture facture = serviceFacture.getOneById(idFacture);
            if (facture != null) {
                // Mettre à jour les attributs de la facture
                facture.setMontant(montant);
                facture.setDate(java.sql.Date.valueOf(date));
                facture.setDescriptionFacture(description);
                facture.setType(Facture.Type.valueOf(type)); // Définissez le type de facture

                // Appeler la méthode pour modifier la facture dans la base de données
                serviceFacture.modifier(facture);

                System.out.println("Facture modifiée avec succès !");
            } else {
                System.out.println("Facture non trouvée !");
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez saisir des valeurs valides pour les champs numériques !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la facture : " + e.getMessage());
        }
    } */


    }
