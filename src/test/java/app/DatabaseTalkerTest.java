package app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DatabaseTalkerTest {
    DatabaseTalker databaseTalker = new DatabaseTalker("src/main/resources/app/testUsers.csv");

    @Test
    public void userExistsTest(){
        assertEquals(databaseTalker.userExists("user1"), true);
        assertEquals(databaseTalker.userExists("user2"), true);
        assertEquals(databaseTalker.userExists("NOTAUSER"), false);
    }



    @Test
    public void checkPasswordTest(){
        assertEquals(databaseTalker.checkPassword("user1", "password1"), true);
        assertEquals(databaseTalker.checkPassword("user1", "password2"), false);
        assertEquals(databaseTalker.checkPassword("user2", "password1"), false);
        assertEquals(databaseTalker.checkPassword("user2", "password2"), true);
        assertEquals(databaseTalker.checkPassword("NOTAUSER", "password1"), false);
    }
}
