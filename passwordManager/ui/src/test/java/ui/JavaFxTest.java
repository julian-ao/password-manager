package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.matching.UrlPattern;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.editStub;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import client.RestTalker;
import client.ServerResponseException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.minidev.json.JSONObject;
import javafx.scene.control.ListView;

import org.json.JSONArray;
import org.junit.jupiter.api.AfterEach;

public class JavaFxTest extends ApplicationTest {

  private RestTalker restTalker = new RestTalker();
  private WireMockConfiguration mockConfig;
  private WireMockServer mockServer;

  public void startWireMockServer() throws URISyntaxException {
    mockConfig = WireMockConfiguration.wireMockConfig().port(8080);
    mockServer = new WireMockServer(mockConfig.portNumber());
    mockServer.start();
  }

  @AfterEach
  public void stopWireMockServer() {
      mockServer.stop();
  }

  @Override
  public void start(Stage stage) throws IOException, URISyntaxException {
    startWireMockServer();

    FXMLLoader fxmlLoader = new FXMLLoader(PasswordManagerApp.class.getResource("login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
    stage.setTitle("Password Manager | Login");
    stage.setScene(scene);
    stage.show();

    String username = "Admin";
    String password = "Admin1!";
    String body = "Success";
    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
            .withBody(body)));
    stubFor(get(
      urlEqualTo("/api/v1/entries/getProfiles?username=" + username + "&password=" + password))
          .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
              .withBody("[]")));
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

    Assertions.assertEquals(lookup("#YourProfiles").query().toString(),
        "Text[id=YourProfiles, text=\"Your Profiles\", x=0.0, y=0.0, alignment=LEFT, origin=BASELINE, boundsType=LOGICAL, font=Font[name=System Regular, family=System, style=Regular, size=60.0], fontSmoothingType=GRAY, fill=0x000000ff]");
  }
   

  // Login with wrong password
  @Test
  public void testLoginWrongPassword() {
    String username = "Admin";
    String password = "Admin1";
    String body = "Failure";
    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
            .withBody(body)));

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

    //
    clickOn("#logOutButton");
    Assertions.assertEquals(true, ((TextField) lookup("#passwordPasswordField").query()).isVisible());
  }
  

  // Login in wrong then click create account
  @Test
  public void testCreateAccount() {
    String username = "Admin1";
    String password = "Admin1!";
    String passwordRepeat = "Admin1!";
    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
            .withBody("Failure")));

    clickOn("#usernameTextField");
    write("Admin1");
    Assertions.assertEquals("Admin1", ((TextField) lookup("#usernameTextField").query()).getText());
    clickOn("#passwordPasswordField");
    write("Admin1!");
    Assertions.assertEquals("", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#passwordPasswordField").query()).getText());

    clickOn("#loginButton");

    Assertions.assertEquals(true, ((Text) lookup("#visualFeedbackText").query()).isVisible());

    clickOn("#registerButton");

    String body = "Success";
    stubFor(post(urlEqualTo("/api/v1/entries/register")).willReturn(
        aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));

    String body2 = "OK";
    stubFor(get(urlEqualTo("/api/v1/entries/userValidator?username=" + username + "&password="
        + password + "&passwordRepeat=" + passwordRepeat))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                .withBody(body2)));


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

    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
            .withBody("Success")));
            stubFor(get(
              urlEqualTo("/api/v1/entries/getProfiles?username=" + username + "&password=" + password))
                  .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                      .withBody("[]")));

    // Log in.
    clickOn("#usernameTextField");
    write("Admin1");

    clickOn("#passwordPasswordField");
    write("Admin1!");
    Assertions.assertEquals("", ((TextField) lookup("#passwordTextField").query()).getText());
    Assertions.assertEquals("Admin1!", ((TextField) lookup("#passwordPasswordField").query()).getText());

    clickOn("#loginButton");
    Assertions.assertEquals(lookup("#YourProfiles").query().toString(),
        "Text[id=YourProfiles, text=\"Your Profiles\", x=0.0, y=0.0, alignment=LEFT, origin=BASELINE, boundsType=LOGICAL, font=Font[name=System Regular, family=System, style=Regular, size=60.0], fontSmoothingType=GRAY, fill=0x000000ff]");

  }
*/
  // Login and create profile
  @Test
  public void testCreateProfile() {
    String username = "Admin";
    String password = "Admin1!";
    clickOn("#usernameTextField");
    write(username);
    clickOn("#passwordPasswordField");
    write(password);

    clickOn("#loginButton");

    String body = "Success";
    stubFor(post(urlEqualTo("/api/v1/entries/insertProfile")).willReturn(
        aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));


    String username1 = "kbrat";
    String password1 = "passord1!";
    String title1 = "google.com";

    JSONArray profiles = new JSONArray();
    JSONObject profile = new JSONObject();
    profile.put("username", username1);
    profile.put("password", password1);
    profile.put("title", title1);
    profiles.put(profile);

    stubFor(get(
      urlEqualTo("/api/v1/entries/getProfiles?username=" + username + "&password=" + password))
          .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
              .withBody(profiles.toString())));


    // create profile
    clickOn("#addProfileButton");
    clickOn("#addProfileTitleTextField");
    write(title1);
    clickOn("#addProfileUsernameTextField");
    write(username1);
    clickOn("#addProfilePasswordTextField");
    write(password1);
    clickOn("#submitProfileButton");


    // assert profilesListView is visible and has 1 item
    Assertions.assertEquals(true, ((ListView<GridPane>) lookup("#profilesListView").query()).isVisible());
    
    String username2 = "kbrat";
    String password2 = "passord";
    String title2 = "google.com";

    JSONObject profile2 = new JSONObject();
    profile2.put("username", username2);
    profile2.put("password", password2);
    profile2.put("title", title2);
    profiles.put(profile2);

    stubFor(get(
      urlEqualTo("/api/v1/entries/getProfiles?username=" + username + "&password=" + password))
          .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
              .withBody(profiles.toString())));
    
    // create profile
    clickOn("#addProfileButton");
    clickOn("#addProfileTitleTextField");
    write(title2);
    clickOn("#addProfileUsernameTextField");
    write(username2);
    clickOn("#addProfilePasswordTextField");
    write(password2);
    Assertions.assertEquals("Weak password", ((Text) lookup("#passwordStrengthText").query()).getText());
    clickOn("#addProfilePasswordTextField");
    write("123");
    Assertions.assertEquals("Medium password", ((Text) lookup("#passwordStrengthText").query()).getText());
    clickOn("#profilePasswordGenerateButton");
    Assertions.assertEquals("Strong password", ((Text) lookup("#passwordStrengthText").query()).getText());
    clickOn("#submitProfileButton");

    profiles.remove(0);
    stubFor(get(
      urlEqualTo("/api/v1/entries/getProfiles?username=" + username + "&password=" + password))
          .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
              .withBody(profiles.toString())));

    stubFor(post(urlEqualTo("/api/v1/entries/deleteProfile")).willReturn(
        aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("Success")));

    Assertions.assertEquals(2, ((ListView<GridPane>) lookup("#profilesListView").query()).getItems().size());
    GridPane listOfProfiles = ((ListView<GridPane>) lookup("#profilesListView").query()).getItems().get(0);
    clickOn(listOfProfiles.getChildren().get(4));
    Assertions.assertEquals(1, ((ListView<GridPane>) lookup("#profilesListView").query()).getItems().size());
  }
}