package localpersistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import core.Profile;
import core.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class JsonTalkerTest {

  JsonTalker jsonTalker;
  String path = "../localpersistence/src/resources/localpersistance/test";
  File file = new File(path);

  private void resetFile() {
    File jsonFile = new File(path);

    jsonTalker = new JsonTalker(jsonFile.toPath());

    jsonTalker.resetFiles();
  }

  private void user1Profiles() {
    try {
      jsonTalker.insertProfile("tmp", new Profile("s@.n.n", "brukernavn", "pass", "user1", "a"));
      jsonTalker.insertProfile("tmp", new Profile("s@.n.n", "brukernavn1", "pass", "user1", "a"));
      jsonTalker.insertProfile("tmp", new Profile("s@.n.n", "brukernavn2", "pass", "user1", "a"));
      jsonTalker.insertProfile("tmp", new Profile("s@.n.n", "brukernavn3", "pass", "user1", "a"));

    } catch (Exception e) {

    }
  }

  public JsonTalkerTest() {
    resetFile();

    // Insert a user into the json file
    Profile profile1 = new Profile("bob@bob.mail", "profile1", "password1", "user1", "a");
    Profile profile2 = new Profile("bob@bob.mail", "profile2", "password2", "user1", "a");
    Profile profile3 = new Profile("bob@bob.mail", "profile3", "password3", "user1", "a");
    Profile profile4 = new Profile("bob@bob.mail", "profile4", "password4", "user1", "a");
    User user = new User("user1", "password1");

    ArrayList<Profile> profiles = new ArrayList<Profile>();
    profiles.add(profile1);
    profiles.add(profile2);
    profiles.add(profile3);
    profiles.add(profile4);

    try {
      jsonTalker.insertUser(user);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void userExistsTest() {
    resetFile();
    try {
      jsonTalker.insertUser(new User("user1", "password1"));
      assertEquals(true, jsonTalker.userExists("user1"));
      assertEquals(false, jsonTalker.userExists("NOTAUSER"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void insertUserTest() {
    resetFile();
    User newUser = new User("user2", "password2");
    try {
      jsonTalker.insertUser(newUser);
      assertEquals(true, jsonTalker.userExists(newUser.getUsername()));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void deleteUserTest() {
    resetFile();
    User newUser = new User("user2", "password2");
    try {
      jsonTalker.insertUser(newUser);
      assertEquals(true, jsonTalker.userExists(newUser.getUsername()));
      jsonTalker.deleteUser(newUser.getUsername());
      assertEquals(false, jsonTalker.userExists(newUser.getUsername()));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private boolean hasProfile(ArrayList<Profile> profiles, Profile profile) {
    for (Profile p : profiles) {
      if (p.getTitle().equals(profile.getTitle()) &&
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
    try {
      jsonTalker.insertUser(new User("user1", "password1"));
      Profile profile = new Profile("sondrkol@it.no", "sondrkol", "passord", "user1", "a");
      jsonTalker.insertProfile("user1", profile);
      ArrayList<Profile> profiles = jsonTalker.getProfiles("user1");
      assertEquals(true, hasProfile(profiles, profile));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void checkPasswordTest() {
    resetFile();
    try {
      jsonTalker.insertUser(new User("user1", "password1"));
      assertEquals(true, jsonTalker.checkPassword("user1", "password1"));
      assertEquals(false, jsonTalker.checkPassword("user1", "password2"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void getProfilesTest() {
    ArrayList<Profile> profiles;
    user1Profiles();
    try {
      profiles = jsonTalker.getProfiles("user1");
      assertEquals(4, profiles.size());

      profiles = jsonTalker.getProfiles("NOTAUSSERTHATEXISSTSINOURDATABAASE");
      assertEquals(profiles, null);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void deleteProfileTest() {

    ArrayList<Profile> profiles;
    user1Profiles();
    try {
      profiles = jsonTalker.getProfiles("user1");
      Profile profile = profiles.get(0);
      assertEquals(true, hasProfile(profiles, profile));
      jsonTalker.deleteProfile("user1", profile);
      profiles = jsonTalker.getProfiles("user1");
      assertEquals(false, hasProfile(profiles, profile));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void constructorTest() throws IOException {
    File usersFile = new File(path + "/users.json");
    File profilesFile = new File(path + "/profiles.json");
    usersFile.createNewFile();
    profilesFile.createNewFile();
    DatabaseTalker jsonTalker = new JsonTalker(file.toPath());

    usersFile.delete();
    profilesFile.delete();

    DatabaseTalker jsonTalker1 = new JsonTalker(file.toPath());
  }

  @Test
  public void getUserTest() throws IOException {
    jsonTalker.insertUser(new User("username", "hashedPassword"));
    Assertions.assertThat(jsonTalker.getUser("username").getPassword().equals("hashedPassword"));
  }
}
