package localpersistence;

import core.*;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonTalker implements DatabaseTalker {

  private File usersFile;
  private File profilesFile;
  private List<User> users;
  private List<Profile> profiles;

  public JsonTalker(Path path) {
    this.usersFile = new File(path.toString() + "/users.json");
    this.profilesFile = new File(path.toString() + "/profiles.json");
    if (!this.usersFile.exists()) {
      try {
        usersFile.createNewFile();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    if (!this.profilesFile.exists()) {
      try {
        profilesFile.createNewFile();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private void loadData() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      users = new ArrayList<User>(Arrays.asList(mapper.readValue(usersFile, User[].class)));
      profiles = new ArrayList<Profile>(
          Arrays.asList(mapper.readValue(profilesFile, Profile[].class)));
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  private void storeData() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(usersFile, users.toArray());
      mapper.writeValue(profilesFile, profiles.toArray());
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }

  }

  public void resetFiles() {
    try {
      users = new ArrayList<>();
      profiles = new ArrayList<>();
      storeData();
    } catch (Exception e) {
      // BRRRRRRR
    }
  }

  public boolean userExists(String username) throws IOException {
    loadData();

    for (User u : users) {
      if (u.getUsername().equals(username)) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<Profile> getProfiles(String username) throws IOException {
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

  public boolean checkPassword(String username, String password) throws IOException {// !password should be bytearray
                                                                                     // when hashing gets
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

  public boolean insertUser(User user) throws IOException {
    loadData();
    users.add(user);
    try {
      storeData();
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public boolean insertProfile(String string, Profile profile) throws IOException {
    boolean success = false;
    loadData();
    if (userExists(profile.getParent())) {
      profiles.add(profile);
      success = true;
    }
    storeData();
    return success;
  }

  private boolean isSameProfile(Profile p1, Profile p2) {
    return p1.getEmail().equals(p2.getEmail()) &&
        p1.getProfileUsername().equals(p2.getProfileUsername()) &&
        p1.getEncryptedPassword().equals(p2.getEncryptedPassword()) &&
        p1.getUrl().equals(p2.getUrl()) &&
        p1.getParent().equals(p2.getParent());

  }

  public void deleteProfile(String string, Profile profile) throws IOException {
    loadData();
    profiles = profiles.stream().filter((x) -> !isSameProfile(x, profile)).toList();
    storeData();
  }

  public void deleteUser(String username) throws IOException {
    loadData();
    for (User u : users) {
      if (u.getUsername().equals(username)) {
        users.remove(u);
        break;
      }
    }
    profiles = profiles.stream().filter((x) -> !x.getParent().equals(username)).toList();
    storeData();
  }
}
