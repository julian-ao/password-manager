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
import javafx.scene.text.Text;


public class RegisterPageController extends PasswordManagerController {

    @FXML
    private TextField usernameInput, passwordInput, passwordRepeatInput;

    @FXML
    private Text visualFeedbackText;


    @FXML
    private void onRegisterBackButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "login.fxml");
    }

    @FXML
    private void onCreateUserButtonClick(ActionEvent event) throws IOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String passwordRepeat = passwordRepeatInput.getText();

        if (username != "" && password != "" && passwordRepeat != "") {

            UserBuilder userBuilder = new UserBuilder(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            userBuilder.setUsername(username);
            userBuilder.setPassword(password);

            UsernameValidation usernameValidation = userBuilder.setUsername(username);
            PasswordValidation passwordValidation = userBuilder.setPassword(password);

            if (usernameValidation == UsernameValidation.OK && passwordValidation == PasswordValidation.OK) {
                if (password.equals(passwordRepeat)) {
                    UserSession userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
                    System.out.println("registering user");
                    if (userSession.registerUser(username, password)) {
                        switchScene(event, "login.fxml");
                    }
                } else {
                    visualFeedbackText.setText("Passwords do not match");
                }
            } else {
                if (usernameValidation == UsernameValidation.alreadyTaken) {
                    visualFeedbackText.setText("Username already taken");
                } else if (usernameValidation != UsernameValidation.OK) {
                    visualFeedbackText.setText("Username must be between 3 and 30 characters long and contain only letters and numbers");
                } else {
                    visualFeedbackText.setText("Password must be between 6 and 30 characters long and contain at least one lowercase letter, one uppercase letter, one number and one special character");
                }
            }
        }
        else {
            visualFeedbackText.setText("Please fill in all fields");
        }

        visualFeedbackText.setVisible(true);
        setBorderRed(usernameInput);
        setBorderRed(passwordInput);
        setBorderRed(passwordRepeatInput);
        rotateNode(visualFeedbackText, false);
    }

}
