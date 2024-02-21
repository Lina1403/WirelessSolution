package controllers;

import entities.Voiture;
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
import services.ServiceVoiture;
import java.sql.SQLException;
import java.util.Set;

public class AfficherVoiture {

    @FXML
    private TableView<Voiture> tableVoiture;

    @FXML
    private TableColumn<Voiture, String> colNom;

    @FXML
    private TableColumn<Voiture, String> colEmail;

    @FXML
    private TableColumn<Voiture, String> colNumSerie;

    @FXML
    private TableColumn<Voiture, String> colMarque;

    @FXML
    private TableColumn<Voiture, String> colCouleur;

    @FXML
    private TableColumn<Voiture, Integer> colNumPlace;

    @FXML
    private TableColumn<Voiture, Void> colDelete;

    @FXML
    private TableColumn<Voiture, Void> colEdit;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNumSerie;

    @FXML
    private TextField txtMarque;

    @FXML
    private TextField txtCouleur;

    @FXML
    private TextField txtNumPlace;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;

    private Voiture voiture;

    private final IService<Voiture> serviceVoiture = new ServiceVoiture();

    @FXML
    void initialize() throws SQLException  {
        Set<Voiture> voitures = serviceVoiture.getAll();
        ObservableList<Voiture> voitureList = FXCollections.observableArrayList(voitures);

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNumSerie.setCellValueFactory(new PropertyValueFactory<>("num_serie"));
        colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        colCouleur.setCellValueFactory(new PropertyValueFactory<>("couleur"));
        colNumPlace.setCellValueFactory(new PropertyValueFactory<>("numPlace"));

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
                        Voiture voiture = getTableView().getItems().get(getIndex());
                        // Supprimer l'élément
                        try {
                            serviceVoiture.supprimer(voiture.getId_voiture());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        tableVoiture.getItems().remove(voiture);
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
                        voiture = getTableView().getItems().get(getIndex());
                        // Charger les détails de la voiture dans les champs de texte
                        txtNom.setText(voiture.getNom());
                        txtEmail.setText(voiture.getEmail());
                        txtNumSerie.setText(voiture.getNumSerie());
                        txtMarque.setText(voiture.getMarque());
                        txtCouleur.setText(voiture.getCouleur());
                        txtNumPlace.setText(String.valueOf(voiture.getNumPlace()));
                    });

                    setGraphic(editButton);
                }
            }
        });

        tableVoiture.setItems(voitureList);
    }

    @FXML
    void saveChanges() {
        // Mettre à jour l'objet voiture avec les nouvelles valeurs
        voiture.setNom(txtNom.getText());
        voiture.setEmail(txtEmail.getText());
        voiture.setNumSerie(txtNumSerie.getText());
        voiture.setMarque(txtMarque.getText());
        voiture.setCouleur(txtCouleur.getText());
        voiture.setNumPlace(Integer.parseInt(txtNumPlace.getText()));

        // Mettre à jour l'objet voiture dans la base de données
        try {
            serviceVoiture.modifier(voiture);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Rafraîchir le tableau
        tableVoiture.refresh();
    }

    @FXML
    void ajouterVoiture() {
        // Créer un nouvel objet Voiture avec les valeurs des champs de texte
        Voiture newVoiture = new Voiture();
        newVoiture.setNom(txtNom.getText());
        newVoiture.setEmail(txtEmail.getText());
        newVoiture.setNumSerie(txtNumSerie.getText());
        newVoiture.setMarque(txtMarque.getText());
        newVoiture.setCouleur(txtCouleur.getText());
        newVoiture.setNumPlace(Integer.parseInt(txtNumPlace.getText()));

        // Ajouter le nouvel objet Voiture à la base de données
        try {
            serviceVoiture.ajouter(newVoiture);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Ajouter le nouvel objet Voiture au tableau
        tableVoiture.getItems().add(newVoiture);

        // Rafraîchir le tableau
        tableVoiture.refresh();
    }
}
