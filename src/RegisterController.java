import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    TextField username;
    @FXML
    TextField password;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void movetoLogin ( ActionEvent e) throws IOException {
        String Username = username.getText();
        String Password = password.getText();

            /*
                INPUT
                Registering user LOGIC
            */


        //This should execute ONLY IF THE LOGIN CREDENTIALS ARE CORRECT!!!!
        root = FXMLLoader.load( getClass().getResource( "Login.fxml" ));
        stage  = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource( "Login.css" ).toExternalForm());
        stage.setScene( scene);
    }
}
