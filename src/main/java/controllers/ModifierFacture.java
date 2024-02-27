package controllers;

import entities.Facture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceFacture;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierFacture {

    @FXML
    private TextField id_numFacture_modifier;

    @FXML
    private TextField id_montant_modifier;

    @FXML
    private DatePicker id_date_modifier;

    @FXML
    private TextField id_description_modifier;

    @FXML
    private ComboBox<String> typeComboBox;

    private final ServiceFacture serviceFacture = new ServiceFacture();
    private Facture selectedFacture;

    public void setFactureSelectionne(Facture facture) {
        this.selectedFacture = facture;
        if (facture != null) {
            id_numFacture_modifier.setText(String.valueOf(facture.getNumFacture()));
            id_montant_modifier.setText(String.valueOf(facture.getMontant()));
            Date sqlDate = (Date) facture.getDate();
            LocalDate localDate = sqlDate.toLocalDate();
            id_date_modifier.setValue(localDate);
            id_description_modifier.setText(facture.getDescriptionFacture());
            typeComboBox.setValue(facture.getType().toString());
        }
    }

    @FXML
    void modifierFacture(ActionEvent event) {
        if (selectedFacture != null) {
            try {
                int numFacture = Integer.parseInt(id_numFacture_modifier.getText());
                float montant = Float.parseFloat(id_montant_modifier.getText());
                LocalDate date = id_date_modifier.getValue();
                String description = id_description_modifier.getText();
                Facture.Type type = Facture.Type.valueOf(typeComboBox.getValue());

                selectedFacture.setNumFacture(numFacture);
                selectedFacture.setMontant(montant);
                selectedFacture.setDate(java.sql.Date.valueOf(date));
                selectedFacture.setDescriptionFacture(description);
                selectedFacture.setType(type);

                serviceFacture.modifier(selectedFacture);

                System.out.println("Facture modifiée avec succès !");
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir des valeurs valides pour les champs numériques !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la modification de la facture : " + e.getMessage());
            }
        } else {
            System.out.println("Aucune facture sélectionnée.");
        }
    }
}
