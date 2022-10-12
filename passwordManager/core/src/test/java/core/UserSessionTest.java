package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UserSessionTest {
  UserSession userSession = UserSession.getInstance();
  File file = new File("src/main/resources/core/TestUsers.json");

  public UserSessionTest() {
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
}
