package restserver;

import localpersistence.DatabaseTalker;
import localpersistence.JsonTalker;
import core.userbuilder.PasswordValidation;
import core.userbuilder.UserBuilder;
import core.userbuilder.UsernameValidation;
import core.User;
import core.Profile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/entries")
public class PasswordManagerController {

  private String path = "../localpersistence/src/resources/localpersistance/production";

  private User user;

  @GetMapping(value = "/login")
  public @ResponseBody String getProfiles(@RequestParam String username, @RequestParam String password) {

    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());

    try {
      if (databaseTalker.checkPassword(username, password)) {
        this.user = new User(username, password);
        return "Success";
      } else {
        return "Failure";
      }
    } catch (IOException e) {
      return "Failure";
    }
  }

  @GetMapping(value = "/getProfiles")
  public @ResponseBody String getProfiles() {
    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    ArrayList<Profile> profiles = null;
    try {
      profiles = databaseTalker.getProfiles(user.getUsername());
    } catch (IOException e) {
      return "[]";// !better handling??
    }
    JSONArray jsonArray = new JSONArray();
    for (Profile profile : profiles) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("username", profile.getProfileUsername());
      jsonObject.put("title", profile.getTitle());
      jsonObject.put("password", profile.getEncryptedPassword());
      jsonArray.put(jsonObject);
    }
    return jsonArray.toString();
  }

  /*
   * databaseTalker.insertUser(new User(username, password));
   */
  @PostMapping(value = "/register")
  public @ResponseBody String postRegister(@RequestBody String body) {

    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String password = jsonObject.getString("password");
    try {
      if (databaseTalker.insertUser(new User(username, password))) {
        return "Success";
      } else {
        return "Failure";
      }
    } catch (IOException e) {
      return "Failure";
    }
  }

  // Get username
  @GetMapping(value = "/username")
  public @ResponseBody String getUsername() {
    return user.getUsername();
  }

  // Logout
  @GetMapping(value = "/logout")
  public @ResponseBody String logout() {
    user = null;
    return "Success";
  }

  // Insert profile
  @PostMapping(value = "/insertProfile")
  public @ResponseBody String insertProfile(@RequestBody String body) {

    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String title = jsonObject.getString("title");
    String password = jsonObject.getString("password");
    try {
      if (databaseTalker.insertProfile(user.getUsername(),
          new Profile(title, username, password, user.getUsername()))) {
        return "Success";
      } else {
        return "Failure";
      }
    } catch (IOException e) {
      return "Failure";
    }
  }

  /**
   * userValidator that validates the user input for username and password.
   * 
   * @param username       a username to check
   * @param password       a password to check
   * @param passwordRepeat repeated password provided by the user
   * @return a string which contains the message explaining what why the
   *         username/password is not accepted, if the username and passwords are
   *         accepted, and the passwordRepeat == password, the function return
   *         "OK"
   */
  public String userValidator(String username, String password, String passwordRepeat) {

    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    UserBuilder userBuilder = new UserBuilder();
    userBuilder.setUsername(username);
    userBuilder.setPassword(password);

    UsernameValidation usernameValidation = userBuilder.setUsername(username);
    PasswordValidation passwordValidation = userBuilder.setPassword(password);

    String message = null;
    if (!password.equals(passwordRepeat)) {
      message = "Passwords does not match";
    }
    try {
      if (databaseTalker.userExists(username)) {
        message = "Username is allready taken";
      }
    } catch (IOException e) {
      e.printStackTrace();
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

  // Uservalidator
  @GetMapping(value = "/userValidator")
  public @ResponseBody String getUserValidator(@RequestParam String username, @RequestParam String password,
      @RequestParam String passwordRepeat) {
    return userValidator(username, password, passwordRepeat);
  }

  // Delete profile
  @PostMapping(value = "/deleteProfile")
  public @ResponseBody String deleteProfile(@RequestBody String body) {
    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String title = jsonObject.getString("title");
    String password = jsonObject.getString("password");
    try {
      databaseTalker.deleteProfile(user.getUsername(), new Profile(username, title, password, user.getUsername()));
    } catch (IOException e) {
      return "Failure";
    }
    return "Success";
  }

  @PostMapping(value = "/doDatabaseTest")
  public @ResponseBody String doTestDatabase() {
    path = "../localpersistence/src/resources/localpersistance/test";
    return "Success";
  }
}
