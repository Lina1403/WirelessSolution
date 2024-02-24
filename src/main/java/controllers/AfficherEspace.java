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

public class AfficherEspace {

    @FXML
    private ListView<Espace> listEspace;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtCapacite;

    @FXML
    private TextField txtDescription;

    @FXML
    private ComboBox<String> comboEtat;

    @FXML
    private Label lblTitleError;

    @FXML
    private Label lblDateError;

    @FXML
    private Label lblNbrPersonneError;

    @FXML
    private Label lblDescriptionError;

    private final IService<Espace> serviceEspace = new ServiceEspace();

    @FXML
    void initialize() {
        try {
            chargerListeEspaces();

            // Ajouter un écouteur d'événements pour la sélection dans la liste
            listEspace.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Remplir le formulaire avec les valeurs de l'espace sélectionné
                    remplirFormulaire(newValue);
                }
            });
        } catch (SQLException e) {
            afficherAlerteErreur("Erreur lors du chargement des espaces : " + e.getMessage());
        }
    }

    private void remplirFormulaire(Espace espace) {
        txtNom.setText(espace.getName());
        comboEtat.setValue(espace.getEtat().toString());
        txtCapacite.setText(String.valueOf(espace.getCapacite()));
        txtDescription.setText(espace.getDescription());
    }

    @FXML
    void modifier() {
        Espace espace = listEspace.getSelectionModel().getSelectedItem();
        if (espace != null) {
            // Récupérer les valeurs modifiées dans le formulaire
            String nom = txtNom.getText();
            String etat = comboEtat.getValue();
            int capacite = Integer.parseInt(txtCapacite.getText());
            String description = txtDescription.getText();

            // Mettre à jour les propriétés de l'espace avec les nouvelles valeurs
            espace.setName(nom);
            espace.setEtat(Espace.Etat.valueOf(etat));
            espace.setCapacite(capacite);
            espace.setDescription(description);

            // Appeler la méthode pour mettre à jour l'espace dans la base de données
            try {
                serviceEspace.modifier(espace);
                listEspace.refresh(); // Rafraîchir l'affichage de la liste
                afficherConfirmation("Espace modifié avec succès !");
            } catch (SQLException e) {
                afficherAlerteErreur("Erreur lors de la modification de l'espace : " + e.getMessage());
            }
        } else {
            afficherAlerteErreur("Veuillez sélectionner un espace à modifier.");
        }
    }

    @FXML
    void supprimer() {
        Espace espace = listEspace.getSelectionModel().getSelectedItem();
        if (espace != null) {
            if (afficherConfirmation("Êtes-vous sûr de vouloir supprimer cet espace ?")) {
                try {
                    serviceEspace.supprimer(espace.getIdEspace());
                    listEspace.getItems().remove(espace);
                } catch (SQLException e) {
                    afficherAlerteErreur("Erreur lors de la suppression de l'espace : " + e.getMessage());
                }
            }
        } else {
            afficherAlerteErreur("Veuillez sélectionner un espace à supprimer.");
        }
    }



    private void chargerListeEspaces() throws SQLException {
        Set<Espace> espaces = serviceEspace.getAll();
        ObservableList<Espace> espaceList = FXCollections.observableArrayList(espaces);

        // Afficher la liste dans la ListView
        listEspace.setItems(espaceList);
    }

    private void afficherAlerteErreur(String message) {
        showAlert(Alert.AlertType.ERROR, "Erreur", message);
    }

    private boolean afficherConfirmation(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(message);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private boolean validateInputs() {
        boolean isValid = true;

        // Validation du titre
        if (txtNom.getText().isEmpty()) {
            lblTitleError.setText("Le titre est requis");
            isValid = false;
        } else {
            lblTitleError.setText("");
        }

        // Validation de l'état
        if (comboEtat.getValue() == null) {
            lblDateError.setText("L'état est requis");
            isValid = false;
        } else {
            lblDateError.setText("");
        }

        // Validation de la capacité
        if (txtCapacite.getText().isEmpty() || !txtCapacite.getText().matches("\\d+")) {
            lblNbrPersonneError.setText("Capacité invalide");
            isValid = false;
        } else {
            lblNbrPersonneError.setText("");
        }

        // Validation de la description
        if (txtDescription.getText().isEmpty()) {
            lblDescriptionError.setText("La description est requise");
            isValid = false;
        } else {
            lblDescriptionError.setText("");
        }

        return isValid;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
