package controllers;

import entities.Appartement;
import entities.Facture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.ServiceFacture;
import java.sql.Date;

import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterFacture {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField montantField;

    @FXML
    private TextField NumFacture;

    @FXML
    private Text title;

    @FXML
    private ComboBox<String> typeComboBox;

    private final ServiceFacture PS = new ServiceFacture();
    @FXML
    void Ajouter(ActionEvent event) {
        try {

            LocalDate date = datePicker.getValue();
            if (date == null) {
                throw new IllegalArgumentException("Please select a date.");
            }

            // Vérifier que les autres champs sont remplis
            if (NumFacture.getText().isEmpty()) {
                throw new IllegalArgumentException("NumFacture is null or empty");
            }

            int Numfact = Integer.parseInt(NumFacture.getText());
            String typeString = typeComboBox.getValue();
            Facture.Type type = Facture.Type.valueOf(typeString);
            float montant = Float.parseFloat(montantField.getText());
            String description = descriptionField.getText();
            if (Numfact <= 0) {
                throw new IllegalArgumentException("NumFacture doit être un nombre entier positif.");
            }

            // Vérifier si Montant est un nombre décimal positif
            if (montant <= 0) {
                throw new IllegalArgumentException("Montant doit être un nombre décimal positif.");
            }
            Appartement p = new Appartement();
            // Créer l'objet Facture
            Facture facture = new Facture(Numfact, java.sql.Date.valueOf(date), type, montant, description,p);

            // Appeler la méthode pour ajouter la facture
            PS.ajouter(facture);

            // Afficher une confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Facture added successfully");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Gérer l'erreur pour le format de numéro invalide
            afficherAlerteErreur("Format Error", "Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            // Gérer l'erreur pour la date ou NumFacture nulle
            afficherAlerteErreur("Input Error", e.getMessage());
        } catch (SQLException e) {
            // Gérer l'exception SQL
            afficherAlerteErreur("SQL Exception", e.getMessage());
        } catch (Exception e) {
            // Gérer les autres exceptions
            afficherAlerteErreur("Error", e.getMessage());
        }
    }

   /* @FXML
    void Ajouter(ActionEvent event) {
        try {
            LocalDate date = datePicker.getValue();
            if (date == null) {
                throw new IllegalArgumentException("Please select a date.");
            }

            // Vérifier que les autres champs sont remplis
            if (NumFacture.getText().isEmpty()) {
                throw new IllegalArgumentException("NumFacture is null or empty");
            }
            Appartement p = new Appartement();
            int Numfact = Integer.parseInt(NumFacture.getText());
            String typeString = typeComboBox.getValue();
            Facture.Type type = Facture.Type.valueOf(typeString);
            int numAppartement = Integer.parseInt(NumAppartement.getText());

            float montant = Float.parseFloat(montantField.getText());
            String description = descriptionField.getText();
            // Créer l'objet Facture
            Facture facture = new Facture(Numfact,java.sql.Date.valueOf(date),type,montant,description, p );

            // Appeler la méthode pour ajouter la facture
            PS.ajouter(facture);

            // Afficher une confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Facture added successfully");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Gérer l'erreur pour le format de numéro invalide
            afficherAlerteErreur("Format Error", "Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            // Gérer l'erreur pour la date ou NumFacture nulle
            afficherAlerteErreur("Input Error", e.getMessage());
        } catch (SQLException e) {
            // Gérer l'exception SQL
            afficherAlerteErreur("SQL Exception", e.getMessage());
        } catch (Exception e) {
            // Gérer les autres exceptions
            afficherAlerteErreur("Error", e.getMessage());
        }
    }

*/

  /*  @FXML
    void Ajouter(ActionEvent event) {
            try {
             // Check if the date is null
                LocalDate date = datePicker.getValue();
              if (date == null) {
                    throw new IllegalArgumentException("Please select a date.");
               }

                if (!NumFacture.getText().isEmpty()) {
                    int Numfact = Integer.parseInt(NumFacture.getText());

                    String type = typeComboBox.getValue();
                    float montant = Float.parseFloat(montantField.getText());
                    String description = descriptionField.getText();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(date);
                    // Create the Facture object
                    Facture facture = new Facture(Numfact,  sqlDate, type, montant, description);
                    Appartement appartement = new Appartement();
                    facture.setAppartement(appartement);

                    // Call the method to add the facture
                    PS.ajouter(facture);

                    // Show confirmation to the user
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Facture added successfully");
                    alert.showAndWait();
                } else {
                    throw new IllegalArgumentException("NumFacture is null or empty");
                }
            } catch (NumberFormatException e) {
                // Handle error for invalid number format
                afficherAlerteErreur("Format Error", "Please enter a valid number.");
            } catch (SQLException e) {
                // Handle SQL exception
                afficherAlerteErreur("SQL Exception", e.getMessage());
            } catch (Exception e) {
                // Handle other exceptions
                afficherAlerteErreur("Error", e.getMessage());
            }
        }*/

//  @FXML
//  void Ajouter(ActionEvent event) {
//      try {
//          LocalDate date = datePicker.getValue();
//          if (date == null) {
//              throw new IllegalArgumentException("Please select a date.");
//          }
//
//          // Autres vérifications et traitement...
//          Appartement appartement = new Appartement();
//
//
//          // Create the Facture object
//          Facture facture = new Facture(NumFacture, java.sql.Date.valueOf(date), typeComboBox, montantField, descriptionField,ANumAppartement);
//          facture.setAppartement(appartement);
//          // Call the method to add the facture
//          PS.ajouter(facture);
//
//          // Show confirmation to the user
//          Alert alert = new Alert(Alert.AlertType.INFORMATION);
//          alert.setTitle("Validation");
//          alert.setContentText("Facture added successfully");
//          alert.showAndWait();
//      } catch (IllegalArgumentException e) {
//          // Handle error for null date
//          afficherAlerteErreur("Date Error", e.getMessage());
//      } catch (NumberFormatException e) {
//          // Handle error for invalid number format
//          afficherAlerteErreur("Format Error", "Please enter a valid number.");
//      } catch (SQLException e) {
//          // Handle SQL exception
//          afficherAlerteErreur("SQL Exception", e.getMessage());
//      } catch (Exception e) {
//          // Handle other exceptions
//          afficherAlerteErreur("Error", e.getMessage());
//      }
//  }

    private void afficherAlerteErreur(String error, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
