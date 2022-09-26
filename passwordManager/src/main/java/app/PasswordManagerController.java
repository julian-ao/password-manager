package app;

import app.database.CSVDatabaseTalker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PasswordManagerController {

    @FXML
    private TextField usernameInput, passwordInput, regUsername, regPassword, regPasswordRepeat;
    @FXML
    private Button loginButton, registerButton, addPasswordButton;
    @FXML
    private Pane loginPage, passwordListPage, registerPage;
    
    private UserSession userSession;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // LOGIN PAGE METHODS

    @FXML
    private void onLoginButtonClick(ActionEvent event) throws IOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (username != "" && password != "") {
            userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            if (userSession.login(username, password)) {
                switchScene(event, "passwords.fxml");
            } else {
                userSession = null;
            }
        }
    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "register.fxml");
    }

    // REGISTER PAGE METHODS
    
    @FXML
    private void onRegisterBackButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "login.fxml");
    }

    @FXML
    private void onCreateUserButtonClick(ActionEvent event) throws IOException {
        if (!regPassword.getText().isEmpty() && regPassword.getText().equals(regPasswordRepeat.getText())) {
            userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            if (userSession.registerUser(regUsername.getText(), regPassword.getText())) {
                switchScene(event, "login.fxml");
            }
        }
    }

    // PASSWORDS PAGE METHODS

    @FXML
    private void onLogOutButtonClick(ActionEvent event) throws IOException {
        // log out?
        // userSession.logOut()?
        switchScene(event, "login.fxml");
    }

    @FXML
    private void onAddPasswordButtonClick(ActionEvent event) throws IOException {
        // something happens
    }

    // GENERAL METHODS

    private void switchScene(ActionEvent event, String sceneName) throws IOException {
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        String title = sceneName.substring(0, sceneName.length() - 5);
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        stage.setTitle("Password Manager | " + title);
        stage.show();
    }
}