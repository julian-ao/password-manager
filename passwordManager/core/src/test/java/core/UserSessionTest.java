package core;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UserSessionTest {
    UserSession userSession = UserSession.getInstance();


    @Test
    public void insertProfileTest(){

        userSession.overridePath("src/main/resources/core/TestUsers.json");
        userSession.getDatabaseTalker().insertUser(new User("Admin", "Admin1!"));

        userSession.login("Admin", "Admin1!");
        ArrayList<Profile> profiles = userSession.getProfiles();
        ArrayList<ArrayList<String>> profilesStr = userSession.getProfilesNativeTypes();
        userSession.insertProfile("user", "stuff", "more stuffv");


        ArrayList<Profile> profiles1 = userSession.getProfiles();
        ArrayList<ArrayList<String>> profilesStr1 = userSession.getProfilesNativeTypes();


        System.out.println();

    }
}
