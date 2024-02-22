/* package controllers;


import entities.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.IService;
import services.ServiceParking;
import java.sql.SQLException;
import java.util.Set;

public class AfficherParking {

    @FXML
    private TableView<Parking> tableParking;

    @FXML
    private TableColumn<Parking, String> colNom;

    @FXML
    private TableColumn<Parking, Integer> colType;

    @FXML
    private TableColumn<Parking, Integer> colCapacite;

    @FXML
    private TableColumn<Parking, Void> colDelete;

    @FXML
    private TableColumn<Parking, Void> colEdit;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtCapacite;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;

    @FXML
    private Label lblNomError, lblTypeError, lblCapaciteError;



    private Parking parking;

    private final IService<Parking> serviceParking = new ServiceParking();

    @FXML
    void initialize() throws SQLException  {

        Set<Parking> parkings = serviceParking.getAll();
        ObservableList<Parking> parkingList = FXCollections.observableArrayList(parkings);

        colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
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
                        txtNom.setText(parking.getNom());
                        txtType.setText(String.valueOf(parking.getType()));
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
        if (validateInputs()) {
            // Mettre à jour l'objet parking avec les nouvelles valeurs
            parking.setNom(txtNom.getText());
            parking.setType(Integer.parseInt(txtType.getText()));
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
    }

    @FXML
    void ajouterParking() {
        if (validateInputs()) {

            // Créer un nouvel objet Parking avec les valeurs des champs de texte
            Parking newParking = new Parking();
            newParking.setNom(txtNom.getText());
            newParking.setType(Integer.parseInt(txtType.getText()));
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
    private boolean validateInputs() {
        // Valider le champ Nom
        String nom = txtNom.getText().trim();
        if (nom.isEmpty() || !nom.matches("[a-zA-Z]{1,20}")) {
            // Afficher un message d'erreur
            lblNomError.setText("Le nom doit contenir uniquement des lettres et avoir une longueur maximale de 20 caractères.");
            return false;
        } else {
            lblNomError.setText(""); // Effacer le message d'erreur précédent
        }

        // Valider le champ Numéro de place
        String type = txtType.getText().trim();
        if (type.isEmpty() || !type.matches("\\d+") || Integer.parseInt(type) <= 0 || Integer.parseInt(type) > 3) {
            // Afficher un message d'erreur
            lblTypeError.setText("Le type doit être un entier positif et ne doit pas dépasser 3.");
            return false;
        } else {
            lblTypeError.setText(""); // Effacer le message d'erreur précédent
        }


        // Valider le champ Capacité
        String capacite = txtCapacite.getText().trim();
        if (capacite.isEmpty() || !capacite.matches("\\d+") || Integer.parseInt(capacite) <= 0 || Integer.parseInt(capacite) > 50) {
            // Afficher un message d'erreur
            lblCapaciteError.setText("La capacité doit être un entier positif et ne doit pas dépasser 50.");
            return false;
        } else {
            lblCapaciteError.setText(""); // Effacer le message d'erreur précédent
        }


        return true;
    }


}

 */