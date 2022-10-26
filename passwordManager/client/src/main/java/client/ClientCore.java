package client;


import client.uservalidator;

public class ClientCore {
    public String userValidator(String username, String password, String passwordRepeat){
      UserValidator userValidator = new UserBuilder(databaseTalker);

    UsernameValidation usernameValidation = userValidator.validateUsername(username);
    PasswordValidation passwordValidation = userValidator.validatePassword(password);

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
}
