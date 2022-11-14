package ui;

import client.RestTalker;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

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
    eyeImageView.setImage(eyeOpenImage);
    visualFeedbackText.setFill(Color.valueOf(lightRed));
  }

  @FXML
  private void eyeImageViewClick() {
    passwordEye(passwordTextField, passwordPasswordField, eyeImageView);
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

    String username = usernameTextField.getText();
    String password = passwordTextField.getText();
    if (!passwordTextField.isVisible()) {
      password = passwordPasswordField.getText();
    }

    if (!username.equals("") && !password.equals("")) {

      String data = restTalker.login(username, password);
      if (data.equals("Success")) {
        login(username, password, "passwords.fxml", event);
      }
      visualFeedbackText.setText("Wrong username or password");
    } else {
      visualFeedbackText.setText("Please fill in all fields");
    }

    visualFeedbackText.setVisible(true);
    setBorder(usernameTextField, lightRed);
    setBorder(passwordTextField, lightRed);
    setBorder(passwordPasswordField, lightRed);
    rotateNode(visualFeedbackText, false);
  }

  private void login(String username, String password, String sceneName, ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
    root = loader.load();

    PasswordPageController contr = loader.getController();
    contr.setUserData(username, password);

    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    String title = sceneName.substring(0, sceneName.length() - 5);
    title = title.substring(0, 1).toUpperCase() + title.substring(1);
    stage.setTitle("Password Manager | " + title);
    stage.show();
  }

  @FXML
  private void onRegisterButtonClick(ActionEvent event) throws IOException {
    switchScene(event, "register.fxml");
  }
}
