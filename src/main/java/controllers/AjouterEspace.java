package controllers;

import entities.Espace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.IService;
import services.ServiceEspace;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class AjouterEspace {

    @FXML
    private ListView<Espace> listEspace;

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
    private Label lblNomError, lblCapaciteError, lblDescriptionError, lblEtatError;

    private Espace espace;

    private final IService<Espace> serviceEspace = new ServiceEspace();

    @FXML
    void initialize() {
        cbEtat.getItems().addAll(Espace.Etat.LIBRE, Espace.Etat.RESERVE);

        // Définir l'état par défaut comme "Libre"
        cbEtat.setValue(Espace.Etat.LIBRE);
    }

    private void remplirChamps(Espace espace) {
        txtNom.setText(espace.getName());
        cbEtat.setValue(espace.getEtat());
        txtCapacite.setText(String.valueOf(espace.getCapacite()));
        txtDescription.setText(espace.getDescription());
    }

    @FXML
    void saveChanges() {
        if (espace != null) {
            if (validerChamps()) {
                espace.setName(txtNom.getText());
                espace.setEtat(cbEtat.getValue());
                espace.setCapacite(Integer.parseInt(txtCapacite.getText()));
                espace.setDescription(txtDescription.getText());

                try {
                    serviceEspace.modifier(espace);
                    listEspace.refresh();
                } catch (SQLException e) {
                    afficherErreur("Erreur lors de la modification de l'espace : " + e.getMessage());
                }
            }
        } else {
            afficherErreur("Veuillez sélectionner un espace à modifier.");
        }
    }

    @FXML
    void ajouterEspace() {
        if (validerChamps()) {
            Espace nouvelEspace = new Espace(
                    txtNom.getText(),
                    cbEtat.getValue(),
                    Integer.parseInt(txtCapacite.getText()),
                    txtDescription.getText()
            );


            try {
                serviceEspace.ajouter(nouvelEspace);
                clearFields(); // Efface les champs après l'ajout
            } catch (SQLException e) {
                afficherErreur("Erreur lors de l'ajout de l'espace : " + e.getMessage());
            }
        }
        }



    private void clearFields() {
        txtNom.clear();
        cbEtat.getSelectionModel().clearSelection();
        txtCapacite.clear();
        txtDescription.clear();
    }

    private boolean validerChamps() {
        boolean valide = true;

        if (txtNom.getText().isEmpty() || !txtNom.getText().matches("[a-zA-Z]{1,20}")) {
            afficherErreurChamp(lblNomError, "Le nom doit contenir uniquement des lettres et avoir une longueur maximale de 20 caractères.");
            valide = false;
        } else {
            lblNomError.setText("");
        }

        if (cbEtat.getValue() == null) {
            afficherErreurChamp(lblEtatError, "Veuillez sélectionner un état.");
            valide = false;
        } else {
            lblEtatError.setText("");
        }

        if (txtCapacite.getText().isEmpty() || !txtCapacite.getText().matches("\\d+") || Integer.parseInt(txtCapacite.getText()) <= 0 || Integer.parseInt(txtCapacite.getText()) > 50) {
            afficherErreurChamp(lblCapaciteError, "La capacité doit être un entier positif et ne doit pas dépasser 50.");
            valide = false;
        } else {
            lblCapaciteError.setText("");
        }

        if (txtDescription.getText().isEmpty() || txtDescription.getText().length() > 1000) {
            afficherErreurChamp(lblDescriptionError, "La description ne peut pas être vide et ne doit pas dépasser 1000 caractères.");
            valide = false;
        } else {
            lblDescriptionError.setText("");
        }

        return valide;
    }

    private void afficherErreurChamp(Label champErreur, String message) {
        champErreur.setText(message);
    }

    private void chargerListeEspaces() throws SQLException {
        Set<Espace> espaces = serviceEspace.getAll();
        ObservableList<Espace> espaceList = FXCollections.observableArrayList(espaces);
        listEspace.setItems(espaceList);
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
