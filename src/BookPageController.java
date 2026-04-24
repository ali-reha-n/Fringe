import com.fringe.model.Book;
import com.fringe.model.ContentWarning;
import com.fringe.model.Quote;
import com.fringe.model.Review;
import com.fringe.store.ContentWarningStore;
import com.fringe.store.QuoteStore;
import com.fringe.store.ReviewStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookPageController {
    Book book;
    @FXML
    private Label title;
    @FXML
    private Label review;
    @FXML
    private Label author;
    @FXML
    private Label authorname;
    @FXML
    private Label genre;
    @FXML
    private Label description;
    @FXML
    private Label isbn;
    @FXML
    private Label published;
    @FXML
    private Label warning;

    @FXML
    private ImageView image;

    @FXML
    private ListView<String> quoteslist;
    @FXML
    private ListView<String> reviewlist;

    @FXML
    private TextField quotetext;
    @FXML
    private TextField reviewtext;

    Parent root;
    Stage stage;
    Scene scene;
    private final ReviewStore reviewStore = new ReviewStore();
    private final QuoteStore quoteStore = new QuoteStore();
    private final ContentWarningStore contentWarningStore = new ContentWarningStore();

    public void setBook(Book b) {
        this.book = b;
        if (b == null) {
            return;
        }

        title.setText(book.getTitle());
        review.setText(String.format("Rating: %.1f/5", book.getAvgRating()));
        authorname.setText(book.getAuthor());
        genre.setText("Genre: " + book.getGenre());
        description.setText(book.getDescription() == null ? "No description available." : book.getDescription());
        isbn.setText("ISBN: " + (book.getIsbn() == null ? "N/A" : book.getIsbn()));
        published.setText("Published: " + book.getPublishedYear());
        warning.setText(buildWarningsText(book.getId()));

        refreshQuotes();
        refreshReviews();

        if (book.getCoverImageUrl() != null) {
            URL imageUrl = getClass().getResource(book.getCoverImageUrl());
            if (imageUrl != null) {
                image.setImage(new Image(imageUrl.toExternalForm()));
            } else {
                image.setImage(null);
            }
        } else {
            image.setImage(null);
        }
    }

    public void addReview(ActionEvent e) {
        if (book == null || Session.currentUser == null) {
            showAlert("Review Failed", "You must be logged in to post a review.");
            return;
        }

        String r = reviewtext.getText().trim();

        if (!r.isEmpty()) {
            try {
                reviewStore.addReview(Session.currentUser.getId(), book.getId(), r);
                reviewtext.clear();
                refreshReviews();
            } catch (IllegalArgumentException ex) {
                showAlert("Review Failed", ex.getMessage());
            }
        }
    }

    public void addQuote(ActionEvent e) {
        if (book == null || Session.currentUser == null) {
            showAlert("Quote Failed", "You must be logged in to post a quote.");
            return;
        }

        String q = quotetext.getText().trim();

        if (!q.isEmpty()) {
            try {
                quoteStore.postQuote(Session.currentUser.getId(), book.getId(), q, null);
                quotetext.clear();
                refreshQuotes();
            } catch (IllegalArgumentException ex) {
                showAlert("Quote Failed", ex.getMessage());
            }
        }
    }

    public void gotoChat(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatroom.fxml"));
        root = loader.load();
        ChatRoomController controller = loader.getController();
        controller.setBook(book);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goback(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void refreshQuotes() {
        List<String> quoteTexts = new ArrayList<>();
        for (Quote quote : quoteStore.getByBookId(book.getId())) {
            quoteTexts.add(quote.getText());
        }
        quoteslist.getItems().setAll(quoteTexts);
    }

    private void refreshReviews() {
        List<String> reviewTexts = new ArrayList<>();
        for (Review storedReview : reviewStore.getByBookId(book.getId())) {
            reviewTexts.add(storedReview.getContent());
        }
        reviewlist.getItems().setAll(reviewTexts);
    }

    private String buildWarningsText(int bookId) {
        List<ContentWarning> warnings = contentWarningStore.getByBookId(bookId);
        if (warnings.isEmpty()) {
            return "Warning: None";
        }

        List<String> warningTexts = new ArrayList<>();
        for (ContentWarning contentWarning : warnings) {
            warningTexts.add(contentWarning.getWarningType());
        }
        return "Warning: " + String.join(", ", warningTexts);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
