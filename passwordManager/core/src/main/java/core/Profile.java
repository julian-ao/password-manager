package core;

/**
 * Profile is a class that represents a password profile.
 */
public class Profile {
  private String title;
  private String profileUsername;
  private String encryptedPassword;
  private String nonceHex;
  private String parent;

  public Profile() {

  }

  /**
   * Profile is a constructor for the Profile class.
   * 
   * @param url               the url of the profile
   * @param title             the title of the profile
   * @param profileUsername   the username of the profile
   * @param encryptedPassword the encrypted password of the profile
   */
  public Profile(String title, String profileUsername, String encryptedPassword, String parent, String nonce) {
    this.title = title;
    this.profileUsername = profileUsername;
    this.encryptedPassword = encryptedPassword;
    this.parent = parent;
    this.nonceHex = nonce;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setProfileUsername(String profileUsername) {
    this.profileUsername = profileUsername;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public void setNonceHex(String nonce) {
    this.nonceHex = nonce;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getParent() {
    return this.parent;
  }

  public String getTitle() {
    return this.title;
  }

  public String getProfileUsername() {
    return this.profileUsername;
  }

  public String getEncryptedPassword() {
    return this.encryptedPassword;
  }

  public String getNonceHex() {
    return this.nonceHex;
  }

  /**
   * toString is a method that returns a string representation of the Profile.
   */
  public String toString() {
    return " title: " + this.title
        + " profileUsername: " + this.profileUsername
        + " encryptedPassword: " + this.encryptedPassword;
  }
}
