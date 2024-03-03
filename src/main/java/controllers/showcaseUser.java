package controllers;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import services.ServiceUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class showcaseUser {
    private final ServiceUser su = new ServiceUser();
    @FXML
    private Button boutonPDF;

    @FXML
    private ListView<User> listeUsers;

    @FXML
    private Button boutonGerer;
    @FXML
    private Button boutonGererEspace;
    @FXML
    private TextField txtRechercheNom;
    private ObservableList<User> eventsObservableList;



    private void attachContextMenuToListView(ListView<User> listView) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) { // Right-click detected
                User selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    int userid = selectedItem.getId();

                    // Create the context menu
                    ContextMenu contextMenu = new ContextMenu();

                    // Create menu items
                    //MenuItem updateMenuItem = new MenuItem("Update");
                    MenuItem deleteMenuItem = new MenuItem("Delete");


                    // Handle delete menu item
                    deleteMenuItem.setOnAction(deleteEvent -> {
                        try {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation ");
                            alert.setHeaderText("Look, a Confirmation Dialog");
                            alert.setContentText("Are you ok with deleting the user?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                su.supprimer(userid);
                                listView.getItems().remove(selectedItem);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Delete message with ID: " + userid);
                    });

                    // Add menu items to context menu
                    contextMenu.getItems().addAll(deleteMenuItem);

                    // Show context menu at the mouse location
                    contextMenu.show(listView, event.getScreenX(), event.getScreenY());
                }
            }
        });

    }

    @FXML
    public void initialize() throws SQLException {
        ServiceUser serviceUser = new ServiceUser();
        Set<User> users = serviceUser.getAll();
        System.out.println(users);
        ObservableList<User> eventsObservableList = FXCollections.observableArrayList(users);
        listeUsers.setItems(eventsObservableList);

        // Customize how items are rendered in the ListView
        listeUsers.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Set the text to display for each list item
                    setText(item.getNom() + " - " + item.getPrenom() + " - " + item.getMail() + " - " + item.getNumber() + " - " + item.getPassword() + " - " + item.getRole());
                }
            }
        });
        attachContextMenuToListView(listeUsers);
    }

    public void showcaseUser(ListView<User> listView) {
        try {
            listeUsers = (ListView<User>) su.getAll();
            ObservableList<User> observableList = FXCollections.observableArrayList();
            listView.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleshowcaseuserbuttonclick() {
        try {
            eventsObservableList.addAll(su.getAll());
            listeUsers.setItems(eventsObservableList);
            // Personnalisez la façon dont les éléments sont rendus dans la ListView
            listeUsers.setCellFactory(param -> new ListCell<User>() {
                @Override
                protected void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Définissez le texte à afficher pour chaque élément de la liste
                        setText(item.getNom() + " - " + item.getPrenom()+ " - " + item.getMail()+ " - " + item.getNumber()+ " - " + item.getPassword()+ " - " + item.getRole());
                    }
                }
            });
        } catch (Exception throwables) {

            throwables.printStackTrace();
        }

    }

}
