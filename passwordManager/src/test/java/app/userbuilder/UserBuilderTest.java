package app.userbuilder;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import app.database.*;
import app.userbuilder.UserBuilder;
import app.userbuilder.UsernameValidation;

public class UserBuilderTest {
    
    DatabaseTalker databaseTalker = new CSVDatabaseTalker("src/main/resources/app/testUsers.csv");
    UserBuilder userBuilder = new UserBuilder(databaseTalker);


    @Test
    public void testUsernameValidation(){
        Assertions.assertEquals(UsernameValidation.tooShort, userBuilder.setUsername("a"));
        Assertions.assertEquals(UsernameValidation.allreadyTaken, userBuilder.setUsername("user1"));
        Assertions.assertEquals(UsernameValidation.tooLong, userBuilder.setUsername("VeryLongUsernameIDontKnowWhySomeoneWouldTryThisButHeyItNeedsToBeTested"));
        Assertions.assertEquals(UsernameValidation.invalidCharacters, userBuilder.setUsername("invInMyName++"));
        Assertions.assertEquals(UsernameValidation.OK, userBuilder.setUsername("usEr123"));
    }


    @Test
    public void testPasswordValidation(){
        Assertions.assertEquals(PasswordValidation.tooShort, userBuilder.setPassword("1aA#"));
        Assertions.assertEquals(PasswordValidation.tooLong, userBuilder.setPassword("VeryLongpas#swordahsfbhsbf44HowcanSomeoneEve9nRememberThis"));
        Assertions.assertEquals(PasswordValidation.diversityError, userBuilder.setPassword("andjfNJSANFJ0"));
        Assertions.assertEquals(PasswordValidation.OK, userBuilder.setPassword("Passord123#"));
    }

}
