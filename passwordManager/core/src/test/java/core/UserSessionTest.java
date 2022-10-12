package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UserSessionTest {
  UserSession userSession = UserSession.getInstance();
  File file = new File("src/main/resources/core/TestUsers.json");

  public UserSessionTest() {
    this.initDatabase();
  }

  public void initDatabase() {
    file.delete();
    userSession.overridePath(file.toString());
  }

  protected void finalize() {
    file.delete();
  }

  private boolean hasProfile(ArrayList<Profile> profiles, Profile profile) {
    for (Profile p : profiles) {
      if (p.getUrl().equals(profile.getUrl()) &&
          p.getEmail().equals(profile.getEmail()) &&
          p.getProfileUsername().equals(profile.getProfileUsername()) &&
          p.getEncryptedPassword().equals(profile.getEncryptedPassword())) {
        return true;
      }
    }
    return false;
  }

  @Test
  public void insertProfileTest() {
    this.initDatabase();
    userSession.getDatabaseTalker().insertUser(new User("Admin", "Admin1!"));

    userSession.login("Admin", "Admin1!");
    ArrayList<Profile> profiles = userSession.getProfiles();
    ArrayList<ArrayList<String>> profilesStr = userSession.getProfilesNativeTypes();
    userSession.insertProfile("user", "stuff", "more stuffv");
    ArrayList<Profile> profiles1 = userSession.getProfiles();
    ArrayList<ArrayList<String>> profilesStr1 = userSession.getProfilesNativeTypes();
    assertEquals(true, profiles1.size() > profiles.size());

    System.out.println();

  }

  @Test
  public void loginTest() {
    this.initDatabase();
    User user1 = new User("User1", "password1");
    User user2 = new User("User2", "password2");

    userSession.getDatabaseTalker().insertUser(user1);
    assertEquals(true, userSession.login(user1.getUsername(), user1.getPassword()));
    userSession.logOut();
    assertEquals(false, userSession.login(user1.getUsername(), "password2"));
    assertEquals(false, userSession.login("not a user", "password"));
    assertEquals(false, userSession.login(user2.getUsername(), user2.getPassword()));
    userSession.getDatabaseTalker().insertUser(user2);
    assertEquals(true, userSession.login(user2.getUsername(), user2.getPassword()));

  }

  @Test
  public void registerUserTest() {
    this.initDatabase();
    assertEquals(false, userSession.login("user1", "password1"));
    userSession.registerUser("user1", "password1");
    assertEquals(true, userSession.login("user1", "password1"));

  }

  @Test
  public void userValidatorTest() {
    this.initDatabase();
    userSession.registerUser("user1", "Password1");
    assertEquals(
        "Passwords do not match",
        userSession.userValidator("someuser", "Password1!", "Password1!a"));
    assertEquals(
        "Username already taken",
        userSession.userValidator("user1", "Password1!", "Password1!"));
    assertEquals(
        "Username must be between 3 and 30 characters long and contain only letters and numbers",
        userSession.userValidator("us", "Password1!", "Password1!"));
    assertEquals(
        "Username must be between 3 and 30 characters long and contain only letters and numbers",
        userSession.userValidator("us", "Password1!", "Password"));
    assertEquals(
        "Password must be between 6 and 30 characters long and contain at least one lowercase letter, one uppercase letter, one number and one special character",
        userSession.userValidator("user123", "Pa", "Pa"));
    assertEquals(
        "OK",
        userSession.userValidator("someuser", "Password1!", "Password1!"));
  }
}
