package localpersistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Profile;
import core.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for reading and writing to the JSON file.
 */
public class JsonTalker implements DatabaseTalker {

  private File usersFile;
  private File profilesFile;
  private List<User> users;
  private List<Profile> profiles;


  /**
   * Constructor for JsonTalker.

   * @param path the path to the JSON fil
   */
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

  /**
   * Deserialize the data from the json file.
   *
   * @throws IOException if the file cannot be read
   */
  private void loadData() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    users = new ArrayList<>();
    profiles = new ArrayList<>();
    try {
      users = new ArrayList<User>(Arrays.asList(mapper.readValue(usersFile, User[].class)));
      profiles =
          new ArrayList<Profile>(Arrays.asList(mapper.readValue(profilesFile, Profile[].class)));
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * This method is responsible for writing to the JSON file.
   *
   * @throws IOException if the file cannot be written to
   */
  private void storeData() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(usersFile, users.toArray());
      mapper.writeValue(profilesFile, profiles.toArray());
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }

  }

  /**
   * This method is responsible for adding empty profiles to the database.
   */
  public void resetFiles() {
    try {
      users = new ArrayList<>();
      profiles = new ArrayList<>();
      storeData();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
    * Checks if a user with a given username exists.

    * @param username of a user
    * @return true if the user exists, false otherwise
   */
  public boolean userExists(String username) throws IOException {
    loadData();
    return users.stream().anyMatch(u -> u.getUsername().equals(username));
  }

  
  /**
   * This method return every profile that is stored in the database to the user.

   * @param username username to be checked
   * @return a list of profiles
   */
  public ArrayList<Profile> getProfiles(String username) throws IOException {
    loadData();
    if (!userExists(username)) {
      return null;
    }

    return new ArrayList<Profile>(profiles.stream()
        .filter(p -> p.getParent().equals(username))
        .collect(Collectors.toList()));
  }

  /**
   * Checks if password is correct for a given user.

   * @param username username to be checked
   * @param hashedPassword hashed password to be checked
   * @return true if password is correct, false otherwise
   */
  public boolean checkPassword(String username, String hashedPassword) throws IOException {
    loadData();
    return users.stream().anyMatch(
      u -> u.getUsername().equals(username) 
      && u.getPassword().equals(hashedPassword)
      );
  }

  /**
   * This method is responsible for adding a new user to the database.

   * @param user user to be added
   * @return true if user was added successfully, false otherwise
   */
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

  /**
   * This method is responsible for adding a new profile to the database.

   * @param profile profile to be added
   * @return true if the profile was added successfully, false otherwise
   */
  public boolean insertProfile(Profile profile) throws IOException {
    boolean success = false;
    loadData();
    if (userExists(profile.getParent())) {
      profiles.add(profile);
      success = true;
    }
    storeData();
    return success;
  }

  /**
   * Checks if two profiles are equal.
   *
   * @param p1 first profile
   * @param p2 second profile
   * @return true if profiles are equal, false otherwise
   */
  private boolean isSameProfile(Profile p1, Profile p2) {
    return p1.getId() == p2.getId();

  }

  /**
   * Delete a profile from the database.
   *
   * @param profile profile to be deleted
   */
  public void deleteProfile(String string, Profile profile) throws IOException {
    loadData();

    List<Profile> newProfiles =
        profiles.stream().filter((x) -> !isSameProfile(x, profile)).toList();
    profiles = newProfiles;
    storeData();
  }

  /**
   * Deletes a user from the database.
   *
   * @param username username to be deleted
   */
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

  /**
   * getUser returns the user with the given username.
   *
   * @param username the username of the user to be returned
   * @return the user with the given username
   */
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
      if (p.getId() >= max)
        max = p.getId() + 1;
    }
    return max;
  }
}
