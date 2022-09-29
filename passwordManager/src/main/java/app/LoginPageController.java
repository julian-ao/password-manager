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

import java.io.IOException;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import app.UserSession;
import app.database.*;

public class LoginPageController extends PasswordManagerController{
    
    @FXML
    private TextField usernameInput, passwordInput;

    @FXML
    private Button loginButton, registerButton;

    


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

                /*
                 * ArrayList<GridPane> passwords = new ArrayList<GridPane>();
                 * passwords.add(passwordComponent("Google", "••••••••••••",
                 * "kennbr@gmail.com"));
                 * passwords.add(passwordComponent("NTNU-bruker", "••••••••••••",
                 * "kennbr@stud.ntnu.no"));
                 * passwords.add(passwordComponent("Figma", "••••••••••••",
                 * "kennbr@gmail.com"));
                 * passwords.add(passwordComponent("Facebook", "••••••••••••",
                 * "email@gmail.com"));
                 * passwords.add(passwordComponent("Instagram", "••••••••••••",
                 * "email@gmail.com"));
                 * 
                 * for (GridPane i : passwords) {
                 * passwordList.getChildren().add(i);
                 * }
                 */
            }
        } else {
            userSession = null;
        }
    }
    
    @FXML
    private void onRegisterButtonClick(ActionEvent event) throws IOException {
        super.switchScene(event, "register.fxml");
    }

}
