package ui;

import client.RestTalker;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * PasswordManagerApp is the main class of the PasswordManager application.
 */
public class PasswordManagerApp extends Application {

  /**
   * start is the main method of the application, it loads the login page.
   *
   * @param stage the stage to be used in the application
   */
  @Override
  public void start(Stage stage) throws IOException {
    RestTalker restTalker = new RestTalker();
    restTalker.doPrdDB();
    FXMLLoader fxmlLoader = new FXMLLoader(PasswordManagerApp.class.getResource("login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
    stage.setTitle("Password Manager | Login");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
