package client;

import java.security.SecureRandom;

/**
 * Password is a class that is used to create passwords with utilities.
 */
public class Password {
  private String password;
  private int score;
  private SecureRandom rand = new SecureRandom();

  /**
   * Password constructor.
   * 
   * @param password the password to be used
   */
  public Password() {
    this.password = "";
    randomPassword();
    this.score = calcScore();
  }

  public Password(String password) {
    this.password = password;
    this.score = calcScore();
  }

  public void setPassword() {
    randomPassword();
    this.score = calcScore();
  }

  public void setPassword(String password) {
    this.password = password;
    this.score = calcScore();
  }

  public String getPassword() {
    return this.password;
  }

  public int getScore() {
    return this.score;
  }

  /**
   * Generates a random 16 characther long password.
   */
  public void randomPassword() {
    while (calcScore() < 3) {
      this.password = rand.ints(16, 33, 122)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    }
  }

  /**
   * Calculates the strength of the password.
   * 
   * @return int the score of the password from 1 to 3
   */
  public int calcScore() {
    int length = this.password.length();
    int upperCase = 0;
    int lowerCase = 0;
    int numbers = 0;
    int specialCharacters = 0;

    for (int i = 0; i < length; i++) {
      if (Character.isUpperCase(password.charAt(i))) {
        upperCase++;
      } else if (Character.isLowerCase(password.charAt(i))) {
        lowerCase++;
      } else if (Character.isDigit(password.charAt(i))) {
        numbers++;
      } else {
        specialCharacters++;
      }
    }

    if (length >= 14
        && (upperCase >= 2 && lowerCase >= 2 && numbers >= 2 && specialCharacters >= 2)) {
      this.score = 3;
    } else if (length >= 6
        && ((upperCase >= 1 && (lowerCase >= 1 || numbers >= 1 || specialCharacters >= 1))
            || (lowerCase >= 1 && (numbers >= 1 || specialCharacters >= 1 || upperCase >= 1))
            || (numbers >= 1 && (specialCharacters >= 1 || upperCase >= 1 || lowerCase >= 1))
            || (specialCharacters >= 1 && (upperCase >= 1 || lowerCase >= 1 || numbers >= 1)))) {
      this.score = 2;
    } else {
      this.score = 1;
    }
    return this.score;
  }
}
