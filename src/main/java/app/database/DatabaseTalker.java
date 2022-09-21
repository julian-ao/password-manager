package app.database;

import java.util.ArrayList;

public interface DatabaseTalker {
    public boolean userExists(String username);
    public boolean checkPassword(String username, String password);
    public boolean insertUser(String username, String password);
    public boolean deleteUser(String username);
    public ArrayList<Profile> getProfiles(String username, String password);
}