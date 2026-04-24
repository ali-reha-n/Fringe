import com.fringe.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatRoomController {

    Book book;

    @FXML
    private Button backbutton;

    @FXML
    private Button sendbutton;

    @FXML
    private TextField chattext;

    @FXML
    private ListView<String> chat;

    public void sendMessage( ActionEvent e){
        String msg = chattext.getText();

        //LOGIC TO STORE msg in file of the respective book.

        if( !msg.isEmpty() ){
            chat.getItems().add( msg);
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void goBack( ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookpage.fxml"));
        Parent root = loader.load();
        BookPageController controller = loader.getController();
        controller.setBook( book);
        Stage stage  = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //ThiS FUNCTION MUST FETCH ALL THE BOOK CHAT AND THEN DISPLAY IT in the highlighted command
    /*
    public void setBook( Book book) {
        this.book=book;
        chat.getItems().setAll(previous chat);
    }
    */
}
