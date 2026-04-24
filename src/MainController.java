import com.fringe.model.Book;
import com.fringe.store.BookStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController {
    @FXML
    private TextField searchbar;
    @FXML
    ListView<Book> booklist;

    private final ObservableList<Book> books = FXCollections.observableArrayList();
    private final BookStore bookStore = new BookStore();

    @FXML
    void initialize() {
        booklist.setItems(books);
        reloadBooks();

        booklist.setCellFactory(list -> new ListCell<>() {

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);

                if (empty || book == null) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("book.fxml"));
                        Parent root = loader.load();
                        Label Name = (Label) root.lookup("#name");
                        Label Review = (Label) root.lookup("#rating");
                        Name.setText(book.getTitle());
                        Review.setText(String.format("Rating: %.1f/5", book.getAvgRating()));

                        ImageView img = (ImageView) root.lookup("#image");
                        if (book.getCoverImageUrl() != null) {
                            URL imageUrl = getClass().getResource(book.getCoverImageUrl());
                            if (imageUrl != null) {
                                img.setImage(new Image(imageUrl.toExternalForm()));
                            } else {
                                img.setImage(null);
                            }
                        } else {
                            img.setImage(null);
                        }

                        setGraphic(root);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    public void search(ActionEvent e) {
        String search = searchbar.getText().trim();
        if (search.isEmpty()) {
            reloadBooks();
            return;
        }

        books.setAll(bookStore.searchAll(search));
    }

    public void resetSearch(ActionEvent e) {
        searchbar.clear();
        reloadBooks();
    }

    public void openBook(MouseEvent e) throws IOException {
        Book selectedBook = booklist.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookpage.fxml"));
        Parent root = loader.load();
        BookPageController controller = loader.getController();
        controller.setBook(selectedBook);
        Stage stage = (Stage) booklist.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void reloadBooks() {
        books.setAll(bookStore.getAll());
    }
}
