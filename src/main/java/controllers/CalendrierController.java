package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import entities.Espace;
import entities.Event;
import services.ServiceEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CalendrierController implements Initializable {

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;


    private ServiceEvent eventService;
    private Espace selectedEspace; // Espace sélectionné
    private YearMonth currentYearMonth; // Current year and month


    public void selectEspace(Espace espace) {
        this.selectedEspace = espace;
        drawCalendar(); // Redessiner le calendrier pour l'espace sélectionné
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventService = new ServiceEvent(); // Initialize the event service
        currentYearMonth = YearMonth.now(); // Set the current year and month
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        currentYearMonth = currentYearMonth.minusMonths(1); // Move to the previous month
        drawCalendar(); // Redraw the calendar
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        currentYearMonth = currentYearMonth.plusMonths(1); // Move to the next month
        drawCalendar(); // Redraw the calendar
    }

    private void drawCalendar() {
        year.setText(String.valueOf(currentYearMonth.getYear()));
        month.setText(String.valueOf(currentYearMonth.getMonth()));

        int daysInMonth = currentYearMonth.lengthOfMonth();

        calendar.getChildren().clear(); // Clear previous calendar entries

        // Map to store events for each date
        Map<LocalDate, List<Event>> eventMap = getAllEventsForMonth(currentYearMonth);

        // Loop through the calendar cells
        for (int day = 1; day <= daysInMonth; day++) {
            StackPane stackPane = new StackPane();

            Rectangle rectangle = new Rectangle(40, 40);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);
            stackPane.getChildren().add(rectangle);

            Text dayText = new Text(String.valueOf(day));
            stackPane.getChildren().add(dayText);

            LocalDate currentDate = currentYearMonth.atDay(day);
            List<Event> eventsForDate = eventMap.getOrDefault(currentDate, new ArrayList<>());
            if (!eventsForDate.isEmpty()) {
                rectangle.setFill(Color.LIGHTBLUE); // Highlight the day if there are events
            }

            calendar.getChildren().add(stackPane);
        }
    }

    // Method to get all events for the given month
    // Method to get all events for the given month
    private Map<LocalDate, List<Event>> getAllEventsForMonth(YearMonth yearMonth) {
        Map<LocalDate, List<Event>> eventMap = new HashMap<>();
        try {
            if (selectedEspace != null) { // Check if selectedEspace is not null
                // Récupérer les événements de l'espace sélectionné pour le mois en cours
                Set<Event> events = eventService.getEventsForMonth(selectedEspace.getIdEspace(), yearMonth);

                // Filtrer les événements pour le mois en cours
                for (Event event : events) {
                    LocalDate eventDate = event.getDate().toLocalDate();
                    if (eventDate.getMonth() == yearMonth.getMonth()) {
                        // Ajouter l'événement à la carte des événements
                        eventMap.computeIfAbsent(eventDate, k -> new ArrayList<>()).add(event);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to fetch events from the database.");
        }
        return eventMap;
    }


    // Method to display an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
