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

public class Statistique {
    ServiceFacture serviceFacture = new ServiceFacture();

    @FXML
    private ComboBox<String> typeFactureComboBox;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField nbrEtagesTextField;

    public void initialiserStatistiques() {

    }

    // Méthode pour récupérer le type de facture sélectionné
    public String getTypeFactureSelectionne() {
        return typeFactureComboBox.getValue();
    }

    // Méthode pour récupérer la date de début sélectionnée
    public LocalDate getDateDebutSelectionnee() {
        return dateDebutPicker.getValue();
    }

    // Méthode pour récupérer la date de fin sélectionnée
    public LocalDate getDateFinSelectionnee() {
        return dateFinPicker.getValue();
    }

    // Méthode pour récupérer le nombre d'étages saisi
    public int getNombreEtages() {
        try {
            return Integer.parseInt(nbrEtagesTextField.getText());
        } catch (NumberFormatException e) {
            // Gérer l'erreur si la saisie n'est pas un nombre entier
            e.printStackTrace();
            return -1; // Retourner une valeur par défaut ou gérer l'erreur autrement
        }
    }

    public void afficherStatistiquesAppartement(ActionEvent actionEvent) {
        try {
            String typeFactureStr = getTypeFactureSelectionne();
            Facture.Type typeFacture = Facture.Type.valueOf(typeFactureStr); // Convertir String en Facture.Type
            LocalDate dateDebut = getDateDebutSelectionnee();
            LocalDate dateFin = getDateFinSelectionnee();
            int nombreEtages = getNombreEtages();

            // Appel à la méthode du service pour récupérer les statistiques pour un appartement spécifique
            float consommationEnergie = serviceFacture.calculerConsommationEnergieAppartementParTypeEtPeriode(nombreEtages, typeFacture, dateDebut, dateFin);
            // Affichage des statistiques dans votre interface utilisateur
            System.out.println("Consommation d'énergie pour l'appartement sélectionné : " + consommationEnergie);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur
        }
    }



    public void afficherStatistiquesEtage(ActionEvent actionEvent) {
        try {
            String typeFactureStr = getTypeFactureSelectionne();
            Facture.Type typeFacture = Facture.Type.valueOf(typeFactureStr); // Convertir String en Facture.Type
            LocalDate dateDebut = getDateDebutSelectionnee();
            LocalDate dateFin = getDateFinSelectionnee();
            int nombreEtages = getNombreEtages();

            // Appel à la méthode du service pour récupérer les statistiques pour un étage spécifique
            float consommationEnergie = serviceFacture.calculerConsommationEnergieTotaleParEtageEtType(nombreEtages, typeFacture);
            // Affichage des statistiques dans votre interface utilisateur
            System.out.println("Consommation d'énergie pour l'étage sélectionné : " + consommationEnergie);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur
        }
    }
    public void afficherStatistiquesTousAppartements(ActionEvent actionEvent) {
        String typeFactureStr = getTypeFactureSelectionne();
        Facture.Type typeFacture = null;
        try {
            typeFacture = Facture.Type.valueOf(typeFactureStr);
        } catch (IllegalArgumentException e) {
            // Handle the case where the enum constant does not exist
            e.printStackTrace();
            return; // Exit the method or handle the error as appropriate
        }
        LocalDate dateDebut = getDateDebutSelectionnee();
        LocalDate dateFin = getDateFinSelectionnee();

        // Rest of your method logic...
    }


}
