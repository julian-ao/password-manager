package client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;


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
import org.junit.jupiter.api.Test;

public class RestTalkerTest {

  private WireMockServer mockServer;

  private RestTalker restTalker;

  @BeforeEach
  public void startWireMockServer() {
      WireMockConfiguration mockConfig = WireMockConfiguration.wireMockConfig().port(8080);
      mockServer = new WireMockServer(mockConfig.portNumber());
      mockServer.start();
      WireMock.configureFor("localhost", mockConfig.portNumber());
      restTalker = new RestTalker();
  }

  @AfterEach
    public void stopWireMockServer() {
        mockServer.stop();
    }

    @Test
    public void testLogClient() {

        String body =
                "{\"title\": \"Example title\",\"comment\": \"Example comment\",\"date\": \"2021-10-25\",\"feeling\": \"7\",\"duration\": \"3600\",\"distance\": \"3\",\"maxHeartRate\": \"150\",\"exerciseCategory\": \"STRENGTH\",\"exerciseSubCategory\": \"PULL\"}";
        stubFor(get(urlEqualTo("/api/v1/entries/0"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)));
        try {
            HashMap<String, String> entry = logClient.getLogEntry("0");
            assertEquals("Example title", entry.get("title"));
            assertEquals("Example comment", entry.get("comment"));
            assertEquals("2021-10-25", entry.get("date"));
            assertEquals("7", entry.get("feeling"));
            assertEquals("3600", entry.get("duration"));
            assertEquals("3", entry.get("distance"));
            assertEquals("150", entry.get("maxHeartRate"));
            assertEquals("STRENGTH", entry.get("exerciseCategory"));
            assertEquals("PULL", entry.get("exerciseSubCategory"));

        } catch (URISyntaxException | InterruptedException | ExecutionException | ServerResponseException e) {
            e.printStackTrace();
            fail();
        }

    }
/* 
  @Test
  public void test() {
    restTalker.doDatabaseTest();
    restTalker.registerUser("Admin", "Admin1!");
    Assertions.assertEquals("Success", restTalker.login("Admin", "Admin1!"));
  }

  // insert profile
  @Test
  public void test2() {
    restTalker.doDatabaseTest();
    restTalker.registerUser("Admin", "Admin1!");
    restTalker.login("Admin", "Admin1!");
    restTalker.setLoggedIn("Admin", "Admin1!");
    Assertions.assertEquals(true, restTalker.insertProfile("Admin", "Facebook", "Facebook1!"));
    Assertions.assertEquals("Success", restTalker.login("Admin", "Admin1!"));
  }

  // delete profile
  @Test
  public void test3() {
    restTalker.doDatabaseTest();
    restTalker.registerUser("Admin", "Admin1!");
    restTalker.login("Admin", "Admin1!");
    restTalker.setLoggedIn("Admin", "Admin1!");
    restTalker.insertProfile("Admin", "Facebook", "Facebook1!");
    Assertions.assertEquals("Success", restTalker.login("Admin", "Admin1!"));
    Assertions.assertEquals(true, restTalker.deleteProfile("Admin", "Facebook", "Facebook", "Facebook1!"));
    Assertions.assertEquals("Success", restTalker.login("Admin", "Admin1!"));
  }

  // user validator
  @Test
  public void test4() {
    restTalker.doDatabaseTest();
    restTalker.registerUser("Admin", "Admin1!");
    restTalker.login("Admin", "Admin1!");
    restTalker.setLoggedIn("Admin", "Admin1!");
    Assertions.assertEquals("Success", restTalker.login("Admin", "Admin1!"));
    Assertions.assertEquals("Username is allready taken", restTalker.userValidator("Admin", "Admin1!", "Admin1!"));
  }

  // get profile and get username test
  @Test
  public void test5() {
    restTalker.doDatabaseTest();
    restTalker.registerUser("Admin", "Admin1!");
    restTalker.login("Admin", "Admin1!");
    restTalker.setLoggedIn("Admin", "Admin1!");
    restTalker.insertProfile("Admin", "Facebook", "Facebook1!");
    Assertions.assertEquals("Success", restTalker.login("Admin", "Admin1!"));
    Assertions.assertEquals("[{\"password\":\"Facebook1!\",\"title\":\"Facebook\",\"username\":\"Admin\"}]",
        restTalker.getProfiles());
    Assertions.assertEquals("Admin", restTalker.getUsername());
  }

  @Test
  public void badUrlTest() {
    class BadRestTalker extends RestTalker {
      @Override
      public String getUrl() {
        return "someverybadurlthat does not exists";
      }
    }
    BadRestTalker badRestTalker = new BadRestTalker();
    assertEquals("error", badRestTalker.login("user", "user3"));

  }
  */
}