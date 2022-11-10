package ui;

import client.RestTalker;
import java.io.IOException;
import java.util.HashMap;
 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * FXML Controller class for the login page.
 */
public class LoginPageController extends PasswordManagerController {

  @FXML
  private TextField usernameTextField;

  @FXML
  private TextField passwordTextField;

  @FXML
  private Button loginButton;

  @FXML
  private Button registerButton;

  @FXML
  private Text visualFeedbackText;

  @FXML
  private PasswordField passwordPasswordField;

  @FXML
  private ImageView eyeImageView;

  /**
   * initialize sets the eye image to the open eye image.
   */
  @FXML
  public void initialize() {
    eyeImageView.setImage(super.eyeOpenImage);
  }

  @FXML
  private void eyeImageViewClick() {
    super.passwordEye(passwordTextField, passwordPasswordField, eyeImageView);
  }

  /**
   * onLoginButtonClick handles the loginbutton click, if the fields are filled in
   * it passes the username and password to the restserver and if the login is
   * successful it opens the mainpage and sends the profile to the mainpage.
   * login, if it can't login or the password and/or username
   * is not filled in; The function shows the appropriate response to the user.
   * 
   * @param event ActionEvent object to be used in the switchScene method.
   * @throws IOException if there is a problem opening the new scene
   * 
   */
  @FXML
  private void onLoginButtonClick(ActionEvent event) throws IOException {

    RestTalker restTalker = new RestTalker();
    System.out.println(restTalker.test1());
    System.out.println(restTalker.test3("postTest"));
    System.out.println(restTalker.test4("TestID"));
    System.out.println(restTalker.login("Admin", "Admin1!"));

    String username = usernameTextField.getText();
    String password = passwordTextField.getText();
    if (!passwordTextField.isVisible()) {
      password = passwordPasswordField.getText();
    }

    if (!username.equals("") && !password.equals("")) {

      String data = restTalker.login(username, password);
      if (!data.equals("Invalid")) {
        switchScene(event, "passwords.fxml", data);
      }
      visualFeedbackText.setText("Wrong username or password");
    } else {
      visualFeedbackText.setText("Please fill in all fields");
    }

    visualFeedbackText.setVisible(true);
    usernameTextField.setStyle("-fx-border-color: #FE8383");
    passwordTextField.setStyle("-fx-border-color: #FE8383");
    passwordPasswordField.setStyle("-fx-border-color: #FE8383");
    super.rotateNode(visualFeedbackText, false);
  }

  @FXML
  private void onRegisterButtonClick(ActionEvent event) throws IOException {
    super.switchScene(event, "register.fxml", "Register");
  }
}
