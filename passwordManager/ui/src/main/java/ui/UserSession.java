package ui;

import java.util.ArrayList;

import ui.database.CSVDatabaseTalker;
import ui.database.DatabaseTalkerUi;
import core.ProfileCore;
import core.UserCore;

public class UserSession {
    private UserCore user;
    private ArrayList<ProfileCore> profiles;
    private DatabaseTalkerUi databaseTalker;
    private static UserSession onlyInstance = new UserSession(new CSVDatabaseTalker("src/main/resources/ui/Users.csv"));

    private UserSession(DatabaseTalkerUi databaseTalker){
        this.databaseTalker = databaseTalker;
    }

    public static UserSession getInstance(){
        return onlyInstance;
    }
    
    public boolean login(String username, String password){
        if(databaseTalker.checkPassword(username, password)){
            this.user = new UserCore(username, password);
            this.profiles = databaseTalker.getProfiles(username, password);
            return true;
        }else return false;
    }

    public ArrayList<ProfileCore> getProfiles(){
        return this.databaseTalker.getProfiles(user.getUsername(), user.getPassword());
    }

    public boolean registerUser(String username, String password){
        return this.databaseTalker.insertUser(username, password);
    }



    public String getUsername(){
        return user.getUsername();
    }
    /*
     * logOut sets the user to null
     */
    public void logOut() {
        this.user = null;
    }
}
