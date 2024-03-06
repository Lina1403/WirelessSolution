package edu.esprit.controllers;

import edu.esprit.services.ServiceReclamation;
import edu.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class StatistiquesReclamation {
    @FXML
    private BarChart<String, Integer> barChartDate;
    @FXML
    private PieChart pieChartResident;

    // It's now better to use getInstance() method if it's meant to implement Singleton pattern
    // private final ServiceReclamation serviceReclamation = new ServiceReclamation();
    private final ServiceReclamation serviceReclamation = ServiceReclamation.getInstance();

    public void initialize() {
        try {
            populateBarChart();
            populatePieChartUsingService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateBarChart() throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        String sql = "SELECT DATE(DateRec) AS date, COUNT(*) AS count FROM Reclamation GROUP BY DATE(DateRec)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<XYChart.Data<String, Integer>> data = FXCollections.observableArrayList();
        while (rs.next()) {
            data.add(new XYChart.Data<>(rs.getString("date"), rs.getInt("count")));
        }
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Reclamations par date");
        series.setData(data);
        barChartDate.getData().add(series);
    }

    private void populatePieChartUsingService() throws SQLException {
        Map<Integer, Integer> residentCounts = serviceReclamation.countReclamationsPerUser();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Integer> entry : residentCounts.entrySet()) {
            pieChartData.add(new PieChart.Data("Resident " + entry.getKey(), entry.getValue()));
        }
        pieChartResident.setData(pieChartData);
    }
}