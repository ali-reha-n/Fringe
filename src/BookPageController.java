import com.fringe.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class BookPageController {
    Book book;
    @FXML
    private Label title;
    @FXML private Label review;
    @FXML private Label author;
    @FXML private Label authorname;
    @FXML private Label genre;
    @FXML private Label description;
    @FXML private Label isbn;
    @FXML private Label published;
    @FXML private Label warning;

    @FXML private ImageView image;

    @FXML private ListView<String> quoteslist;
    @FXML private ListView<String> reviewlist;

    @FXML private TextField quotetext;
    @FXML private TextField reviewtext;

    Parent root;
    Stage stage;
    Scene scene;

    public void setBook( Book b){
        this.book = b;

        //SET ALL THE BOOK ATRIBUTES IN THIS FUNCTION
        /*Adding quotes
        //quoteslist.getItems().addAll( An array of strings having quotes here);
        //*adding reviews
        reviewlist.getItems().addAll( array string);
        Adding data to all the labels
        Labelname.setText( String );

        if (  **string representing coverimage url** != null) {
        Image img = new Image(getClass()
                .getResource(book.getCoverImageUrl())
                .toExternalForm());
        image.setImage(img);
    }

        */
    }

    public void addReview( ActionEvent e){
        String r = reviewtext.getText().trim();

        if (!r.isEmpty()) {
            reviewlist.getItems().add(r);
            reviewtext.clear();

            // ADD REVIEW TO BOOK File HERE
        }
    }

    public void addQuote( ActionEvent e ){
        String q = quotetext.getText().trim();

        if (!q.isEmpty()) {
            quoteslist.getItems().add(q);
            quotetext.clear();

            //STORE THE QUOTE TO BOOK HERE.
        }
    }

    public void gotoChat( ActionEvent e) throws IOException{
        root = FXMLLoader.load( getClass().getResource( "chatroom.fxml" ));
        stage  = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene( scene);
    }

    public void goback( ActionEvent e) throws IOException{
        root = FXMLLoader.load( getClass().getResource( "main.fxml" ));
        stage  = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource( "main.css" ).toExternalForm());
        stage.setScene( scene);
    }

}
