package ui.database;

import java.util.ArrayList;
import core.ProfileCore;

public interface DatabaseTalkerUi {



    /*
     * userExists checks if a user with a given username exists
     * 
     * @param username username of a user
     * 
     * @return true if a user with the username exists in the database
     */
    public boolean userExists(String username);


    /*
     * checkPassword checks if the password matches the username in the database
     * 
     * 
     * @param username username to be checked
     * @param password password to be checked against username
     * 
     * @return true if the user exists and the password is correct
     */
    public boolean checkPassword(String username, String password);


    /*
     * 
     * insertUser stores a new user in the databse
     * 
     * @param username the username of the new user
     * @param password the password of the new user
     * 
     * @return true if the username was unique and the user was stores successfully
     */
    public boolean insertUser(String username, String password);


    /*
     * 
     * deleteUser deletes a user from the database
     * 
     * @param username the username of the user that is to be deleted
     * 
     * @return true if the user existed and the user was deleted
     */
    public boolean deleteUser(String username);


    /*
     * getProfiles returns the profiles of a user
     * 
     * @param username the username of the user
     * @param password the password of the user
     * 
     * @return a list of profile objects that has been stored by the user
     */
    public ArrayList<ProfileCore> getProfiles(String username, String password);
}