package integration;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import client.ServerResponseException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import restserver.PasswordManagerApplication;
import restserver.PasswordManagerController;

import client.RestTalker;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {PasswordManagerController.class, PasswordManagerApplication.class})
public class PasswordManagerIntegrationTest {
  @LocalServerPort
  int port = 8080;
    
  @Autowired
  public PasswordManagerController controller;

  private RestTalker restTalker;

  @BeforeEach
  public void startClient() throws InterruptedException {
      this.restTalker = new RestTalker("http://localhost", port);
      restTalker.doDatabaseTest();
  }

  @Test
    public void testCompilation() {
        Assertions.assertNotNull(controller);
    }

  // Tests login, and register a user and then login with that user
  @Test
  public void testLogin() throws ServerResponseException, InterruptedException, ExecutionException, URISyntaxException {
    Assertions.assertEquals(false , restTalker.login("test", "test"));
    Assertions.assertEquals(true , restTalker.registerUser("test", "test"));
    Assertions.assertEquals(true , restTalker.login("test", "test"));
  }

  // Tests adding an profile, and then getting it back
  @Test
  public void testGetProfile() throws ServerResponseException, InterruptedException, ExecutionException, URISyntaxException {
    restTalker.registerUser("test", "test");
    restTalker.login("test", "test");

    Assertions.assertEquals("[]", restTalker.getProfiles());

    Assertions.assertEquals(true, restTalker.insertProfile("username", "title", "password"));

    JSONArray profiles = new JSONArray();
    JSONObject profile = new JSONObject();
    profile.put("username", "username");
    profile.put("title", "title");
    profile.put("password", "password");
    profile.put("id", 0);
    profiles.add(profile);
    Assertions.assertEquals(profiles.toJSONString(), restTalker.getProfiles());
  }

  // Testing uservalidator and deleting a profile
  @Test
  public void testDeleteProfile() throws ServerResponseException, InterruptedException, ExecutionException, URISyntaxException {

    Assertions.assertEquals("OK", restTalker.userValidator("test", "Test1!", "Test1!"));
    Assertions.assertEquals("Password must contain: uppercase letter, lowercase letter, digit and a symbol", restTalker.userValidator("test", "Testtest", "Testtest"));
    Assertions.assertEquals("Password is too short", restTalker.userValidator("test", "T3s!", "T3s!"));

    restTalker.registerUser("test", "test");
    restTalker.login("test", "test");

    Assertions.assertEquals(true, restTalker.insertProfile("username", "title", "password"));
    
    JSONArray profiles = new JSONArray();
    JSONObject profile = new JSONObject();
    profile.put("username", "username");
    profile.put("title", "title");
    profile.put("password", "password");
    profile.put("id", 0);
    profiles.add(profile);
    Assertions.assertEquals(profiles.toJSONString(), restTalker.getProfiles());

    Assertions.assertEquals(true, restTalker.deleteProfile(0));
    Assertions.assertEquals("[]", restTalker.getProfiles());
  }
}