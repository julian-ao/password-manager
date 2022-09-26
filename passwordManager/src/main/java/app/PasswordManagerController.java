package app;

import app.database.CSVDatabaseTalker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PasswordManagerController {

    UserSession userSession;

    @FXML
    private TextField usernameInput, passwordInput, regUsername, regPassword, regPasswordRepeat;
    @FXML
    private Button loginButton, registerButton, registerBackButton;
    @FXML
    private Pane loginPage, passwordListPage, registerPage;
    @FXML
    private FlowPane passwordList;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize() {
        System.out.println("PasswordManagerController initialized");
    }

    private GridPane passwordComponent(String username, String password, String email) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefHeight(90);
        gridPane.setPrefWidth(580);

        Text usernameText = new Text(username);
        usernameText.setStyle("-fx-font: 40 system;");
        usernameText.setWrappingWidth(290);

        Text passwordText = new Text(password);
        passwordText.setStyle("-fx-font: 40 system;-fx-text-alignment: right;");
        passwordText.setWrappingWidth(290);

        Text emailText = new Text(email);
        emailText.setStyle("-fx-font: 25 system;");

        gridPane.add(usernameText, 0, 0);
        gridPane.add(passwordText, 1, 1);
        gridPane.add(emailText, 0, 1);

        return gridPane;
    }

    @FXML
    private void onLoginButtonClick(ActionEvent event) throws IOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (username != "" && password != "") {
            System.out.println("username: " + username + " password: " + password);
            userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            if (userSession.login(username, password)) {
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
        switchScene(event, "register.fxml");
    }

    @FXML
    private void onRegisterBackButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "login.fxml");
    }

    @FXML
    private void onCreateUserButtonClick(ActionEvent event) throws IOException {
        if (!regPassword.getText().isEmpty() && regPassword.getText().equals(regPasswordRepeat.getText())) {
            userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            if (userSession.registerUser(regUsername.getText(), regPassword.getText())) {
                System.out.println("User created");
                System.out.println("Switching scene");
                switchScene(event, "login.fxml");
            }
        }
    }

    public void switchScene(ActionEvent event, String sceneName) throws IOException {
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        String title = sceneName.substring(0, sceneName.length() - 5);
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        stage.setTitle("Password Manager | " + title);
        stage.show();
    }
}