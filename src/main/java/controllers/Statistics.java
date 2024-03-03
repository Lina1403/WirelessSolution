package controllers;

import entities.Facture;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.StatiqueService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class Statistics {
    private StatiqueService serviceStatique = new StatiqueService();

    @FXML
    private ComboBox<String> typeFactureComboBox;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField nbrEtagesTextField;

    @FXML
    private BarChart<String, Number> barChart;


    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    public void initialiserStatistiques() {

    }

    @FXML
    void afficherStatistiquesAppartement(ActionEvent event) {
        String typeFacture = typeFactureComboBox.getValue();
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        int idAppartement = Integer.parseInt(nbrEtagesTextField.getText());
        Facture.Type type = Facture.Type.valueOf(typeFacture);

        afficherStatistiquesA(idAppartement, type, dateDebut, dateFin);
    }

    @FXML
    void afficherStatistiquesEtage(ActionEvent event) {
        String typeFacture = typeFactureComboBox.getValue();
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        int numeroEtage = Integer.parseInt(nbrEtagesTextField.getText());
        Facture.Type type = Facture.Type.valueOf(typeFacture);

        afficherStatistiquesB(numeroEtage, type, dateDebut, dateFin);
    }

    @FXML
    void afficherStatistiquesTousAppartements(ActionEvent event) {
        String typeFacture = typeFactureComboBox.getValue();
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        Facture.Type type = Facture.Type.valueOf(typeFacture);

        afficherStatistiquesC(type, dateDebut, dateFin);
    }

    private void afficherStatistiquesA(int idAppartement, Facture.Type typeFacture, LocalDate dateDebut, LocalDate dateFin) {
        try {
            float consommation = serviceStatique.calculerConsommationEnergieAppartementParTypeEtPeriode(idAppartement, typeFacture, dateDebut, dateFin);
            System.out.println("Statistiques pour l'appartement " + idAppartement + " de type " + typeFacture + " entre " + dateDebut + " et " + dateFin + ":");
            System.out.println("Consommation: " + consommation);

            // Affichage des données sur le graphique
            barChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Appartement " + idAppartement, consommation));
            barChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherStatistiquesB(int numeroEtage, Facture.Type typeFacture, LocalDate dateDebut, LocalDate dateFin) {
        try {
            float consommationEtage = serviceStatique.calculerConsommationEnergieTotaleParEtageEtType(numeroEtage, typeFacture, dateDebut, dateFin);
            System.out.println("Statistiques pour l'étage " + numeroEtage + " de type " + typeFacture + " entre " + dateDebut + " et " + dateFin + ":");
            System.out.println("Consommation de l'étage: " + consommationEtage);

            // Affichage des données sur le graphique
            barChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Étage " + numeroEtage, consommationEtage));
            barChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void afficherStatistiquesC(Facture.Type typeFacture, LocalDate dateDebut, LocalDate dateFin) {
        try {
            Map<Integer, Float> consommations = serviceStatique.calculerConsommationEnergieTotaleParTypeEtPeriode(typeFacture, dateDebut, dateFin);
            System.out.println("Statistiques pour tous les appartements de type " + typeFacture + " entre " + dateDebut + " et " + dateFin + ":");
            for (Map.Entry<Integer, Float> entry : consommations.entrySet()) {
                System.out.println("Appartement " + entry.getKey() + ": Consommation " + entry.getValue());
            }

            // Affichage des données sur le graphique
            barChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Map.Entry<Integer, Float> entry : consommations.entrySet()) {
                series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
            }
            barChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
