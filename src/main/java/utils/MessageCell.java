package utils;

import entities.Message;
import javafx.scene.control.ListCell;

public class MessageCell extends ListCell<Message> {
    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getEmetteur().getName() + "\n" + item.getContenu() + "\n" + item.getTimeStamp_envoi());
        }
    }
}
