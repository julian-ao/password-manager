package ui;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import client.RestTalker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

public class JavaFxTest extends ApplicationTest {

  private RestTalker restTalker = new RestTalker();

  @Override
  public void start(Stage stage) throws IOException {

    String tempUrl = "../localpersistence/src/resources/localpersistance/TempUsers.json";
    String url = "../localpersistence/src/resources/localpersistance/Users.json";

    // move everything inside the main persistence file to the temp file by deleting the temp file and renaming the main file
    // then create a new main file
    File tempFile = new File(tempUrl);
    File mainFile = new File(url);
    if (tempFile.exists()) {
      tempFile.delete();
    }
    mainFile.renameTo(tempFile);
    File newMainFile = new File(url);
    newMainFile.createNewFile();

    restTalker.registerUser("Admin", "Admin1!");


    FXMLLoader fxmlLoader = new FXMLLoader(PasswordManagerApp.class.getResource("login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
    stage.setTitle("Password Manager | Login");
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() {

    // delete main file and rename temp file to main file
    String tempUrl = "../localpersistence/src/resources/localpersistance/TempUsers.json";
    String url = "../localpersistence/src/resources/localpersistance/Users.json";
    File tempFile = new File(tempUrl);
    File mainFile = new File(url);
    if (mainFile.exists()) {
      mainFile.delete();
    }
    tempFile.renameTo(mainFile);
  }

/*
  // Basic login, username: Admin, password: Admin1!
  @Test
  public void testLogin() {
    clickOn("#usernameTextField");
    write("Admin");
    Assertions.assertEquals("Admin", ((TextField) lookup("#usernameTextField").query()).getText());
    clickOn("#passwordPasswordField");
    write("Admin1!");
    Assertions.assertEquals("", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#passwordPasswordField").query()).getText());

    // Check input and if visible
    clickOn("#eyeImageView");
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals(true, ((TextField) lookup("#passwordTextField").query()).isVisible());
    Assertions.assertEquals(false, ((TextField) lookup("#passwordPasswordField").query()).isVisible());

    clickOn("#eyeImageView");
    Assertions.assertEquals(false, ((TextField) lookup("#passwordTextField").query()).isVisible());
    Assertions.assertEquals(true, ((TextField) lookup("#passwordPasswordField").query()).isVisible());

    clickOn("#loginButton");  

    Assertions.assertEquals(lookup("#YourProfiles").query().toString(), "Text[id=YourProfiles, text=\"Your Profiles\", x=0.0, y=0.0, alignment=LEFT, origin=BASELINE, boundsType=LOGICAL, font=Font[name=System Regular, family=System, style=Regular, size=60.0], fontSmoothingType=GRAY, fill=0x000000ff]");
  }

  // Login with wrong password
  @Test
  public void testLoginWrongPassword() {
    clickOn("#usernameTextField");
    write("Admin");
    Assertions.assertEquals("Admin", ((TextField) lookup("#usernameTextField").query()).getText());
    clickOn("#passwordPasswordField");
    write("Admin1");
    Assertions.assertEquals("", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals("Admin1", ((TextField) lookup("#passwordPasswordField").query()).getText());

    // Check input and if visible
    clickOn("#eyeImageView");
    Assertions.assertEquals("Admin1", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals(true, ((TextField) lookup("#passwordTextField").query()).isVisible());
    Assertions.assertEquals(false, ((TextField) lookup("#passwordPasswordField").query()).isVisible());

    clickOn("#eyeImageView");
    Assertions.assertEquals(false, ((TextField) lookup("#passwordTextField").query()).isVisible());
    Assertions.assertEquals(true, ((TextField) lookup("#passwordPasswordField").query()).isVisible());

    clickOn("#loginButton");

    Assertions.assertEquals(true, ((Text) lookup("#visualFeedbackText").query()).isVisible());
  }

  // Login and clicks logout button
  @Test
  public void testLogout() {
    clickOn("#usernameTextField");
    write("Admin");
    Assertions.assertEquals("Admin", ((TextField) lookup("#usernameTextField").query()).getText());
    clickOn("#passwordPasswordField");
    write("Admin1!");
    Assertions.assertEquals("", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#passwordPasswordField").query()).getText());

    // Check input and if visible
    clickOn("#eyeImageView");
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals(true, ((TextField) lookup("#passwordTextField").query()).isVisible());
    Assertions.assertEquals(false, ((TextField) lookup("#passwordPasswordField").query()).isVisible());

    clickOn("#eyeImageView");
    Assertions.assertEquals(false, ((TextField) lookup("#passwordTextField").query()).isVisible());
    Assertions.assertEquals(true, ((TextField) lookup("#passwordPasswordField").query()).isVisible());

    clickOn("#loginButton");

    Assertions.assertEquals(lookup("#YourProfiles").query().toString(), "Text[id=YourProfiles, text=\"Your Profiles\", x=0.0, y=0.0, alignment=LEFT, origin=BASELINE, boundsType=LOGICAL, font=Font[name=System Regular, family=System, style=Regular, size=60.0], fontSmoothingType=GRAY, fill=0x000000ff]");

    //
    clickOn("#logOutButton");
    Assertions.assertEquals(true, ((TextField) lookup("#passwordPasswordField").query()).isVisible());    
  }


  // Login in wrong then click create account
  @Test
  public void testCreateAccount() {
    clickOn("#usernameTextField");
    write("Admin1");
    Assertions.assertEquals("Admin1", ((TextField) lookup("#usernameTextField").query()).getText());
    clickOn("#passwordPasswordField");
    write("Admin1");
    Assertions.assertEquals("", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals("Admin1", ((TextField) lookup("#passwordPasswordField").query()).getText());

    clickOn("#loginButton");

    Assertions.assertEquals(true, ((Text) lookup("#visualFeedbackText").query()).isVisible());

    clickOn("#registerButton");

    // Register
    clickOn("#usernameTextField");
    write("Admin1");
    Assertions.assertEquals("Admin1", ((TextField) lookup("#usernameTextField").query()).getText());
    clickOn("#passwordPasswordField");
    write("Admin1!");
    clickOn("#repeatPasswordPasswordField");
    write("Admin1!");
    Assertions.assertEquals("", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals("", ((TextField) lookup("#repeatPasswordTextField").query()).getText());
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#passwordPasswordField").query()).getText());
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#repeatPasswordPasswordField").query()).getText());

    clickOn("#createUserButton");

    // Log in.
    clickOn("#usernameTextField");
    write("Admin1");
    
    clickOn("#passwordPasswordField");
    write("Admin1!");
    Assertions.assertEquals("", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#passwordPasswordField").query()).getText());

    clickOn("#loginButton");
    Assertions.assertEquals(lookup("#YourProfiles").query().toString(), "Text[id=YourProfiles, text=\"Your Profiles\", x=0.0, y=0.0, alignment=LEFT, origin=BASELINE, boundsType=LOGICAL, font=Font[name=System Regular, family=System, style=Regular, size=60.0], fontSmoothingType=GRAY, fill=0x000000ff]");
  }


    */
  // Login and create profile
   @Test
   public void testCreateProfile() {
     clickOn("#usernameTextField");
     write("Admin");
     clickOn("#passwordPasswordField");
     write("Admin1!");
 
     clickOn("#loginButton");
     // create profile
    clickOn("#addProfileButton");
    clickOn("#addProfileTitleTextField");
    write("google.com");

     clickOn("#addProfileUsernameTextField");
     write("kbrat");

     clickOn("#addProfilePasswordTextField");
     write("passord1");

     clickOn("#submitProfileButton");
     // assert profilesListView is visible and has 1 item
      Assertions.assertEquals(true, ((ListView<GridPane>) lookup("#profilesListView").query()).isVisible());

    // create profile
    clickOn("#addProfileButton");
    clickOn("#addProfileTitleTextField");
    write("instagram.com");

     clickOn("#addProfileUsernameTextField");
     write("kbrat2");

     clickOn("#addProfilePasswordTextField");
     write("passord");
    Assertions.assertEquals("Weak password", ((Text) lookup("#passwordStrengthText").query()).getText());
    clickOn("#addProfilePasswordTextField");
     write("123");
    Assertions.assertEquals("Medium password", ((Text) lookup("#passwordStrengthText").query()).getText());
    clickOn("#profilePasswordGenerateButton");
    Assertions.assertEquals("Strong password", ((Text) lookup("#passwordStrengthText").query()).getText());
     clickOn("#submitProfileButton");

    Assertions.assertEquals(2, ((ListView<GridPane>) lookup("#profilesListView").query()).getItems().size());
    GridPane listOfProfiles = ((ListView<GridPane>) lookup("#profilesListView").query()).getItems().get(0);
    clickOn(listOfProfiles.getChildren().get(4));
    Assertions.assertEquals(1, ((ListView<GridPane>) lookup("#profilesListView").query()).getItems().size());
    }
}