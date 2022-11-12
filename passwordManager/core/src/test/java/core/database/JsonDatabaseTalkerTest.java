package core.database;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import core.Profile;
import core.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class JsonDatabaseTalkerTest {

  DatabaseTalker jsonDatabaseTalker;
  String path = "../localpersistence/src/resources/localpersistance/TestUsers.json";
  File file = new File(path);

  private void start() {
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
    try {
      newMainFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    jsonDatabaseTalker = new JsonDatabaseTalker(url);
  }

  private void stop() {
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

  public JsonDatabaseTalkerTest() {
    start();

    // Insert a user into the json file
    Profile profile1 = new Profile( "bob@bob.mail", "profile1", "password1", "user1");
    Profile profile2 = new Profile( "bob@bob.mail", "profile2", "password2", "user1");
    Profile profile3 = new Profile( "bob@bob.mail", "profile3", "password3", "user1");
    Profile profile4 = new Profile( "bob@bob.mail", "profile4", "password4", "user1");
    User user = new User("user1", "password1");

    ArrayList<Profile> profiles = new ArrayList<Profile>();
    profiles.add(profile1);
    profiles.add(profile2);
    profiles.add(profile3);
    profiles.add(profile4);

    user.setProfiles(profiles);

    jsonDatabaseTalker.insertUser(user);

    stop();
  }

  @Test
  public void userExistsTest() {
    start();
    jsonDatabaseTalker.insertUser(new User("user1", "password1"));
    assertEquals(true, jsonDatabaseTalker.userExists("user1"));
    assertEquals(false, jsonDatabaseTalker.userExists("NOTAUSER"));
    stop();
  }

  @Test
  public void insertUserTest() {
    start();
    User newUser = new User("user2", "password2");
    jsonDatabaseTalker.insertUser(newUser);
    assertEquals(true, jsonDatabaseTalker.userExists(newUser.getUsername()));
    stop();
  }

  @Test
  public void deleteUserTest() {
    start();
    User newUser = new User("user2", "password2");
    jsonDatabaseTalker.insertUser(newUser);
    assertEquals(true, jsonDatabaseTalker.userExists(newUser.getUsername()));
    jsonDatabaseTalker.deleteUser(newUser.getUsername());
    assertEquals(false, jsonDatabaseTalker.userExists(newUser.getUsername()));
    stop();
  }

  private boolean hasProfile(ArrayList<Profile> profiles, Profile profile) {
    for (Profile p : profiles) {
      if (
          p.getTitle().equals(profile.getTitle()) &&
          p.getProfileUsername().equals(profile.getProfileUsername()) &&
          p.getEncryptedPassword().equals(profile.getEncryptedPassword())) {
        return true;
      }
    }
    return false;
  }

  @Test
  public void insertProfileTest() {
    start();
    jsonDatabaseTalker.insertUser(new User("user1", "password1"));
    Profile profile = new Profile( "sondrkol@it.no", "sondrkol", "passord", "user1");
    jsonDatabaseTalker.insertProfile("user1", profile);
    ArrayList<Profile> profiles = jsonDatabaseTalker.getProfiles("user1");
    assertEquals(true, hasProfile(profiles, profile));
    stop();
  }

  @Test
  public void checkPasswordTest() {
    start();
    jsonDatabaseTalker.insertUser(new User("user1", "password1"));
    assertEquals(true, jsonDatabaseTalker.checkPassword("user1", "password1"));
    assertEquals(false, jsonDatabaseTalker.checkPassword("user1", "password2"));
    stop();
  }

  // test for deleteProfile(String, Profile) is in JsonDatabaseTalkerTest.java
  @Test
  public void testDeleteProfile() {
    start();
    jsonDatabaseTalker.insertUser(new User("user1", "password1"));
    Profile profile = new Profile("test", "test", "test", "user1");
    jsonDatabaseTalker.insertProfile("user1", profile);
    ArrayList<Profile> profiles = jsonDatabaseTalker.getProfiles("user1");
    assertEquals(true, hasProfile(profiles, profile));
    jsonDatabaseTalker.deleteProfile("user1", profile);
    profiles = jsonDatabaseTalker.getProfiles("user1");
    assertEquals(false, hasProfile(profiles, profile));
    stop();
  }

  // test for isSameProfile(Profile, Profile) is in JsonDatabaseTalkerTest.java
  @Test
  public void testIsSameProfile() {
    start();
    Profile profile1 = new Profile("test", "test", "test", "user1");
    Profile profile2 = new Profile("test", "test", "test", "user1");
    Profile profile3 = new Profile("test", "test", "test", "user2");
    assertEquals(true, jsonDatabaseTalker.isSameProfile(profile1, profile2));
    assertEquals(false, jsonDatabaseTalker.isSameProfile(profile1, profile3));
    stop();
  }
}
