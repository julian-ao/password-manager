package core;

import java.util.ArrayList;

import core.database.DatabaseTalker;
import core.database.JsonDatabaseTalker;
import core.userbuilder.PasswordValidation;
import core.userbuilder.UserBuilder;
import core.userbuilder.UsernameValidation;

/**
 * UserSession is a class that is used to manage the user session.
 */
public class UserSession {
  private User user;
  private static DatabaseTalker databaseTalker = new JsonDatabaseTalker(
      "../localpersistence/src/main/resources/localpersistance/Users.json");
  private static UserSession onlyInstance = new UserSession(databaseTalker);
  private UserBuilder userBuilder;

  private UserSession(DatabaseTalker databaseTalker) {
    this.databaseTalker = databaseTalker;
  }

  public static UserSession getInstance() {
    return onlyInstance;
  }

  /**
   * login function attempts to login.
   * 
   * @param username the username provided for login attempt
   * @param password the password provided for login attempt
   * @return true if the login was successful, and false if is was not
   */
  public boolean login(String username, String password) {
    if (databaseTalker.checkPassword(username, password)) {
      this.user = new User(username, password);
      user.setProfiles(databaseTalker.getProfiles(user.getUsername()));
      return true;
    } else {
      return false;
    }
  }

  public ArrayList<Profile> getProfiles() {
    return this.databaseTalker.getProfiles(user.getUsername());
  }

  public void overridePath(String path) {
    databaseTalker = new JsonDatabaseTalker(path);
  }

  /**
   * getProfiles returns the profiles of the user.
   * 
   * @return the profiles of the user
   */
  public ArrayList<ArrayList<String>> getProfilesNativeTypes() {
    ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    ArrayList<Profile> profiles = getProfiles();
    for (Profile profile : profiles) {
      ArrayList<String> p = new ArrayList<String>();
      p.add(profile.getProfileUsername());
      p.add(profile.getEmail());
      p.add(profile.getEncryptedPassword());
      result.add(p);
    }
    return result;
  }

  /**
   * registerUser attempts to register a new user in our database/storage.
   * 
   * @param username the username to register
   * @param password the passsword to register
   * @return true if the registration is successful and the user is stored, false
   *         if it is not
   */
  public boolean registerUser(String username, String password) {
    return this.databaseTalker.insertUser(new User(username, password));
  }

  public String getUsername() {
    return user.getUsername();
  }

  /**
   * logOut sets the user to null.
   */
  public void logOut() {
    this.user = null;
  }

  /**
   * userValidator that validates the user input for username and password.
   * 
   * @param username       a username to check
   * @param password       a password to check
   * @param passwordRepeat repeated password provided by the user
   * @return a string which contains the message explaining what why the
   *         username/password
   *         is not accepted, if the username and passwords are accepted,
   *         and the passwordRepeat == password, the function return "OK"
   */
  public String userValidator(String username, String password, String passwordRepeat) {
    UserBuilder userBuilder = new UserBuilder(databaseTalker);
    userBuilder.setUsername(username);
    userBuilder.setPassword(password);

    UsernameValidation usernameValidation = userBuilder.setUsername(username);
    PasswordValidation passwordValidation = userBuilder.setPassword(password);

    String message = null;
    if (!password.equals(passwordRepeat)) {
      message = "Passwords does not match";
    }
    switch (usernameValidation) {
      case OK:
        switch (passwordValidation) {
          case OK:
            if (message == null) {
              message = "OK";
            }
            break;
          case diversityError:
            message = "Password must contain: uppercase letter, lowercase letter, digit and a symbol";
            break;
          case tooShort:
            message = "Password is too short";
            break;
          case tooLong:
            message = "Password is too long";
            break;
          default:
            message = "something went wrong with the validator";
            break;
        }
        break;
      case alreadyTaken:
        message = "Username is allready taken";
        break;
      case invalidCharacters:
        message = "Username must only contain letters and digits";
        break;
      case tooShort:
        message = "Username is too short";
        break;
      case tooLong:
        message = "Username is too long";
        break;
      default:
        message = "something went wrong with the validator";
        break;
    }
    return message;
  }

  /**
   * insertProfile inserts a profile into the database.
   * 
   * @param username the username of the profile
   * @param email    the email of the profile
   * @param password the password of the profile
   */
  public void insertProfile(String username, String email, String password) {
    Profile p = new Profile("empty.url", email, username, password);
    this.databaseTalker.insertProfile(user.getUsername(), p);
    user.setProfiles(databaseTalker.getProfiles(user.getUsername()));
  }

  public DatabaseTalker getDatabaseTalker() {
    return this.databaseTalker;
  }
}
