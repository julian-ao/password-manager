package app;

import java.util.ArrayList;

import app.database.*;

public class UserSession {
    private User user;
    private ArrayList<Profile> profiles;
    private DatabaseTalker databaseTalker;


    UserSession(DatabaseTalker databaseTalker){
        this.databaseTalker = databaseTalker;
    }

    public boolean login(String username, String password){
        if(databaseTalker.checkPassword(username, password)){
            user = new User(username, password);
            this.profiles = databaseTalker.getProfiles(username, password);
            return true;
        }else return false;
    }

    public ArrayList<Profile> getProfiles(){
        return databaseTalker.getProfiles(user.getUsername(), user.getPassword());
    }
}
