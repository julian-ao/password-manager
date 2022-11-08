package ui;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.UserSession;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFxTest extends ApplicationTest {


  @Override
  public void start(Stage stage) throws IOException {

    UserSession userSession = UserSession.getInstance();
    File file = new File("../localpersistence/src/resources/localpersistance/TestUsers.json");
    if(file.exists()){
      file.delete();
    }
    userSession.overridePath("../localpersistence/src/resources/localpersistance/TestUsers.json");
    userSession.registerUser("Admin", "Admin1!");

    FXMLLoader fxmlLoader = new FXMLLoader(PasswordManagerApp.class.getResource("login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
    stage.setTitle("Password Manager | Login");
    stage.setScene(scene);
    stage.show();
  }


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
}