package localpersistence;

import core.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class JsonTalker {

  private File usersFile;
  private File profilesFile;
  private ArrayList<User> users;
  private ArrayList<Profile> profiles;

  public JsonTalker(Path path) {
    this.usersFile = new File(path.toString() + "/users.json");
    this.profilesFile = new File(path.toString() + "/profiles.json");
  }

  private void loadData() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      users = new ArrayList<User>(Arrays.asList(mapper.readValue(usersFile, User[].class)));
      profiles = new ArrayList<Profile>(
          Arrays.asList(mapper.readValue(profilesFile, Profile[].class)));
    } catch (Exception e) {
      // !Handle this
    }
  }

  private void storeData() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(usersFile, users.toArray());
      mapper.writeValue(profilesFile, profiles.toArray());
    } catch (Exception e) {
      // !Handle this
    }
  }

  public boolean userExists(String username) {
    loadData();

    for (User u : users) {
      if (u.getUsername().equals(username)) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<Profile> getProfiles(String username) {
    loadData();
    if (!userExists(username)) {
      return null;
    }

    ArrayList<Profile> result = new ArrayList<Profile>();

    for (Profile p : profiles) {
      if (p.getParent().equals(username)) {
        result.add(p);
      }
    }

    return result;

  }

  public boolean checkPassword(String username, String password) {// !password should be bytearray when hashing gets
                                                                  // implemented
    loadData();
    for (User u : users) {
      if (u.getUsername().equals(username)) {
        if (u.getPassword().equals(password)) {
          return true;
        }
      }
    }
    return false;
  }

  public void insertUser(User user) {
    loadData();
    users.add(user);
    storeData();
  }

  public void insertProfile(Profile profile) {
    loadData();
    if (userExists(profile.getParent())) {
      profiles.add(profile);
    }
    storeData();
  }

  private boolean isSameProfile(Profile p1, Profile p2) {
    loadData();
    return p1.getEmail().equals(p2.getEmail()) &&
        p1.getProfileUsername().equals(p2.getProfileUsername()) &&
        p1.getEncryptedPassword().equals(p2.getEncryptedPassword()) &&
        p1.getUrl().equals(p2.getUrl()) &&
        p1.getParent().equals(p2.getParent());

  }

  public void deleteProfile(Profile profile) {
    loadData();
    Profile toDelete;
    for (Profile p : profiles) {
      if (isSameProfile(p, profile)) {
        toDelete = p;
      }
    }
    if (toDelete != null) {
      profiles.remove(toDelete);

    }
    storeData();
  }
}
