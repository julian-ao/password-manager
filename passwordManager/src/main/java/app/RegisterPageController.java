package app;

import app.userbuilder.*;

import java.io.IOException;

import app.database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class RegisterPageController extends PasswordManagerController {
    

    @FXML
    private TextField username, password, passwordRepeat;



    @FXML
    private void onRegisterBackButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "login.fxml");
    }

    @FXML
    private void onCreateUserButtonClick(ActionEvent event) throws IOException {
        if (!password.getText().isEmpty() && password.getText().equals(passwordRepeat.getText())) {
            UserSession userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            if (userSession.registerUser(username.getText(), password.getText())) {
                switchScene(event, "login.fxml");
            }
        }
    }

}
