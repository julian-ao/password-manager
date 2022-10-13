package core;

import java.util.ArrayList;

/**
 * User is a class that represents an user of the application.
 */
public class User {
  private String username;
  // !!!!MIDTLERTIDIG!!!!!
  private String password;
  private ArrayList<Profile> profiles;
  private int salt;
  private byte[] hashedPassword;

  public User() {
    profiles = new ArrayList<Profile>();
  }

  /**
   * User is a constructor for the User class.

   * @param username the username of the user
   * @param password the password of the user
   */
  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.profiles = new ArrayList<Profile>();
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setProfiles(ArrayList<Profile> profiles) {
    this.profiles = profiles;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public ArrayList<Profile> getProfiles() {
    return this.profiles;
  }

  public void addProfile(Profile profile) {
    this.profiles.add(profile);
  }

  public void removeProfile(Profile profile) {
    this.profiles.remove(profile);
  }

  /**
   * toString is a method that returns a string representation of the User.
   */
  public String toString() {
    return "username: " + this.username
        + " password: " + this.password
        + " profiles: " + this.profiles.toString();
  }
}
