import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField username;
    @FXML
    TextField password;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void moveToMainPage( ActionEvent event) throws IOException {
            String Username = username.getText();
            String Password = password.getText();

            /*
                INPUT
                PASSWORD VERIFICATION LOGIC
            */


            //This should execute ONLY IF THE LOGIN CREDENTIALS ARE CORRECT!!!!
            root = FXMLLoader.load( getClass().getResource( "main.fxml" ));
            stage  = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource( "main.css" ).toExternalForm());
            stage.setScene( scene);
    }

    public void register( ActionEvent e) throws IOException{
        root = FXMLLoader.load( getClass().getResource( "Register.fxml" ));
        stage  = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource( "Register.css" ).toExternalForm());
        stage.setScene( scene);
    }
}
