package controllers;

import entities.Espace;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import services.IService;
import services.ServiceEspace;
import services.ServiceEvent;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class AfficherEvent {

    @FXML
    private ComboBox<Espace> comboBoxEspace;

    @FXML
    private ListView <Event> tableEvent;

    @FXML
    private TableColumn<Event, String> colName;

    @FXML
    private TableColumn<Event, String> colTitle;

    @FXML
    private TableColumn<Event, Date> colDate;

    @FXML
    private TableColumn<Event, Integer> colNbrPersonne;

    @FXML
    private TableColumn<Event, Espace> colEspace;

    @FXML
    private TableColumn<Event, String> colDescription;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;

    @FXML
    private Label lblNameError;

    @FXML
    private Label lblTitleError;

    @FXML
    private Label lblDateError;

    @FXML
    private Label lblNbrPersonneError;

    @FXML
    private Label lblDescriptionError;

    @FXML
    private DatePicker datePicker;

    private final IService<Event> serviceEvent = new ServiceEvent();
    private final IService<Espace> serviceEspace = new ServiceEspace();

    @FXML
    void initialize() throws SQLException {
        // Remplir le ComboBox avec les espaces disponibles
        Set<Espace> espaces = serviceEspace.getAll();
        ObservableList<Espace> espaceList = FXCollections.observableArrayList(espaces);
        comboBoxEspace.setItems(espaceList);

        // Configurer la manière dont les espaces sont affichés dans le ComboBox
        comboBoxEspace.setConverter(new StringConverter<Espace>() {
            @Override
            public String toString(Espace espace) {
                if (espace != null) {
                    return espace.getName();
                } else {
                    return "espace";
                }
            }

            @Override
            public Espace fromString(String string) {
                // Implement if needed
                return null;
            }
        });

        // Initialiser la table des événements
        initializeEventTable();
    }

    private void initializeEventTable() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date")); // Assurez-vous que votre classe Event a une méthode getDate() qui retourne un java.util.Date
        colNbrPersonne.setCellValueFactory(new PropertyValueFactory<>("nbrPersonne"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colEspace.setCellValueFactory(new PropertyValueFactory<>("espace"));
    }

    @FXML
    void saveChanges() {
        if (validateInputs()) {
            Event event = tableEvent.getSelectionModel().getSelectedItem();
            if (event != null) {
                // Mettre à jour l'objet event avec les nouvelles valeurs
                event.setTitle(event.getTitle());
                event.setDate(event.getDate());
                event.setNbrPersonne(event.getNbrPersonne());
                event.setDescription(event.getDescription());
                event.setEspace(comboBoxEspace.getValue());

                // Mettre à jour l'objet event dans la base de données
                try {
                    serviceEvent.modifier(event);
                    loadEvents(); // Actualiser la table après la modification
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer l'erreur
                }
            }
        }
    }

    @FXML
    void ajouterEvent() {
        if (validateInputs()) {
            Event newEvent = new Event();
            newEvent.setTitle(""); // Remplacer par la valeur appropriée
            LocalDate date = datePicker.getValue();
            if (date == null) {
                throw new IllegalArgumentException("Veuillez sélectionner une date.");
            }
            newEvent.setDate(java.sql.Date.valueOf(date));
            newEvent.setNbrPersonne(0); // Remplacer par la valeur appropriée
            newEvent.setDescription(""); // Remplacer par la valeur appropriée
            newEvent.setEspace(comboBoxEspace.getValue());

            try {
                serviceEvent.ajouter(newEvent);
                loadEvents(); // Actualiser la table après l'ajout
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur
            }
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validation du titre
        if (lblTitleError.getText().isEmpty()) {
            lblTitleError.setText("Le titre est requis");
            isValid = false;
        } else {
            lblTitleError.setText("");
        }

        // Validation de la date
        if (datePicker.getValue() == null) {
            lblDateError.setText("La date est requise");
            isValid = false;
        } else {
            lblDateError.setText("");
        }

        // Validation du nombre de personnes
        if (lblNbrPersonneError.getText().isEmpty() || !lblNbrPersonneError.getText().matches("\\d+")) {
            lblNbrPersonneError.setText("Nombre de personnes invalide");
            isValid = false;
        } else {
            lblNbrPersonneError.setText("");
        }

        // Validation de la description
        if (lblDescriptionError.getText().isEmpty()) {
            lblDescriptionError.setText("La description est requise");
            isValid = false;
        } else {
            lblDescriptionError.setText("");
        }

        return isValid;
    }


    private void loadEvents() {
        try {
            Set<Event> events = serviceEvent.getAll();
            tableEvent.setItems(FXCollections.observableArrayList(events));
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur
        }
    }
}
