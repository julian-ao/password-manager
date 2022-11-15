package client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;

public class RestTalkerTest {

  @Test
  public void test() {
    RestTalker restTalker = new RestTalker();
    restTalker.doDatabaseTest();
    restTalker.registerUser("Admin", "Admin1!");
    Assertions.assertEquals("Success", restTalker.login("Admin", "Admin1!"));
  }

  // insert profile
  @Test
  public void test2() {
    RestTalker restTalker = new RestTalker();
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
    RestTalker restTalker = new RestTalker();
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
    RestTalker restTalker = new RestTalker();
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
    RestTalker restTalker = new RestTalker();
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

}