package ui;

import core.Password;
import core.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
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
  private Text passwordStrengthText;

  @FXML
  private Text visualFeedbackText;

  @FXML
  private TextField addLoginTitleTextField;

  @FXML
  private TextField addLoginUsernameTextField;

  @FXML
  private TextField addLoginPasswordTextField;


  private UserSession userSession;

  private Password passwordObj;

  final Clipboard clipboard = Clipboard.getSystemClipboard();

  final ClipboardContent  clipboardContent = new ClipboardContent();

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
  private GridPane passwordComponent(String username, String title, String password) {
    GridPane gridPane = new GridPane();
    gridPane.setPrefWidth(690);
    gridPane.setPadding(new Insets(15, 0, 20, 0));  
    gridPane.setBorder(new Border(new BorderStroke(Color.web("#A6A6A6"), 
            Color.web("#A6A6A6"), Color.web("#A6A6A6"), Color.web("#A6A6A6"),
            BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
            CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

    Text titleText = new Text(title);
    titleText.setStyle("-fx-font: 40 system;");

    Text usernameText = new Text(username);
    usernameText.setStyle("-fx-font: 25 system;-fx-text-fill: red;");
    usernameText.setFill(Color.web("#A6A6A6"));

    Text passwordText = new Text(password);
    passwordText.setStyle("-fx-font: 25 system;-fx-text-alignment: right;");
    passwordText.setFill(Color.web("#A6A6A6"));

    // IMAGE
    SVGPath copySVGPath = new SVGPath();
    copySVGPath.setContent("M502.6 70.63l-61.25-61.25C435.4 3.371 427.2 0 418.7 0H255.1c-35.35 0-64 28.66-64 64l.0195 256C192 355.4 220.7 384 256 384h192c35.2 0 64-28.8 64-64V93.25C512 84.77 508.6 76.63 502.6 70.63zM464 320c0 8.836-7.164 16-16 16H255.1c-8.838 0-16-7.164-16-16L239.1 64.13c0-8.836 7.164-16 16-16h128L384 96c0 17.67 14.33 32 32 32h47.1V320zM272 448c0 8.836-7.164 16-16 16H63.1c-8.838 0-16-7.164-16-16L47.98 192.1c0-8.836 7.164-16 16-16H160V128H63.99c-35.35 0-64 28.65-64 64l.0098 256C.002 483.3 28.66 512 64 512h192c35.2 0 64-28.8 64-64v-32h-47.1L272 448z");

    // COLOR
    BackgroundFill greyBackgroundFill = new BackgroundFill(Color.valueOf("#A6A6A6"), new CornerRadii(10), new Insets(10));
    Background greyBackground = new Background(greyBackgroundFill);

    // TOGETHER
    Region copyRegion = new Region();
    copyRegion.setBackground(greyBackground);
    copyRegion.setShape(copySVGPath);
    copyRegion.setMinSize(50, 50);
    copyRegion.setMaxSize(50, 50);

    copyRegion.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        clipboardContent.putString(password);
        clipboard.setContent(clipboardContent);
        System.out.println("Copied to clipboard");
      }
    });

    copyRegion.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        scene = copyRegion.getScene();
        copyRegion.setBackground(new Background(new BackgroundFill(Color.valueOf("#D9D9D9"), new CornerRadii(10), new Insets(10))));
        scene.setCursor(Cursor.HAND); //Change cursor to hand
      }
    });

    copyRegion.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        scene = copyRegion.getScene();
        copyRegion.setBackground(new Background(new BackgroundFill(Color.valueOf("#A6A6A6"), new CornerRadii(10), new Insets(10))));
        scene.setCursor(Cursor.DEFAULT); //Change cursor to default
      }
    });

    clipboardContent.putString(password);
    clipboard.setContent(clipboardContent);
    
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(50);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(40);
    ColumnConstraints col3 = new ColumnConstraints();
    col3.setPercentWidth(10);
    
    gridPane.getColumnConstraints().addAll(col1,col2,col3);

    gridPane.add(titleText, 0, 0);
    gridPane.add(usernameText, 0, 1);
    gridPane.add(passwordText, 1, 1);
    gridPane.add(copyRegion, 2, 1);

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
    passwordListPage.setEffect(new GaussianBlur(50));
  }

  /**
   * Closes the add login overlay.
   */
  @FXML
  private void onAddLoginCloseButton() {
    visualFeedbackText.setVisible(false);
    addLoginOverlay.setVisible(false);
    passwordListPage.setEffect(null);
  }

  /**
   * Adds a login to the database and updates the list view.
   */
  @FXML
  private void onAddLoginButton(ActionEvent event) throws IOException {
    if (
        addLoginUsernameTextField.getText().equals("")
        || addLoginTitleTextField.getText().equals("")
        || addLoginPasswordTextField.getText().equals("")
    ) {
      visualFeedbackText.setVisible(true);
      addLoginUsernameTextField.setStyle("-fx-border-color: #FE8383");
      addLoginTitleTextField.setStyle("-fx-border-color: #FE8383");
      addLoginPasswordTextField.setStyle("-fx-border-color: #FE8383");
      super.rotateNode(visualFeedbackText, false);
    } else {
      userSession.insertProfile(
          addLoginUsernameTextField.getText(),
          addLoginTitleTextField.getText(),
          addLoginPasswordTextField.getText()
      );

      visualFeedbackText.setVisible(false);
      addLoginOverlay.setVisible(false);
      passwordListPage.setEffect(null);
      updatePasswords();
    }
  }

  @FXML
  private void onGeneratePasswordButtonClick() {
    passwordObj = new Password();
    String randomPassword = passwordObj.getPassword();
    addLoginPasswordTextField.setText(randomPassword);
  }

  /**
   * Checks the strength of the password and displays a message to the user.
   */
  @FXML
  private void displayPasswordStrength() {
    String password = addLoginPasswordTextField.getText();
    passwordObj = new Password(password);
    int score = passwordObj.getScore();
    if (password.equals("")) {
      passwordStrengthText.setText("");
    } else if (score == 1) {
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
