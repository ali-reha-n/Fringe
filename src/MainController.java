import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;

import com.fringe.model.Book;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
            books.add( new Book() );

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
                        Name.setText( book.getTitle() );
                        Review.setText( Double.toString(book.getAvgRating()) + "/10" );

                        if( book.getCoverImageUrl() != null){
                            Image i = new Image(getClass().getResource(book.getCoverImageUrl()).toExternalForm());
                            ImageView img = (ImageView) root.lookup("#image");
                            img.setImage(i);
                        }
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
    books.clear();
    //Use books.add() the same way as above to add only filtered books.
        /*
            IMPLEMENT
            SEARCH LOGIC
        */
    }

    public void resetSearch( ActionEvent e) {
        books.clear();

        //
          //INSERT THE SAME LOGIC AS initialize();
        //
    }

    public void openBook( MouseEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookpage.fxml"));
        Parent root = loader.load();
        BookPageController controller = loader.getController();
        controller.setBook( booklist.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) booklist.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
