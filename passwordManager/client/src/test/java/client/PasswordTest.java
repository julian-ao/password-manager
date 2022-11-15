package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordTest {
  @Test
  public void PasswordScoreTest() {
    Password password1 = new Password("password");
    Password password2 = new Password("Password123!");
    Password password3 = new Password("PAssword123!@123123123");
    Password password4 = new Password("PAuw12%$aa");

    Assertions.assertEquals(1, password1.getScore());
    Assertions.assertEquals(2, password2.getScore());
    Assertions.assertEquals(3, password3.getScore());
    Assertions.assertEquals(2, password4.getScore());

    for (int i = 0; i < 100; i++) {
      Password pass = new Password();
      pass.randomPassword();
      Assertions.assertTrue(pass.getScore() > 0);
      String p = pass.getPassword().substring(0, 8);
      pass.setPassword(p);
      Assertions.assertTrue(pass.getScore() > 0);
    }
  }

  @Test
  public void PasswordRandomTest() {
    Password generatedPassword = new Password();
    Assertions.assertEquals(3, generatedPassword.getScore());
  }

  // test setPassword() and getPassword(string)
  @Test
  public void PasswordSetTest() {
    Password password = new Password();
    password.setPassword("password");
    Assertions.assertEquals("password", password.getPassword());
    password.setPassword();
    Assertions.assertEquals(3, password.getScore());
    Assertions.assertEquals(false, password.getPassword().equals("password"));
  }
}
