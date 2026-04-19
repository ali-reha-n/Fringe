import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MainController {
    @FXML
    private TextField searchbar;
    @FXML
    ListView<Book> booklist;

    //Auto updating list of books
    ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    void initialize() {

            /*
                ADD BOOKS TO THE "books" array here
            */
            books.add( new Book( "Treasure Island" , 9.99));

        booklist.setItems(books);

        booklist.setCellFactory(list -> new ListCell<>() {

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);

                if (empty || book == null) {
                    setGraphic(null);
                } else {
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("book.fxml"));

                        Parent root = loader.load();
                        Label Name = (Label) root.lookup("#name");
                        Label Review = (Label) root.lookup("#rating");
                        Name.setText( book.name );
                        Review.setText( Double.toString(book.rating) + "/10" );
                        setGraphic(root);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
        public void search( ActionEvent e){
        String search = searchbar.getText();
        /*
            IMPLEMENT
            SEARCH LOGIC
        */
        }
}
