package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;

import java.io.IOException;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import app.UserSession;
import app.database.*;

public class LoginPageController extends PasswordManagerController {

    @FXML
    private TextField usernameInput, passwordInput;

    @FXML
    private Button loginButton, registerButton;

    @FXML
    private Text visualFeedbackText;

    @FXML
    private void onLoginButtonClick(ActionEvent event) throws IOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        UserSession userSession;

        if (username != "" && password != "") {
            userSession = UserSession.getInstance();
            if (userSession.login(username, password)) {
                ((Stage)usernameInput.getScene().getWindow()).setUserData(userSession);
                switchScene(event, "passwords.fxml");
            }
            visualFeedbackText.setText("Wrong username or password");
        } else {
            visualFeedbackText.setText("Please fill in all fields");
        }

        visualFeedbackText.setVisible(true);
        usernameInput.setStyle("-fx-border-color: #FE8383");
        passwordInput.setStyle("-fx-border-color: #FE8383");
        super.rotateNode(visualFeedbackText, false);
        userSession = null;
    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) throws IOException {
        super.switchScene(event, "register.fxml");
    }
}
