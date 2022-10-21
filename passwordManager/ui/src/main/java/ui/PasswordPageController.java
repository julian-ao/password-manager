package ui;

import core.UserSession;
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

/**
 * FXML Controller class for the password page.
 */
public class PasswordPageController extends PasswordManagerController {

  @FXML
  private FlowPane passwordList;

  @FXML
  private Pane passwordListPage;

  @FXML
  private Pane addLoginOverlay;

  @FXML
  private Button addLoginCloseButton;

  @FXML
  private Text signedInAsText;

  @FXML
  private Text visualFeedbackText;

  @FXML
  private TextField addLoginTitleTextField;

  @FXML
  private TextField addLoginUsernameTextField;

  @FXML
  private TextField addLoginPasswordTextField;

  @FXML
  private Rectangle addLoginBlur;

  private UserSession userSession;

  /**
   * initialize sets the signed in as text to the username of the user
   * and updates the passwords based on the used session.
   */
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

  /**
   * Opens the add login overlay.
   */
  @FXML
  private void onAddPasswordButtonClick() {
    addLoginTitleTextField.setText("");
    addLoginUsernameTextField.setText("");
    addLoginPasswordTextField.setText("");

    addLoginUsernameTextField.setStyle("-fx-border-color: #A6A6A6");
    addLoginTitleTextField.setStyle("-fx-border-color: #A6A6A6");
    addLoginPasswordTextField.setStyle("-fx-border-color: #A6A6A6");

    addLoginOverlay.setVisible(true);
  }

  /**
   * Closes the add login overlay.
   */
  @FXML
  private void onAddLoginCloseButton() {
    visualFeedbackText.setVisible(false);
    addLoginOverlay.setVisible(false);
  }

  /**
   * Adds a login to the database and updates the list view.
   */
  @FXML
  private void onAddLoginButton(ActionEvent event) throws IOException {
    if (addLoginUsernameTextField.getText() == "" || addLoginTitleTextField.getText() == "" || addLoginPasswordTextField.getText() == "") {
      visualFeedbackText.setVisible(true);
      addLoginUsernameTextField.setStyle("-fx-border-color: #FE8383");
      addLoginTitleTextField.setStyle("-fx-border-color: #FE8383");
      addLoginPasswordTextField.setStyle("-fx-border-color: #FE8383");
      super.rotateNode(visualFeedbackText, false);
    } else {
      userSession.insertProfile(addLoginUsernameTextField.getText(), addLoginTitleTextField.getText(), addLoginPasswordTextField.getText());

      visualFeedbackText.setVisible(false);
      addLoginOverlay.setVisible(false);
      updatePasswords();
    }
  }
}
