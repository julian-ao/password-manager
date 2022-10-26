package client.uservalidator;

/**
 * PasswordValidation is a class that represents different
 * types of responses to the validation of a user password.
 */
public enum PasswordValidation {
  OK, // password is ok
  diversityError, // password does not contain all of these: uppercase char, lowercase char,
  /* // digit, symbol //!rename this */
  tooShort, // password is too short
  tooLong, // password is too long
}