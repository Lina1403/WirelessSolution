package controllers;

import entities.Espace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.IService;
import services.ServiceEspace;

import java.sql.SQLException;
import java.util.Set;

public class AfficherEspace {

    @FXML
    private TableView<Espace> tableEspace;

    @FXML
    private TableColumn<Espace, String> colNom;

    @FXML
    private TableColumn<Espace, Espace.Etat> colEtat;

    @FXML
    private TableColumn<Espace, Integer> colCapacite;

    @FXML
    private TableColumn<Espace, String> colDescription;

    @FXML
    private TableColumn<Espace, Void> colDelete;

    @FXML
    private TableColumn<Espace, Void> colEdit;

    @FXML
    private TextField txtNom;

    @FXML
    private ComboBox<Espace.Etat> cbEtat;

    @FXML
    private TextField txtCapacite;

    @FXML
    private TextField txtDescription;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;

    @FXML
    private Label lblNomError, lblCapaciteError, lblDescriptionError;

    private Espace espace;

    private final IService<Espace> serviceEspace = new ServiceEspace();

    @FXML
    void initialize() throws SQLException {

        Set<Espace> espaces = serviceEspace.getAll();
        ObservableList<Espace> espaceList = FXCollections.observableArrayList(espaces);

        colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

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
                        Espace espace = getTableView().getItems().get(getIndex());
                        // Supprimer l'élément
                        try {
                            serviceEspace.supprimer(espace.getIdEspace());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        tableEspace.getItems().remove(espace);
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
                        espace = getTableView().getItems().get(getIndex());
                        // Charger les détails de l'espace dans les champs de texte
                        txtNom.setText(espace.getName());
                        cbEtat.setValue(espace.getEtat());
                        txtCapacite.setText(String.valueOf(espace.getCapacite()));
                        txtDescription.setText(espace.getDescription());
                    });

                    setGraphic(editButton);
                }
            }
        });

        tableEspace.setItems(espaceList);
        cbEtat.getItems().addAll(Espace.Etat.values()); // Ajouter les valeurs de l'enum dans le ComboBox
    }

    @FXML
    void saveChanges() {
        if (validateInputs()) {
            // Mettre à jour l'objet espace avec les nouvelles valeurs
            espace.setName(txtNom.getText());
            espace.setEtat(cbEtat.getValue());
            espace.setCapacite(Integer.parseInt(txtCapacite.getText()));
            espace.setDescription(txtDescription.getText());

            // Mettre à jour l'objet espace dans la base de données
            try {
                serviceEspace.modifier(espace);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Rafraîchir le tableau
            tableEspace.refresh();
        }
    }

    @FXML
    void ajouterEspace() {
        if (validateInputs()) {

            // Créer un nouvel objet Espace avec les valeurs des champs de texte
            Espace newEspace = new Espace();
            newEspace.setName(txtNom.getText());
            newEspace.setEtat(cbEtat.getValue());
            newEspace.setCapacite(Integer.parseInt(txtCapacite.getText()));
            newEspace.setDescription(txtDescription.getText());

            // Ajouter le nouvel objet Espace à la base de données
            try {
                serviceEspace.ajouter(newEspace);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Ajouter le nouvel objet Espace au tableau
            tableEspace.getItems().add(newEspace);

            // Rafraîchir le tableau
            tableEspace.refresh();
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

        // Valider le champ Capacité
        String capacite = txtCapacite.getText().trim();
        if (capacite.isEmpty() || !capacite.matches("\\d+") || Integer.parseInt(capacite) <= 0 || Integer.parseInt(capacite) > 50) {
            // Afficher un message d'erreur
            lblCapaciteError.setText("La capacité doit être un entier positif et ne doit pas dépasser 50.");
            return false;
        } else {
            lblCapaciteError.setText(""); // Effacer le message d'erreur précédent
        }

        // Valider le champ Description
        String description = txtDescription.getText().trim();
        if (description.isEmpty() || description.length() > 100) {
            // Afficher un message d'erreur
            lblDescriptionError.setText("La description ne peut pas être vide et ne doit pas dépasser 100 caractères.");
            return false;
        } else {
            lblDescriptionError.setText(""); // Effacer le message d'erreur précédent
        }

        return true;
    }
}
