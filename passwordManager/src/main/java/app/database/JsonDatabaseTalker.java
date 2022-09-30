package app.database;

import java.util.ArrayList;

public class JsonDatabaseTalker implements DatabaseTalker{

    @Override
    public boolean userExists(String username) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkPassword(String username, String password) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean insertUser(String username, String password) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ArrayList<Profile> getProfiles(String username, String password) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
