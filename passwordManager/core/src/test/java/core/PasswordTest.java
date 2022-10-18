package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordTest {
    @Test
    public void PasswordScoreTest() {
        Password password1 = new Password("password");
        Password password2 = new Password("Password123!");
        Password password3 = new Password("Password123!@123123123");
        
        Assertions.assertEquals(1, password1.getScore());
        Assertions.assertEquals(2, password2.getScore());
        Assertions.assertEquals(3, password3.getScore());
    }

    @Test
    public void PasswordRandomTest() {
        Password generatedPassword = new Password();
        generatedPassword.randomPassword();
        Assertions.assertEquals(3, generatedPassword.getScore());
    }
}
