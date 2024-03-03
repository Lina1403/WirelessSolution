package controllers;

import entities.Facture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceFacture;

import java.sql.SQLException;
import java.time.LocalDate;

public class Statistique {
    ServiceFacture serviceFacture = new ServiceFacture();
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

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

    }



    public void afficherStatistiquesEtage(ActionEvent actionEvent) {
        try {
            String typeFactureStr = getTypeFactureSelectionne();
            Facture.Type typeFacture = Facture.Type.valueOf(typeFactureStr); // Convertir String en Facture.Type
            LocalDate dateDebut = getDateDebutSelectionnee();
            LocalDate dateFin = getDateFinSelectionnee();
            int nombreEtages = getNombreEtages();

            // Initialiser la série de données pour le graphique
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Consommation d'énergie par étage");

            // Appel à la méthode du service pour récupérer les statistiques pour un étage spécifique
            float consommationEnergie = serviceFacture.calculerConsommationEnergieTotaleParEtageEtType(nombreEtages, typeFacture);

            // Ajouter les données à la série
            series.getData().add(new XYChart.Data<>("Étage", consommationEnergie));

            // Effacer les données précédentes du graphique
            barChart.getData().clear();

            // Ajouter la série de données mise à jour au graphique
            barChart.getData().add(series);

            // Affichage des statistiques dans votre interface utilisateur
            System.out.println("Consommation d'énergie pour l'étage sélectionné : " + consommationEnergie);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur
        }
    }



    public void afficherStatistiquesTousAppartements(ActionEvent actionEvent) {

    }


}
