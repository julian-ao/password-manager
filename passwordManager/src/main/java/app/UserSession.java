package app;

import java.util.ArrayList;

import app.database.*;

public class UserSession {
    private User user;
    private ArrayList<Profile> profiles;
    private DatabaseTalker databaseTalker;


    public UserSession(DatabaseTalker databaseTalker){
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
        return this.databaseTalker.getProfiles(user.getUsername(), user.getPassword());
    }

    public boolean registerUser(String username, String password){
        return this.databaseTalker.insertUser(new User(username, password));
    }

    /*
     * logOut sets the user to null
     */
    public void logOut() {
        this.user = null;
    }
}
