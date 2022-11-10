package core;

/**
 * Profile is a class that represents a password profile.
 */
public class Profile {
  private String email;
  private String profileUsername;
  private String encryptedPassword;
  private String parent;

  public Profile() {

  }

  /**
   * Profile is a constructor for the Profile class.
   * 
   * @param url               the url of the profile
   * @param email             the email of the profile
   * @param profileUsername   the username of the profile
   * @param encryptedPassword the encrypted password of the profile
   */
  public Profile(String email, String profileUsername, String encryptedPassword, String parent) {
    this.email = email;
    this.profileUsername = profileUsername;
    this.encryptedPassword = encryptedPassword;
    this.parent = parent;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setProfileUsername(String profileUsername) {
    this.profileUsername = profileUsername;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }
  
  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getParent() {
    return this.parent;
  }

  public String getEmail() {
    return this.email;
  }

  public String getProfileUsername() {
    return this.profileUsername;
  }

  public String getEncryptedPassword() {
    return this.encryptedPassword;
  }

  /**
   * toString is a method that returns a string representation of the Profile.
   */
  public String toString() {
    return " email: " + this.email
        + " profileUsername: " + this.profileUsername
        + " encryptedPassword: " + this.encryptedPassword;
  }
}
