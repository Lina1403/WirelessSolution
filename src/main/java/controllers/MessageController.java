package controllers;
import entities.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import services.MessageService;
import utils.MessageCell;

import java.sql.SQLException;

public class MessageController {
    @FXML
    private Button attachButton;

    @FXML
    private TextField messageField;

    @FXML
    private ListView<Message> messageList;

    @FXML
    private Button sendButton;

    public static int discuId ;

    public void initialize() throws SQLException {
        MessageService ms = new MessageService();
        ObservableList<Message> messages = FXCollections.observableList(ms.afficherByDiscussionId(discuId));
        messageList.setItems(messages);
        messageList.setCellFactory(param -> new MessageCell());
    }
}
