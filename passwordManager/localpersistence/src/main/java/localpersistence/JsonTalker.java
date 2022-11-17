package localpersistence;

import core.*;

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
    ObjectMapper mapper = new ObjectMapper();
    final ArrayList<Integer> emptyList = new ArrayList<>();

    this.usersFile = new File(path.toString() + "/users.json");
    this.profilesFile = new File(path.toString() + "/profiles.json");
    if (!this.usersFile.exists()) {
      try {
        if (usersFile.createNewFile()) {
          System.out.print("File created successfully");
        } else {
          System.out.print("File already exists");
        }
        mapper.writeValue(usersFile, emptyList.toArray());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (!this.profilesFile.exists()) {
      try {
        if (profilesFile.createNewFile()) {
          System.out.print("File created successfully.");
        } else {
          System.out.print("File already exists.");
        }
        mapper.writeValue(profilesFile, emptyList.toArray());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  private void loadData() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    users = new ArrayList<>();
    profiles = new ArrayList<>();
    try {
      users = new ArrayList<User>(Arrays.asList(mapper.readValue(usersFile, User[].class)));
      profiles = new ArrayList<Profile>(Arrays.asList(mapper.readValue(profilesFile, Profile[].class)));
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

    if (users.size() > 0) {
      for (User u : users) {
        if (u.getUsername().equals(username)) {
          return true;
        }
      }
      return false;
    } else
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

  public boolean checkPassword(String username, String hashedPassword) throws IOException {// !password should be
                                                                                           // bytearray
    // when hashing gets
    // implemented
    loadData();
    for (User u : users) {
      if (u.getUsername().equals(username)) {
        if (u.getPassword().equals(hashedPassword)) {
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
    return p1.getId() == p2.getId();

  }

  public void deleteProfile(String string, Profile profile) throws IOException {
    loadData();

    List<Profile> newProfiles = profiles.stream().filter((x) -> !isSameProfile(x, profile)).toList();
    profiles = newProfiles;
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

  public User getUser(String username) throws IOException {
    loadData();
    User user = new User();
    for (User u : users) {
      if (u.getUsername().equals(username)) {
        user.setUsername(username);
        user.setPassword(u.getPassword());
        user.setSalt(u.getSalt());
        user.setEncryptionSalt(u.getEncryptionSalt());
      }
    }
    storeData();
    return user;
  }

  @Override
  public int getNextProfileId() throws IOException {
    loadData();
    int max = 0;
    for (Profile p : profiles) {
      if (p.getId() > max)
        max = p.getId();
    }
    return max + 1;
  }
}
