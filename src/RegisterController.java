import com.fringe.store.UserStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    TextField username;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void movetoLogin(ActionEvent e) throws IOException {
        String Username = username.getText().trim();
        String Email = email.getText().trim();
        String Password = password.getText();

        try {
            new UserStore().register(Username, Email, Password);
            showAlert(Alert.AlertType.INFORMATION, "Registration Complete", "Account created successfully. Please log in.");

            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", ex.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
