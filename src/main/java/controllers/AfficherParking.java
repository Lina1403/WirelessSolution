package controllers;

import entities.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import services.IService;
import services.ServiceParking;
import java.sql.SQLException;
import java.util.Set;

public class AfficherParking {

    @FXML
    private TableView<Parking> tableParking;

    @FXML
    private TableColumn<Parking, String> colPlace;

    @FXML
    private TableColumn<Parking, Integer> colNumPlace;

    @FXML
    private TableColumn<Parking, Integer> colCapacite;

    @FXML
    private TableColumn<Parking, Void> colDelete;

    @FXML
    private TableColumn<Parking, Void> colEdit;

    @FXML
    private TextField txtPlace;

    @FXML
    private TextField txtNumPlace;

    @FXML
    private TextField txtCapacite;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;


    private Parking parking;

    private final IService<Parking> serviceParking = new ServiceParking();

    @FXML
    void initialize() throws SQLException  {

        Set<Parking> parkings = serviceParking.getAll();
        ObservableList<Parking> parkingList = FXCollections.observableArrayList(parkings);

        colPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
        colNumPlace.setCellValueFactory(new PropertyValueFactory<>("numPlace"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        // Ajout de la colonne de suppression
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    deleteButton.setOnAction(event -> {
                        Parking parking = getTableView().getItems().get(getIndex());
                        // Supprimer l'élément
                        try {
                            serviceParking.supprimer(parking.getIdParking());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        tableParking.getItems().remove(parking);
                    });

                    setGraphic(deleteButton);
                }
            }
        });

        // Ajout de la colonne de modification
        colEdit.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    editButton.setOnAction(event -> {
                        parking = getTableView().getItems().get(getIndex());
                        // Charger les détails du parking dans les champs de texte
                        txtPlace.setText(parking.getPlace());
                        txtNumPlace.setText(String.valueOf(parking.getNumPlace()));
                        txtCapacite.setText(String.valueOf(parking.getCapacite()));
                    });

                    setGraphic(editButton);
                }
            }
        });

        tableParking.setItems(parkingList);
    }

    @FXML
    void saveChanges() {
        // Mettre à jour l'objet parking avec les nouvelles valeurs
        parking.setPlace(txtPlace.getText());
        parking.setNumPlace(Integer.parseInt(txtNumPlace.getText()));
        parking.setCapacite(Integer.parseInt(txtCapacite.getText()));

        // Mettre à jour l'objet parking dans la base de données
        try {
            serviceParking.modifier(parking);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Rafraîchir le tableau
        tableParking.refresh();
    }

    @FXML
    void ajouterParking() {
        // Créer un nouvel objet Parking avec les valeurs des champs de texte
        Parking newParking = new Parking();
        newParking.setPlace(txtPlace.getText());
        newParking.setNumPlace(Integer.parseInt(txtNumPlace.getText()));
        newParking.setCapacite(Integer.parseInt(txtCapacite.getText()));

        // Ajouter le nouvel objet Parking à la base de données
        try {
            serviceParking.ajouter(newParking);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Ajouter le nouvel objet Parking au tableau
        tableParking.getItems().add(newParking);

        // Rafraîchir le tableau
        tableParking.refresh();
    }

}
