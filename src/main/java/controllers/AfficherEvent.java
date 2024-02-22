package controllers;

import entities.Espace;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import services.IService;
import services.ServiceEspace;
import services.ServiceEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;


public class AfficherEvent {

    @FXML
    private ComboBox<Espace> comboBoxEspace;

    @FXML
    private TableView<Event> tableEvent;

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
        comboBoxEspace.setConverter(new StringConverter<>() {
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


// ...

    private void initializeEventTable() {
        // Configurer les colonnes de la table
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNbrPersonne.setCellValueFactory(new PropertyValueFactory<>("nbrPersonne"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colEspace.setCellValueFactory(new PropertyValueFactory<>("espace"));

        // Ajouter une colonne pour le bouton "Supprimer"
        TableColumn<Event, Void> colDelete = new TableColumn<>("Supprimer");
        // Ajouter une colonne pour le bouton "Modifier"
        TableColumn<Event, Void> colEdit = new TableColumn<>("Modifier");

        // Créer une cellule personnalisée pour le bouton "Supprimer"
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button("Supprimer");

            {
                btnDelete.setOnAction(event -> {
                    Event eventToDelete = getTableView().getItems().get(getIndex());
                    try {
                        serviceEvent.supprimer(eventToDelete.getIdEvent());
                        loadEvents(); // Actualiser la table après la suppression
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Gérer l'erreur
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnDelete);
                }
            }
        });

        // Créer une cellule personnalisée pour le bouton "Modifier"
        colEdit.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("Modifier");

            {
                btnEdit.setOnAction(event -> {
                    Event eventToEdit = getTableView().getItems().get(getIndex());
                    // Ajoutez votre logique de modification ici
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEdit);
                }
            }
        });

        // Ajouter les colonnes à la table
        tableEvent.getColumns().addAll(colDelete, colEdit);

        // Charger les données dans la table
        loadEvents();
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

    @FXML
    void saveChanges() {
        if (validateInputs()) {
            Event event = tableEvent.getSelectionModel().getSelectedItem();
            if (event != null) {
                // Mettre à jour l'objet event avec les nouvelles valeurs
                event.setName(event.getName());
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
            newEvent.setName(""); // Remplacer par la valeur appropriée
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
        // Ajoutez votre logique de validation ici
        return true;
    }
}

