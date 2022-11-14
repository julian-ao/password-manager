package ui;

import client.Password;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import client.RestTalker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
  private Pane addProfileOverlay;

  @FXML
  private Pane addProfileClosePane;

  @FXML
  private Button logOutButton;

  @FXML
  private Text signedInAsText;

  @FXML
  private Text passwordStrengthText;

  @FXML
  private Text visualFeedbackText;

  @FXML
  private TextField addProfileTitleTextField;

  @FXML
  private TextField addProfileUsernameTextField;

  @FXML
  private TextField addProfilePasswordTextField;

  @FXML
  private ListView<GridPane> profilesListView;

  @FXML
  private Scene scene;

  private Password passwordObj;

  final Clipboard clipboard = Clipboard.getSystemClipboard();
  final ClipboardContent clipboardContent = new ClipboardContent();
  private RestTalker restTalker = new RestTalker();

  /**
   * initialize sets the signed in as text to the username of the user
   * and updates the passwords based on the used session.
   */
  @FXML
  public void initialize() {
    logOutButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

    SVGPath crossSVGPath = new SVGPath();
    crossSVGPath.setContent(
      "M310.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L160 210.7 54.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L114.7 256 9.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L160 301.3 265.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L205.3 256 310.6 150.6z"
    );

    BackgroundFill redBackgroundFill = new BackgroundFill(Color.valueOf(lightRed),
      new CornerRadii(10), new Insets(10));
    Background redBackground = new Background(redBackgroundFill);

    addProfileClosePane.setBackground(redBackground);
    addProfileClosePane.setShape(crossSVGPath);
    addProfileClosePane.setMinSize(45, 45);
    addProfileClosePane.setMaxSize(45, 45);

    addProfileClosePane.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        onHover(addProfileClosePane, darkRed);
      }
    });

    addProfileClosePane.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        offHover(addProfileClosePane, lightRed);
      }
    });
    
    String username = restTalker.getUsername();
    signedInAsText.setText("Signed in as: " + username);
    updatePasswords();
  }

  private void updatePasswords() {
    profilesListView.getItems().clear();
    JSONArray jsonArray = new JSONArray(restTalker.getProfiles());
    ArrayList<GridPane> passwords = new ArrayList<GridPane>();
    for (Object elem : jsonArray) {
      passwords.add(profileComponent(((JSONObject) elem).getString("title"), ((JSONObject) elem).getString("username"),
          ((JSONObject) elem).getString("password")));
    }
    for (GridPane i : passwords) {
      profilesListView.getItems().add(i);
    }
  }

  /**
   * profileComponent sets up a component used in the list view.
   * 
   * @param name     name displayed in the password Component
   * @param password password displayed
   * @param title    title displayed
   * @return A GridPane object used to place in the listview
   */
  private GridPane profileComponent(String username, String title, String password) {
    GridPane gridPane = new GridPane();
    gridPane.setMaxWidth(720);
    gridPane.setPadding(new Insets(15, 20, 20, 20));

    Text titleText = new Text(title);
    titleText.setStyle("-fx-font: 35 system;");

    Label usernameText = makeSelectable(new Label(username));
    usernameText.setStyle("-fx-text-fill: white; -fx-font: 25 system;"); //! fargedritten funker ikke
    usernameText.setTextFill(Color.web(grey));

    Label passwordText = makeSelectable(new Label(password));
    passwordText.setStyle("-fx-font: 25 system;");
    passwordText.setTextFill(Color.web(grey)); //! fargedritten funker ikke

    // IMAGE
    SVGPath copySVGPath = new SVGPath();
    copySVGPath.setContent(
        "M502.6 70.63l-61.25-61.25C435.4 3.371 427.2 0 418.7 0H255.1c-35.35 0-64 28.66-64 64l.0195 256C192 355.4 220.7 384 256 384h192c35.2 0 64-28.8 64-64V93.25C512 84.77 508.6 76.63 502.6 70.63zM464 320c0 8.836-7.164 16-16 16H255.1c-8.838 0-16-7.164-16-16L239.1 64.13c0-8.836 7.164-16 16-16h128L384 96c0 17.67 14.33 32 32 32h47.1V320zM272 448c0 8.836-7.164 16-16 16H63.1c-8.838 0-16-7.164-16-16L47.98 192.1c0-8.836 7.164-16 16-16H160V128H63.99c-35.35 0-64 28.65-64 64l.0098 256C.002 483.3 28.66 512 64 512h192c35.2 0 64-28.8 64-64v-32h-47.1L272 448z");

    SVGPath trashSVGPath = new SVGPath();
    trashSVGPath.setContent(
        "M160 400C160 408.8 152.8 416 144 416C135.2 416 128 408.8 128 400V192C128 183.2 135.2 176 144 176C152.8 176 160 183.2 160 192V400zM240 400C240 408.8 232.8 416 224 416C215.2 416 208 408.8 208 400V192C208 183.2 215.2 176 224 176C232.8 176 240 183.2 240 192V400zM320 400C320 408.8 312.8 416 304 416C295.2 416 288 408.8 288 400V192C288 183.2 295.2 176 304 176C312.8 176 320 183.2 320 192V400zM317.5 24.94L354.2 80H424C437.3 80 448 90.75 448 104C448 117.3 437.3 128 424 128H416V432C416 476.2 380.2 512 336 512H112C67.82 512 32 476.2 32 432V128H24C10.75 128 0 117.3 0 104C0 90.75 10.75 80 24 80H93.82L130.5 24.94C140.9 9.357 158.4 0 177.1 0H270.9C289.6 0 307.1 9.358 317.5 24.94H317.5zM151.5 80H296.5L277.5 51.56C276 49.34 273.5 48 270.9 48H177.1C174.5 48 171.1 49.34 170.5 51.56L151.5 80zM80 432C80 449.7 94.33 464 112 464H336C353.7 464 368 449.7 368 432V128H80V432z");

    // COLOR
    BackgroundFill greyBackgroundFill = new BackgroundFill(Color.valueOf(lightBlue),
      new CornerRadii(10), new Insets(10));
    Background greyBackground = new Background(greyBackgroundFill);

    BackgroundFill redBackgroundFill = new BackgroundFill(Color.valueOf(lightRed),
      new CornerRadii(10), new Insets(10));
    Background redBackground = new Background(redBackgroundFill);

    // TOGETHER
    Region copyRegion = new Region();
    copyRegion.setBackground(greyBackground);
    copyRegion.setShape(copySVGPath);
    copyRegion.setMinSize(45, 45);
    copyRegion.setMaxSize(45, 45);

    Region trashRegion = new Region();
    trashRegion.setBackground(redBackground);
    trashRegion.setShape(trashSVGPath);
    trashRegion.setMinSize(40, 45);
    trashRegion.setMaxSize(40, 45);

    copyRegion.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        clipboardContent.putString(password);
        clipboard.setContent(clipboardContent);
      }
    });

    copyRegion.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        onHover(copyRegion, darkBlue);
      }
    });

    copyRegion.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        offHover(copyRegion, lightBlue);
      }
    });

    trashRegion.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        onHover(trashRegion, darkRed);
      }
    });

    trashRegion.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        offHover(trashRegion, lightRed);
      }
    });

    trashRegion.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        // TODO get profile and delete it
        String usernameToDelete = usernameText.getText();
        String titleToDelete = titleText.getText();
        String passwordToDelete = passwordText.getText();
        String userToDelete = restTalker.getUsername();
        onDeletePasswordButtonClick(userToDelete, titleToDelete, usernameToDelete, passwordToDelete);
      }
    });

    clipboardContent.putString(password);
    clipboard.setContent(clipboardContent);

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(50);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(36);
    ColumnConstraints col3 = new ColumnConstraints();
    col3.setPercentWidth(7);
    ColumnConstraints col4 = new ColumnConstraints();
    col4.setPercentWidth(7);

    gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

    gridPane.add(titleText, 0, 0);
    gridPane.add(usernameText, 0, 1);
    gridPane.add(passwordText, 1, 1);
    gridPane.add(copyRegion, 2, 1);
    gridPane.add(trashRegion, 3, 1);

    return gridPane;
  }

  private Label makeSelectable(Label label) {
    StackPane textStack = new StackPane();
    TextField textField = new TextField(label.getText());
    textField.setEditable(false);
    textField.setStyle(
        "-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0; -fx-padding: 0;");
    // the invisible label is a hack to get the textField to size like a label.
    Label invisibleLabel = new Label();
    invisibleLabel.textProperty().bind(label.textProperty());
    invisibleLabel.setVisible(false);
    textStack.getChildren().addAll(invisibleLabel, textField);
    label.textProperty().bindBidirectional(textField.textProperty());
    label.setGraphic(textStack);
    label.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

    return label;
  }

  /**
   * Tells the restTalker to log out and switches the scene back to the login
   * page.
   * 
   * @param event ActionEvent object used in the switchScene method
   * @throws IOException if the switchScene does not work
   */
  @FXML
  private void onLogOutButtonClick(ActionEvent event) throws IOException {
    System.out.println("Logging out...");
    restTalker.logout();
    super.switchScene(event, "login.fxml");
  }

  /**
   * Opens the add profile overlay.
   */
  @FXML
  private void onAddProfileButtonClick() {
    addProfileTitleTextField.setText("");
    addProfileUsernameTextField.setText("");
    addProfilePasswordTextField.setText("");

    setBorder(addProfileUsernameTextField, grey);
    setBorder(addProfileTitleTextField, grey);
    setBorder(addProfilePasswordTextField, grey);

    addProfileOverlay.setVisible(true);
    passwordListPage.setEffect(new GaussianBlur(50));
  }

  /**
   * Closes the add profile overlay.
   */
  @FXML
  private void onAddProfileClosePane() {
    visualFeedbackText.setVisible(false);
    addProfileOverlay.setVisible(false);
    passwordListPage.setEffect(null);
  }

  /**
   * Adds a profile to the database and updates the list view.
   */
  @FXML
  private void onAddProfileButton(ActionEvent event) throws IOException {
    if (addProfileUsernameTextField.getText().equals("")
        || addProfileTitleTextField.getText().equals("")
        || addProfilePasswordTextField.getText().equals("")) {
      visualFeedbackText.setVisible(true);
      setBorder(addProfileUsernameTextField, lightRed);
      setBorder(addProfileTitleTextField, lightRed);
      setBorder(addProfilePasswordTextField, lightRed);
      rotateNode(visualFeedbackText, false);
    } else {
      restTalker.insertProfile(
          addProfileTitleTextField.getText(),
          addProfileUsernameTextField.getText(),
          addProfilePasswordTextField.getText());

      System.out.println("Profile added!");

      visualFeedbackText.setVisible(false);
      addProfileOverlay.setVisible(false);
      passwordListPage.setEffect(null);
      updatePasswords();
    }
  }

  /**
   * Delete password
   */
  @FXML
  private void onDeletePasswordButtonClick(String user, String title, String username, String password) {
    System.out.println("Deleting password...");
    if (restTalker.deleteProfile(user, title, username, password)) {
      System.out.println("Password deleted!");
      updatePasswords();
    } else {
      System.out.println("------Failed to delete profile------");
    }
  }

  @FXML
  private void onGeneratePasswordButtonClick() {
    passwordObj = new Password();
    String randomPassword = passwordObj.getPassword();
    addProfilePasswordTextField.setText(randomPassword);
  }

  /**
   * Checks the strength of the password and displays it to the user.
   */
  @FXML
  private void displayPasswordStrength() {
    String password = addProfilePasswordTextField.getText();
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
