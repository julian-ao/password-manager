package restserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = PasswordManagerController.class)
@AutoConfigureMockMvc
public class PasswordManagerApplicationTest {

  private final static String path = "/api/v1/entries";

  @Autowired
  private MockMvc mMvc;
  @Autowired
  private PasswordManagerController controller;

  @BeforeEach
  public void reset() {
    try {
      mMvc.perform(post(path + "/doDatabaseTest"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  public void resetAfter() {
    try {
      mMvc.perform(post(path + "/doPrdDB"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAppMainMethod() {
    PasswordManagerApplication.main();
  }

  @Test
  public void testContextLoads() {
    assertNotNull(controller);
  }

  // Wrong login
  @Test
  public void testLogin() {
    try {
      ResultActions resultActions = mMvc.perform(get(path + "/login?username=test&password=test"));
      MvcResult result = resultActions.andReturn();
      String content = result.getResponse().getContentAsString();
      Assertions.assertEquals("Failure", content);
    } catch (Exception e) {
      fail();
    }

  }

  // Create a new user and login and uservalidator
  @Test
  public void testCreateUser() {
    try {
      ResultActions resultActions = mMvc.perform(
          post(path + "/register").contentType("application/json").content("{'username':'test','password':'test'}"));
      Assertions.assertEquals("Success", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }

    try {
      ResultActions resultActions = mMvc.perform(
          post(path + "/register").contentType("application/json").content("{'username':'test','password':'test'}"));
      Assertions.assertEquals("Failure", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }

    try {
      ResultActions resultActions = mMvc.perform(get(path + "/login?username=test&password=test"));
      MvcResult result = resultActions.andReturn();
      String content = result.getResponse().getContentAsString();
      Assertions.assertEquals("Success", content);
    } catch (Exception e) {
      fail();
    }

    try {
      ResultActions resultActions = mMvc
          .perform(get(path + "/userValidator?username=test&password=T3s!&passwordRepeat=T3s!"));
      Assertions.assertEquals("Password is too short", resultActions.andReturn().getResponse().getContentAsString());

      resultActions = mMvc
          .perform(get(path + "/userValidator?username=test1&password=Testtest1&passwordRepeat=Testtest1"));
      Assertions.assertEquals("Password must contain: uppercase letter, lowercase letter, digit and a symbol",
          resultActions.andReturn().getResponse().getContentAsString());

      resultActions = mMvc.perform(get(path
          + "/userValidator?username=test1&password=A1!aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa&passwordRepeat=A1!aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
      Assertions.assertEquals("Password is too long", resultActions.andReturn().getResponse().getContentAsString());

      resultActions = mMvc
          .perform(get(path + "/userValidator?username=test2&password=Testtest1!&passwordRepeat=Testtest1!"));
      Assertions.assertEquals("OK", resultActions.andReturn().getResponse().getContentAsString());

      resultActions = mMvc
          .perform(get(path + "/userValidator?username=test2!&password=Testtest1!&passwordRepeat=Testtest1!"));
      Assertions.assertEquals("Username must only contain letters and digits",
          resultActions.andReturn().getResponse().getContentAsString());

      resultActions = mMvc
          .perform(get(path + "/userValidator?username=te&password=Testtest1!&passwordRepeat=Testtest1!"));
      Assertions.assertEquals("Username is too short", resultActions.andReturn().getResponse().getContentAsString());

      resultActions = mMvc
          .perform(get(path + "/userValidator?username=test3&password=Testtsest1!&passwordRepeat=Testtest1!"));
      Assertions.assertEquals("Passwords does not match", resultActions.andReturn().getResponse().getContentAsString());

    } catch (Exception e) {
      fail();
    }
  }

  // Create a new user and create a profile and get the profile
  @Test
  public void testCreateProfile() {
    try {
      ResultActions resultActions = mMvc.perform(
          post(path + "/register").contentType("application/json").content("{'username':'test','password':'test'}"));
      Assertions.assertEquals("Success", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
    try {
      ResultActions resultActions = mMvc.perform(post(path + "/insertProfile").contentType("application/json").content(
          "{'username':'test','title':'test','password':'test','parentUsername':'test','parentPassword':'test'}"));
      Assertions.assertEquals("Success", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }

    try {
      ResultActions resultActions = mMvc.perform(post(path + "/insertProfile").contentType("application/json")
          .content("{'username':'test','title':'test','password':'test','parentPassword':'test'}"));
      Assertions.assertEquals("Failure", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
  }

  // tests getProfile(username, password)
  @Test
  public void testGetProfile() {
    try {
      ResultActions resultActions = mMvc.perform(
          post(path + "/register").contentType("application/json").content("{'username':'test','password':'test'}"));
      Assertions.assertEquals("Success", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
    try {
      ResultActions resultActions = mMvc.perform(
          post(path + "/register").contentType("application/json").content("{'username':'test','password':'test'}"));
      Assertions.assertEquals("Failure", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
    try {
      ResultActions resultActions = mMvc.perform(post(path + "/insertProfile").contentType("application/json").content(
          "{'username':'test','title':'test','password':'test','parentUsername':'test','parentPassword':'test'}"));
      Assertions.assertEquals("Success", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
    try {
      ResultActions resultActions = mMvc.perform(post(path + "/insertProfile").contentType("application/json").content(
          "{'username':'test','title':'test','password':'test','parentUsername':'testtt','parentPassword':'test'}"));
      Assertions.assertEquals("Failure", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
    try {
      ResultActions resultActions = mMvc.perform(get(path + "/getProfiles?username=test&password=test"));
      Assertions.assertEquals("[{\"password\":\"test\",\"id\":0,\"title\":\"test\",\"username\":\"test\"}]",
          resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
  }

  // delete profile
  @Test
  public void testDeleteProfile() {
    try {
      ResultActions resultActions = mMvc.perform(
          post(path + "/register").contentType("application/json").content("{'username':'test','password':'test'}"));
      Assertions.assertEquals("Success", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
    try {
      ResultActions resultActions = mMvc.perform(post(path + "/insertProfile").contentType("application/json").content(
          "{'username':'test','title':'test','password':'test','parentUsername':'test','parentPassword':'test'}"));
      Assertions.assertEquals("Success", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }
    try {
      ResultActions resultActions = mMvc.perform(post(path + "/deleteProfile").contentType("application/json")
          .content("{'username':'test','title':'test','password':'test','userPassword':'test','user':'test', 'id':0}"));
      Assertions.assertEquals("Success", resultActions.andReturn().getResponse().getContentAsString());
    } catch (Exception e) {
      fail();
    }

  }
}
