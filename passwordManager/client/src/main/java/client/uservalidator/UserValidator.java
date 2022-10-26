package client.uservalidator;

import client.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * UserBuilder is a class that is used to build a user object.
 */
public class UserValidator {
  private String username;
  private String password;
  private RestTalker restTalker;
  private Map<Predicate<String>, UsernameValidation> usernameValidation;
  private Map<Predicate<String>, PasswordValidation> passwordValidation;

  /**
   * UserBuilder is a constructor for the UserBuilder class.
   * 
   * @param databaseTalker the databaseTalker used to check if the username is
   *                       already taken
   */
  public UserValidator(RestTalker restTalker) {
    this.restTalker = restTalker;

    usernameValidation = new HashMap<Predicate<String>, UsernameValidation>();
    usernameValidation.put((x) -> x.length() < 3, UsernameValidation.tooShort);
    usernameValidation.put((x) -> x.length() > 30, UsernameValidation.tooLong);
    usernameValidation.put((x) -> !Pattern.matches("[a-zA-Z0-9]{0,10000}", x),
        UsernameValidation.invalidCharacters);
    usernameValidation.put((x) -> restTalker.userExists(x),
        UsernameValidation.alreadyTaken);

    passwordValidation = new HashMap<Predicate<String>, PasswordValidation>();
    passwordValidation.put((x) -> x.length() < 6, PasswordValidation.tooShort);
    passwordValidation.put((x) -> x.length() > 30, PasswordValidation.tooLong);
    passwordValidation.put((x) -> !(Pattern.matches(".{0,100}[a-z].{0,100}", x)
        && Pattern.matches(".{0,100}[A-Z].{0,100}", x)
        && Pattern.matches(".{0,100}[0-9].{0,100}", x)
        && Pattern.matches(".{0,100}[!\"#$%&].{0,100}", x)),
        PasswordValidation.diversityError);
  }

  /**
   * checks is a username is a valid one.
   * 
   * @param username the username to be validated
   * @return UsernameValidation enum which contains the response code
   */
  public UsernameValidation validateUsername(String username) {
    for (Map.Entry<Predicate<String>, UsernameValidation> entry : usernameValidation.entrySet()) {
      if (entry.getKey().test(username)) {
        return entry.getValue();
      }
    }
    return UsernameValidation.OK;
  }

  /**
   * checks if a password is a valid one.
   * 
   * @param password the password to be validated
   * @return PasswordValidation enum which contains the response code
   */
  public PasswordValidation validatePassword(String password) {
    for (Map.Entry<Predicate<String>, PasswordValidation> entry : passwordValidation.entrySet()) {
      if (entry.getKey().test(password)) {
        return entry.getValue();
      }
    }
    return PasswordValidation.OK;
  }

}
