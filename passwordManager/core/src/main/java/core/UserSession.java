package core;

import java.util.ArrayList;

import core.database.CSVDatabaseTalker;
import core.database.DatabaseTalker;

public class UserSession {
    private User user;
    private ArrayList<Profile> profiles;
    private DatabaseTalker databaseTalker;
    private static UserSession onlyInstance = new UserSession(new CSVDatabaseTalker("src/main/resources/ui/Users.csv"));

    private UserSession(DatabaseTalker databaseTalker){
        this.databaseTalker = databaseTalker;
    }

    public static UserSession getInstance(){
        return onlyInstance;
    }
    
    public boolean login(String username, String password){
        if(databaseTalker.checkPassword(username, password)){
            this.user = new User(username, password);
            this.profiles = databaseTalker.getProfiles(username, password);
            return true;
        }else return false;
    }

    public ArrayList<Profile> getProfiles(){
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
