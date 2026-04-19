
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application{


    public static void main( String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource( "login.fxml" ));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource( "Login.css" ).toExternalForm());
        stage.setScene( scene );
        stage.show();
    }
}
