package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UserSessionTest {
  UserSession userSession = UserSession.getInstance();
  File file = new File("../localpersistence/src/resources/localpersistance/TestUsers.json");

  private void resetFile() {
    if (file.exists()) {
      file.delete();
    }
    File jsonFile = new File(file.toString());
    try {
      jsonFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    userSession.overridePath(file.toString());
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
    resetFile();
    userSession.getDatabaseTalker().insertUser(new User("Admin", "Admin1!"));

    userSession.login("Admin", "Admin1!");
    ArrayList<Profile> profiles = userSession.getProfiles();
    ArrayList<ArrayList<String>> profilesStr = userSession.getProfilesNativeTypes();
    userSession.insertProfile("user", "stuff", "more stuffv");
    ArrayList<Profile> profiles1 = userSession.getProfiles();
    ArrayList<ArrayList<String>> profilesStr1 = userSession.getProfilesNativeTypes();
    
    assertEquals(profilesStr1.size(), profilesStr.size() + 1);
    assertEquals(true, profiles1.size() > profiles.size());
  }

  @Test
  public void deleteProfileTest() {
    resetFile();
    userSession.getDatabaseTalker().insertUser(new User("Admin", "Admin1!"));

    userSession.login("Admin", "Admin1!");
    ArrayList<Profile> profiles = userSession.getProfiles();
    ArrayList<ArrayList<String>> profilesStr = userSession.getProfilesNativeTypes();


    Profile profileToDelete = new Profile("empty.url", "google", "user", "passord", "Admin");
    userSession.insertProfile("user", "google", "passord");

    userSession.deleteProfile("Admin", profileToDelete);
    ArrayList<Profile> profiles2 = userSession.getProfiles();

    ArrayList<ArrayList<String>> profilesStr2 = userSession.getProfilesNativeTypes();
    
    assertEquals(profilesStr2.size(), profilesStr.size());
    assertEquals(true, profiles2.size() == profiles.size());
  }

  @Test
  public void loginTest() {
    resetFile();
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
    resetFile();

    assertEquals(false, userSession.login("user1", "password1"));
    userSession.registerUser("user1", "password1");
    assertEquals(true, userSession.login("user1", "password1"));

  }

  @Test
  public void userValidatorTest() {
    resetFile();

    userSession.registerUser("user1", "Password1");

    String[][] tests = {
        { "OK", "User", "Password1!", "Password1!" },
        { "Username is allready taken", "user1", "Password1!", "Password1!" },
        { "Username must only contain letters and digits", "User#", "Password1!", "Password1!" },
        { "Username is too short", "Us", "Password1!", "Password1!" },
        { "Username is too long", "Verylongussernametotriggersomemessage", "Password1!", "Password1!" },
        { "Password must contain: uppercase letter, lowercase letter, digit and a symbol", "User", "passwo", "passwo" },
        { "Password is too short", "User", "Pa1!", "Pa1!" },
        { "Password is too long", "User", "Very!#longpasswordthatisabove30whichisthelimit",
            "Very!#longpasswordthatisabove30whichisthelimit" },
        { "Passwords does not match", "User", "Password1!", "Password1!2" }
    };
    for (String[] test : tests) {
      assertEquals(test[0], userSession.userValidator(test[1], test[2], test[3]));
    }
  }
}
