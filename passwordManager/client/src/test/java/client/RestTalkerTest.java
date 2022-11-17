package client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.fail;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class RestTalkerTest {

  private WireMockServer mockServer;

  private RestTalker restTalker;

  @BeforeEach
  public void startWireMockServer() {
    WireMockConfiguration mockConfig = WireMockConfiguration.wireMockConfig().port(8080);
    mockServer = new WireMockServer(mockConfig.portNumber());
    mockServer.start();
    WireMock.configureFor("localhost", mockConfig.portNumber());
    restTalker = new RestTalker("http://localhost", mockServer.port());
  }

  @AfterEach
  public void stopWireMockServer() {
    mockServer.stop();
  }

  // tests login
  @Test
  public void login() {
    String username = "Admin";
    String password = "Admin1!";
    String body = "Success";
    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));

    try {
      assertEquals(true, restTalker.login("Admin", "Admin1!"));
    } catch (URISyntaxException | InterruptedException | ExecutionException | ServerResponseException e) {
      e.printStackTrace();
      fail();
    }

    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("Failure")));

    try {
      assertEquals(false, restTalker.login("Admin", "Admin1!"));
    } catch (Exception e) {
      fail();
    }
    // restTalker.registerUser("Admin", "Admin1!");
    // Assertions.assertEquals("Success", restTalker.login("Admin", "Admin1!"));
  }

  // test register
  @Test
  public void register() {
    String body = "Success";
    stubFor(post(urlEqualTo("/api/v1/entries/register"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));
    try {
      assertEquals(true, restTalker.registerUser("Admin", "Admin1!"));
    } catch (URISyntaxException | InterruptedException | ExecutionException | ServerResponseException e) {
      e.printStackTrace();
      fail();
    }
  }

  // get profiles
  @Test
  public void getProfiles() {
    String username = "Admin";
    String password = "Admin1!";
    String body = "Success";
    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));

    stubFor(get(urlEqualTo("/api/v1/entries/getProfiles?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("[]")));
    try {
      assertEquals(true, restTalker.login("Admin", "Admin1!"));
      assertEquals("[]", restTalker.getProfiles());
    } catch (URISyntaxException | InterruptedException | ExecutionException | ServerResponseException e) {
      e.printStackTrace();
      fail();
    }
  }

  // test insert profile
  @Test
  public void insertProfile() {
    String username = "Admin";
    String password = "Admin1!";
    String body = "Success";
    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));
    stubFor(post(urlEqualTo("/api/v1/entries/insertProfile"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));
    try {
      assertEquals(true, restTalker.login("Admin", "Admin1!"));
      assertEquals(true, restTalker.insertProfile("Admin", "Title", "Password"));
    } catch (URISyntaxException | InterruptedException | ExecutionException | ServerResponseException e) {
      e.printStackTrace();
      fail();
    }
  }

  // test uservalidator
  @Test
  public void userValidatorTest() {

    String username = "Admin";
    String password = "Admin1!";
    String passwordRepeat = "Admin1!";
    String body = "OK";
    stubFor(get(urlEqualTo("/api/v1/entries/userValidator?username=" + username + "&password=" + password
        + "&passwordRepeat=" + passwordRepeat))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));
    try {
      assertEquals("OK", restTalker.userValidator("Admin", "Admin1!", "Admin1!"));
    } catch (URISyntaxException | InterruptedException | ExecutionException | ServerResponseException e) {
      e.printStackTrace();
      fail();
    }
  }

  // test profile delete
  @Test
  public void deleteProfileTest() {
    String user = "Admin";
    String title = "Title";
    String password = "Password";
    String username = "Admin";
    String body = "Success";

    stubFor(get(urlEqualTo("/api/v1/entries/login?username=" + username + "&password=" + password))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));

    stubFor(post(urlEqualTo("/api/v1/entries/deleteProfile"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));

    try {
      assertEquals(true, restTalker.login("Admin", "Password"));
      assertEquals(true, restTalker.deleteProfile(user, title, username, password, 0));
    } catch (URISyntaxException | InterruptedException | ExecutionException | ServerResponseException e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void switchDatabaseTest() {

    stubFor(post(urlEqualTo("/api/v1/entries/doDatabaseTest"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("b")));
    stubFor(post(urlEqualTo("/api/v1/entries/doPrdDB"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("b")));

    RestTalker restTalker = new RestTalker();
    restTalker.doDatabaseTest();
    restTalker.doPrdDB();
  }
}
