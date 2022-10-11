package app.database;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class JsonDatabaseTalkerTest {

    DatabaseTalker jsonDatabaseTalker; // = new JsonDatabaseTalker("src/main/resources/app/testUsers.json");


    public JsonDatabaseTalkerTest(){
        // if file exist from before. Delete it and create new empty file
        File file = new File("src/main/resources/app/testUsers.json");
        if (file.exists()) {
            file.delete();
        }
        File jsonFile = new File("src/main/resources/app/testUsers.json");
        try {
            jsonFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        jsonDatabaseTalker = new JsonDatabaseTalker("src/main/resources/app/testUsers.json");

        // Insert a user into the json file
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
        assertEquals(true, jsonDatabaseTalker.userExists("user1"));
        assertEquals(false, jsonDatabaseTalker.userExists("NOTAUSER"));

    }

    @Test
    public void insertUserTest(){
        User newUser = new User("user2", "password2");
        jsonDatabaseTalker.insertUser(newUser);
        assertEquals(true, jsonDatabaseTalker.userExists(newUser.getUsername()));
    }

    @Test
    public void deleteUserTest(){
        User newUser = new User("user2", "password2");
        jsonDatabaseTalker.insertUser(newUser);
        assertEquals(true, jsonDatabaseTalker.userExists(newUser.getUsername()));
        jsonDatabaseTalker.deleteUser(newUser.getUsername());
        assertEquals(false, jsonDatabaseTalker.userExists(newUser.getUsername()));
    }

    private boolean hasProfile(ArrayList<Profile> profiles, Profile profile){
        for(Profile p : profiles){
            if(
                p.getUrl().equals(profile.getUrl()) &&
                p.getEmail().equals(profile.getEmail()) &&
                p.getProfileUsername().equals(profile.getProfileUsername()) &&
                p.getEncryptedPassword().equals(profile.getEncryptedPassword())
                ){
                    return true;
                }
        }
        return false;
    }

    @Test
    public void insertProfileTest(){
        Profile profile = new Profile("nettside.no", "sondrkol@it.no", "sondrkol", "passord");
        jsonDatabaseTalker.insertProfile("user1", profile);
        ArrayList<Profile> profiles = jsonDatabaseTalker.getProfiles("user1");
        assertEquals(true, hasProfile(profiles, profile));

    }

    @Test
    public void checkPasswordTest() {
        assertEquals(true, jsonDatabaseTalker.checkPassword("user1", "password1"));
        assertEquals(false, jsonDatabaseTalker.checkPassword("user1", "password2"));
    }

    @Test
    public void getProfilesTest(){
        ArrayList<Profile> profiles = jsonDatabaseTalker.getProfiles("user1");
        assertEquals(4, profiles.size());
    }

    @Test
    public void deleteProfileTest(){
        ArrayList<Profile> profiles = jsonDatabaseTalker.getProfiles("user1");
        Profile profile = profiles.get(0);
        System.out.print("profile is: ");
        System.out.print(profile);
        assertEquals(true, hasProfile(profiles, profile));
        jsonDatabaseTalker.deleteProfile("user1", profile);
        assertEquals(false, hasProfile(profiles, profile));
    }
}
