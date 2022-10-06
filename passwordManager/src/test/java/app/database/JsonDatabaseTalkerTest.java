package app.database;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class JsonDatabaseTalkerTest {
    DatabaseTalker jsonDatabaseTalker = new JsonDatabaseTalker("src/main/resources/app/testUsers.json");


    public JsonDatabaseTalkerTest(){
        Profile profile1 = new Profile("google.bror", "bob@bob.mail", "profile1", "password1");
        Profile profile2 = new Profile("face.bror", "bob@bob.mail", "profile2", "password2");
        Profile profile3 = new Profile("twitt.bror", "bob@bob.mail", "profile3", "password3");
        Profile profile4 = new Profile("nsa.bror", "bob@bob.mail", "profile4", "password4");
        User user = new User("user1", "password1");

        ArrayList<Profile> profiles = new ArrayList<Profile>();
        profiles.add(profile1);
        profiles.add(profile2);
        profiles.add(profile3);
        profiles.add(profile4);

        user.setProfiles(profiles);

        jsonDatabaseTalker.insertUser(user);
    }

    @Test
    public void userExistsTest(){


    }

    @Test
    public void inserUserTest(){

    }

    @Test
    public void deleteUserTest(){

    }
}
