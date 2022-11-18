package restserver;

import core.Profile;
import core.User;
import core.userbuilder.PasswordValidation;
import core.userbuilder.UserBuilder;
import core.userbuilder.UsernameValidation;
import encryption.Encrypted;
import encryption.Encryption;
import encryption.HexStringUtils;
import encryption.Sha256;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import localpersistence.DatabaseTalker;
import localpersistence.JsonTalker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * PasswordManagerController is the controller for the Password Manager application.
 */
@RestController
@RequestMapping("/api/v1/entries")
public class PasswordManagerController {

  private String path = "../localpersistence/src/resources/localpersistance/production";
  private Random rand = new Random();

  /**
   * Register login of a user.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return "success" if the login was successful, "failure" otherwise
   */
  @GetMapping(value = "/login")
  public @ResponseBody String login(@RequestParam String username, @RequestParam String password) {

    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());

    User u = null;
    try {
      u = databaseTalker.getUser(username);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    int salt = 0;
    if (u != null) {
      salt = u.getSalt();

    } else {
      return "Failure";
    }

    Sha256 hash = new Sha256();
    String hashedPasswordAttempt = hash.getHash(password, salt);

    try {
      if (databaseTalker.checkPassword(username, hashedPasswordAttempt)) {
        return "Success";
      } else {
        return "Failure";
      }
    } catch (IOException e) {
      return "Failure";
    }
  }

  /**
   * Method to get all profiles of a user.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return a JSON array of all profiles
   */
  @GetMapping(value = "/getProfiles")
  public @ResponseBody String getProfiles(@RequestParam String username,
      @RequestParam String password) {
    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    ArrayList<Profile> profiles = null;
    try {
      profiles = databaseTalker.getProfiles(username);
    } catch (IOException e) {
      return "[]"; // !better handling??
    }
    User user = null;
    try {
      user = databaseTalker.getUser(username);

    } catch (IOException e) {
      e.printStackTrace();
    }
    Sha256 hash = new Sha256();
    String userPassword = password;
    int encryptionSalt;
    if (user != null) {
      encryptionSalt = user.getEncryptionSalt();

    } else {
      return "Failure";
    }

    byte[] key = HexStringUtils.hexStringToByteArray(hash.getHash(userPassword, encryptionSalt));
    JSONArray jsonArray = new JSONArray();
    if (profiles != null) {
      for (Profile profile : profiles) {
        JSONObject jsonObject = new JSONObject();

        Encrypted encryptedPassword = new Encrypted();
        encryptedPassword
            .setData(HexStringUtils.hexStringToByteArray(profile.getEncryptedPassword()));
        encryptedPassword.setNonce(HexStringUtils.hexStringToByteArray(profile.getNonceHex()));

        jsonObject.put("username", profile.getProfileUsername());
        jsonObject.put("title", profile.getTitle());
        jsonObject.put("password", Encryption.decrypt(encryptedPassword, key));
        jsonObject.put("id", profile.getId());
        jsonArray.put(jsonObject);
      }
    }
    return jsonArray.toString();
  }

  /**
   * databaseTalker.insertUser(new User(username, password));
   */
  @PostMapping(value = "/register")
  public @ResponseBody String postRegister(@RequestBody String body) {

    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    JSONObject jsonObject = new JSONObject(body);
    String username = jsonObject.getString("username");
    String password = jsonObject.getString("password");

    Sha256 hash = new Sha256();
    int salt = rand.nextInt();
    int encryptionSalt = rand.nextInt();
    User user = new User(username, hash.getHash(password, salt));
    user.setSalt(salt);
    user.setEncryptionSalt(encryptionSalt);

    try {
      if (databaseTalker.userExists(username)) {
        return "Failure";
      }
      if (databaseTalker.insertUser(user)) {
        return "Success";
      } else {
        return "Failure";
      }
    } catch (IOException e) {
      return "Failure";
    }
  }

  /**
   * Inserts a new profile into the database.
   *
   * @param body the json object containing the profile information
   * @return string "Success" if the profile was inserted successfully, "Failure" otherwise
   */
  @PostMapping(value = "/insertProfile")
  public @ResponseBody String insertProfile(@RequestBody String body) {
    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    JSONObject jsonObject = new JSONObject(body);

    String userPassword = jsonObject.getString("parentPassword");
    Encryption encryption = new Encryption();
    Sha256 hash = new Sha256();
    User user = null;
    try {
      user = databaseTalker.getUser(jsonObject.getString("parentUsername"));
    } catch (IOException | JSONException e1) {
      return "Failure";
    }
    int encryptionSalt = 0;
    if (user != null) {
      encryptionSalt = user.getEncryptionSalt();
    } else {
      return "Failure";
    }

    byte[] key = HexStringUtils.hexStringToByteArray(hash.getHash(userPassword, encryptionSalt));

    String username = jsonObject.getString("username");
    String title = jsonObject.getString("title");
    Encrypted encryptedPassword = Encryption.encrypt(jsonObject.getString("password"), key);

    int id;
    try {
      id = databaseTalker.getNextProfileId();
    } catch (IOException e) {
      e.printStackTrace();
      return "Failure";
    }
    System.out.println("creating new profile id: " + id);
    try {
      if (databaseTalker.insertProfile(
          new Profile(title, username, 
          HexStringUtils.byteArrayToHexString(encryptedPassword.getData()),
              user.getUsername(),
              HexStringUtils.byteArrayToHexString(encryptedPassword.getNonce()), id))) {
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
   * @param username a username to check
   * @param password a password to check
   * @param passwordRepeat repeated password provided by the user
   * @return a string which contains the message explaining what why the username/password is not
   *         accepted, if the username and passwords are accepted, and the passwordRepeat ==
   *         password, the function return "OK"
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

  /**
   * Deletes profile.
   *
   * @param body containing the username, password and title of the profile to be deleted
   * @return string "Success" or "Failure" depending on if the profile was deleted or not
   */
  @PostMapping(value = "/deleteProfile")
  public @ResponseBody String deleteProfile(@RequestBody String body) {
    DatabaseTalker databaseTalker = new JsonTalker(new File(path).toPath());
    JSONObject jsonObject = new JSONObject(body);
    int id = Integer.parseInt(jsonObject.get("id").toString());
    User user = null;
    try {
      user = databaseTalker.getUser(jsonObject.getString("user"));
    } catch (JSONException | IOException e1) {
      e1.printStackTrace();
      return "Failure";
    }
    try {
      if (user != null) {
        databaseTalker.deleteProfile(user.getUsername(),
            new Profile("username", "title", "password", user.getUsername(), "empty", id));
      }
    } catch (IOException e) {
      return "Failure";
    }
    return "Success";
  }

  /**
   * Creates two new files in the local persistence folder, one for users and one for profiles.
   *
   * @return string success or failure depending on if the files were created or not.
   */
  @PostMapping(value = "/doDatabaseTest")
  public @ResponseBody String doTestDatabase() {
    path = "../localpersistence/src/resources/localpersistance/test";
    File usersFile = new File(path + "/users.json");
    File profilesFile = new File(path + "/profiles.json");
    if (usersFile.exists()) {
      if (!usersFile.delete()) {
        return "Failure";
      }
    }
    if (profilesFile.exists()) {
      if (!profilesFile.delete()) {
        return "Failure";
      }
    }
    return "Success";
  }
}
