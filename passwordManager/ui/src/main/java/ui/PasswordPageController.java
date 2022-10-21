package ui;

import core.UserSession;
import core.Password;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PasswordPageController extends PasswordManagerController {

  @FXML
  private FlowPane passwordList;

  @FXML
  private Pane passwordListPage, addLoginOverlay;

  @FXML
  private Button addLoginCloseButton;

  @FXML
  private Text signedInAsText, passwordStrengthText;

  @FXML
  private TextField addLoginTitleTextField, addLoginUsernameTextField, addLoginPasswordTextField;

  @FXML
  private Rectangle addLoginBlur;

  private UserSession userSession;

  private Password passwordObj;

  @FXML
  public void initialize() {
    userSession = UserSession.getInstance();

    signedInAsText.setText("Signed in as: " + userSession.getUsername());
    updatePasswords();
  }

  private void updatePasswords() {
    passwordList.getChildren().clear();
    ArrayList<ArrayList<String>> data = userSession.getProfilesNativeTypes();
    ArrayList<GridPane> passwords = new ArrayList<GridPane>();
    for (ArrayList<String> elem : data) { // temporary dummy data
      passwords.add(passwordComponent(elem.get(0), elem.get(1), elem.get(2)));
    }
    for (GridPane i : passwords) {
      passwordList.getChildren().add(i);
    }
  }

  /**
   * passwordComponent sets up a component used in the list view.

   * @param name     name displayed in the password Component
   * @param password password displayed
   * @param email    email displayed
   * @return A GridPane object used to place in the listview
   */
  private GridPane passwordComponent(String name, String password, String email) {
    GridPane gridPane = new GridPane();
    gridPane.setPrefHeight(90);
    gridPane.setPrefWidth(730);

    Text nameText = new Text(name);
    nameText.setStyle("-fx-font: 40 system;");
    nameText.setWrappingWidth(365);

    Text passwordText = new Text(password);
    passwordText.setStyle("-fx-font: 40 system;-fx-text-alignment: right;");
    passwordText.setWrappingWidth(365);

    Text emailText = new Text(email);
    emailText.setStyle("-fx-font: 25 system;");

    gridPane.add(nameText, 0, 0);
    gridPane.add(emailText, 0, 1);
    gridPane.add(passwordText, 1, 1);

    return gridPane;
  }

  /**
   * Tells the userSession to log out and switches the scene back to the login
   * page.

   * @param event ActionEvent object used in the switchScene method
   * @throws IOException if the switchScene does not work
   */
  @FXML
  private void onLogOutButtonClick(ActionEvent event) throws IOException {
    userSession.logOut();
    super.switchScene(event, "login.fxml");
  }

  @FXML
  private void onAddPasswordButtonClick() {
    addLoginOverlay.setVisible(true);
  }

  @FXML
  private void onAddLoginCloseButton() {
    addLoginTitleTextField.setText("");
    addLoginUsernameTextField.setText("");
    addLoginPasswordTextField.setText("");

    addLoginOverlay.setVisible(false);
  }

  @FXML
  private void onAddLoginButton(ActionEvent event) throws IOException {
    userSession.insertProfile(addLoginUsernameTextField.getText(), addLoginTitleTextField.getText(),
        addLoginPasswordTextField.getText());

    addLoginTitleTextField.setText("");
    addLoginUsernameTextField.setText("");
    addLoginPasswordTextField.setText("");
    addLoginOverlay.setVisible(false);
    updatePasswords();
  }

  @FXML
  private void onGeneratePasswordButtonClick() {
    passwordObj = new Password();
    String randomPassword = passwordObj.getPassword();
    addLoginPasswordTextField.setText(randomPassword);
  }

  @FXML
  private void displayPasswordStrength() {
    String password = addLoginPasswordTextField.getText();
    passwordObj = new Password(password);
    int score = passwordObj.getScore();
    if (password.equals("")) {
      passwordStrengthText.setText("");
    }
    else if (score == 1) {
      passwordStrengthText.setText("Weak password");
      passwordStrengthText.setFill(javafx.scene.paint.Color.RED);
    } else if (score == 2) {
      passwordStrengthText.setText("Medium password");
      passwordStrengthText.setFill(javafx.scene.paint.Color.ORANGE);
    } else if (score == 3) {
      passwordStrengthText.setText("Strong password");
      passwordStrengthText.setFill(javafx.scene.paint.Color.GREEN);
    }
  }
}
