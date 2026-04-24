import com.fringe.model.Book;
import com.fringe.model.ChatRoom;
import com.fringe.model.Message;
import com.fringe.model.User;
import com.fringe.store.ChatRoomStore;
import com.fringe.store.MessageStore;
import com.fringe.store.UserStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomController {

    Book book;
    private ChatRoom room;
    private final ChatRoomStore chatRoomStore = new ChatRoomStore();
    private final MessageStore messageStore = new MessageStore();
    private final UserStore userStore = new UserStore();

    @FXML
    private Button backbutton;

    @FXML
    private Button sendbutton;

    @FXML
    private TextField chattext;

    @FXML
    private ListView<String> chat;

    public void sendMessage(ActionEvent e) {
        if (book == null || Session.currentUser == null) {
            showAlert("Chat Unavailable", "You must be logged in to send messages.");
            return;
        }

        String msg = chattext.getText().trim();

        if (!msg.isEmpty()) {
            if (room == null) {
                room = chatRoomStore.getOrCreateRoomForBook(book.getId(), book.getTitle());
            }
            messageStore.sendMessage(room.getId(), Session.currentUser.getId(), msg);
            chattext.clear();
            loadMessages();
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void goBack(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookpage.fxml"));
        Parent root = loader.load();
        BookPageController controller = loader.getController();
        controller.setBook(book);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setBook(Book book) {
        this.book = book;
        if (book == null) {
            room = null;
            chat.getItems().clear();
            return;
        }

        room = chatRoomStore.getOrCreateRoomForBook(book.getId(), book.getTitle());
        loadMessages();
    }

    private void loadMessages() {
        if (room == null) {
            chat.getItems().clear();
            return;
        }

        List<String> messages = new ArrayList<>();
        for (Message message : messageStore.getByRoomId(room.getId())) {
            String senderName = userStore.findById(message.getSenderId())
                    .map(User::getUsername)
                    .orElse("User " + message.getSenderId());
            messages.add(senderName + ": " + message.getContent());
        }
        chat.getItems().setAll(messages);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
