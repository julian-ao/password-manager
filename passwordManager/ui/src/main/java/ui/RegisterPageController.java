package ui;

import java.io.IOException;

import core.UserSession;
import core.userbuilder.PasswordValidation;
import core.userbuilder.UserBuilder;
import core.userbuilder.UsernameValidation;
import core.database.CSVDatabaseTalker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;


public class RegisterPageController extends PasswordManagerController {

    @FXML
    private TextField usernameTextField, passwordTextField, repeatPasswordTextField;

    @FXML
    private Text visualFeedbackText;

    @FXML
    private PasswordField passwordPasswordField, repeatPasswordPasswordField;
    
    @FXML
    private ImageView eyeImageView1,eyeImageView2;

    // ----- //

    @FXML
    public void initialize() {
        eyeImageView1.setImage(super.eyeOpenImage);
        eyeImageView2.setImage(super.eyeOpenImage);
    }

    @FXML
    private void eyeImageViewClick1() {
        passwordEye(passwordTextField, passwordPasswordField, eyeImageView1);
    }

    @FXML
    private void eyeImageViewClick2() {
        passwordEye(repeatPasswordTextField, repeatPasswordPasswordField, eyeImageView2);
    }
    
    @FXML
    private void onRegisterBackButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "login.fxml");
    }

    @FXML
    private void onCreateUserButtonClick(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (!passwordTextField.isVisible()) {
            password = passwordPasswordField.getText();
        }
        String passwordRepeat = repeatPasswordTextField.getText();
        if (!repeatPasswordTextField.isVisible()) {
            passwordRepeat = repeatPasswordPasswordField.getText();
        }

        // if all fields are filled
        if (username != "" && password != "" && passwordRepeat != "") {

            UserBuilder userBuilder = new UserBuilder(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            userBuilder.setUsername(username);
            userBuilder.setPassword(password);

            UsernameValidation usernameValidation = userBuilder.setUsername(username);
            PasswordValidation passwordValidation = userBuilder.setPassword(password);

            // if nothing wrong with username and password
            if (usernameValidation == UsernameValidation.OK && passwordValidation == PasswordValidation.OK) {

                // if password inputs match
                if (password.equals(passwordRepeat)) {
                    UserSession userSession = UserSession.getInstance();
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
        } else {
            visualFeedbackText.setText("Please fill in all fields");
        }

        visualFeedbackText.setVisible(true);
        setBorderRed(usernameTextField);
        setBorderRed(passwordTextField);
        setBorderRed(repeatPasswordTextField);
        setBorderRed(passwordPasswordField);
        setBorderRed(repeatPasswordPasswordField);
        rotateNode(visualFeedbackText, false);
    }

}
