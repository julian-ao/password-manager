package core;

/**
 * User is a class that represents an user of the application.
 */
public class User {
  private String username;
  private int salt;
  private int encryptionSalt;
  private String hashedPassword;

  public User() {

  }

  /**
   * User is a constructor for the User class.

   * @param username the username of the user
   * @param password the password of the user
   */
  public User(String username, String password) {
    this.username = username;
    this.hashedPassword = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.hashedPassword = password;
  }

  public void setSalt(int salt) {
    this.salt = salt;
  }

  public void setEncryptionSalt(int salt) {
    this.encryptionSalt = salt;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.hashedPassword;
  }

  public int getSalt() {
    return this.salt;
  }

  public int getEncryptionSalt() {
    return this.encryptionSalt;
  }

  /**
   * toString is a method that returns a string representation of the User.
   */
  public String toString() {
    return "username: " + this.username
        + " hashed Password: " + this.hashedPassword
        + " salt: " + this.salt;
  }
}
