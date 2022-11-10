package restserver;

import core.database.DatabaseTalker;
import core.database.JsonDatabaseTalker;
import core.userbuilder.PasswordValidation;
import core.userbuilder.UserBuilder;
import core.userbuilder.UsernameValidation;
import core.User;
import core.Profile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/v1/entries")
public class PasswordManagerController {

  // Get test that logs the parameters
  @GetMapping("/test1")
  public @ResponseBody String getTest() {
    return "test11";
  }

  // Post test that logs the parameters
  @PostMapping("/test3")
  public @ResponseBody String postTest(@RequestBody String body) {
    return body;
  }

  // Get test that logs the parameters
  @GetMapping("/test")
  public @ResponseBody String getTest(@RequestParam String param1, @RequestParam String param2) {
    return param1 + " " + param2;
  }


  // Test4 gets a hashmap from the body and hashmap {test: test} where test is a value from the body
  @GetMapping(value = "/test4", produces = "application/json")
  public @ResponseBody String getTest4(@RequestParam String param) {
    String id = param;
    HashMap<String, String> map = new HashMap<>();
    map.put("test", id);
    return new JSONObject(map).toString();
  }

  private String url = "../localpersistence/src/resources/localpersistance/Users.json";

  private User user;

  @GetMapping(value = "/login")
  public @ResponseBody String getProfiles(@RequestParam String username,
      @RequestParam String password) {

    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);

    if (databaseTalker.checkPassword(username, password)) {
      this.user = new User(username, password);
      // ! user.setProfiles(databaseTalker.getProfiles(user.getUsername())); DETTE trenger vi ikke
      // ??
      // Get the profiles and return them as a JSON array
      ArrayList<Profile> profiles = databaseTalker.getProfiles(user.getUsername());
      JSONArray jsonArray = new JSONArray();
      for (Profile profile : profiles) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", profile.getProfileUsername());
        jsonObject.put("title", profile.getEmail());
        jsonObject.put("password", profile.getEncryptedPassword());
        jsonArray.put(jsonObject);
      }
      return jsonArray.toString();
    } else {
      return "Invalid";
    }
  }

  /*
   * databaseTalker.insertUser(new User(username, password));
   */
  @PostMapping(value = "/register")
  public @ResponseBody String postRegister(@RequestBody String body) {

    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String password = jsonObject.getString("password");
    if (databaseTalker.insertUser(new User(username, password))) {
      return "Success";
    } else {
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

    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String email = jsonObject.getString("email");
    String password = jsonObject.getString("password");
    if (databaseTalker.insertProfile(user.getUsername(),
        new Profile("empty.url", email, username, password, user.getUsername()))) {
      return "Success";
    } else {
      return "Failure";
    }
  }


  /**
   * userValidator that validates the user input for username and password.
   * 
   * @param username a username to check
   * @param password a password to check
   * @param passwordRepeat repeated password provided by the user
   * @return a string which contains the message explaining what why the username/password is not
   *         accepted, if the username and passwords are accepted, and the passwordRepeat ==
   *         password, the function return "OK"
   */
  public String userValidator(String username, String password, String passwordRepeat) {

    DatabaseTalker databaseTalker = new JsonDatabaseTalker(url);
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
            message =
                "Password must contain: uppercase letter, lowercase letter, digit and a symbol";
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

  // Uservalidator
  @GetMapping(value = "/userValidator")
  public @ResponseBody String getUserValidator(@RequestParam String username,
      @RequestParam String password, @RequestParam String passwordRepeat) {
    return userValidator(username, password, passwordRepeat);
  }

}
