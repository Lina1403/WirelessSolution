package controllers;

import entities.Espace;
import services.ServiceEspace;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.sql.SQLException;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class StatisticsController {

    @FXML
    private BarChart<String, Number> reservationChart;

    @FXML
    private CategoryAxis monthAxis;

    @FXML
    private PieChart pieChart;

    @FXML
    private TableView<Espace> tableView;

    @FXML
    private TableColumn<Espace, String> spaceColumn;

    @FXML
    private TableColumn<Espace, Integer> countColumn;

    private ServiceEspace serviceEspace;

    @FXML
    public void initialize() {
        serviceEspace = new ServiceEspace();
        displayEspaceStatistics();
    }

    public void displayEspaceStatistics() {
        try {
            Set<Espace> espaces = serviceEspace.getAll();
            ObservableList<Espace> data = FXCollections.observableArrayList(espaces);
            tableView.setItems(data);

            // Calculate statistics based on the number of reservations per espace
            Map<Espace, Integer> reservationsPerEspace = new HashMap<>();
            for (Espace espace : espaces) {
                int reservationCount = serviceEspace.getReservationCountForEspace(espace.getIdEspace());
                reservationsPerEspace.put(espace, reservationCount);
            }

            // Display statistics on charts
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Espace espace : reservationsPerEspace.keySet()) {
                series.getData().add(new XYChart.Data<>(espace.getName(), reservationsPerEspace.get(espace)));
            }
            reservationChart.getData().add(series);

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Espace espace : reservationsPerEspace.keySet()) {
                pieChartData.add(new PieChart.Data(espace.getName(), reservationsPerEspace.get(espace)));
            }
            pieChart.setData(pieChartData);

            // Set chart axes labels
            monthAxis.setLabel("Espace");

            // Set chart title
            reservationChart.setTitle("Espace Statistics");
            pieChart.setTitle("Espace Statistics");

            // Associate each property of Espace with the corresponding column in the TableView
            spaceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            countColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(reservationsPerEspace.get(cellData.getValue())).asObject());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
