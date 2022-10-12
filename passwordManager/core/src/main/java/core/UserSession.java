package core;

import core.database.DatabaseTalker;
import core.database.JsonDatabaseTalker;
import core.userbuilder.PasswordValidation;
import core.userbuilder.UserBuilder;
import core.userbuilder.UsernameValidation;
import java.util.ArrayList;

/**
 * UserSession is a class that is used to manage the user session.
 */
public class UserSession {
  private User user;
  private static DatabaseTalker databaseTalker = new JsonDatabaseTalker(
      "src/main/resources/ui/Users.json");
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

    // if nothing wrong with username and password
    if (usernameValidation == UsernameValidation.OK
        && passwordValidation == PasswordValidation.OK) {
      // if password inputs match
      if (password.equals(passwordRepeat)) {
        System.out.println("registering user");
        if (this.registerUser(username, password)) {
          return "OK";
        } else {
          return "Unexpected";
        }
      } else {
        return "Passwords do not match";
      }
    } else {

      if (usernameValidation == UsernameValidation.alreadyTaken) {
        return "Username already taken";
      } else if (usernameValidation != UsernameValidation.OK) {
        String s1 = "Username must be between 5 and 20 characters ";
        String s2 = "Username must contain only letters and numbers";
        return s1 + s2;
      } else {
        String s1 = "Password must be between 8 and 20 characters ";
        String s2 = "Password must contain at least one uppercase letter ";
        return s1 + s2;
      }
    }
  }

  /**
   * insertProfile inserts a profile into the database.

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
