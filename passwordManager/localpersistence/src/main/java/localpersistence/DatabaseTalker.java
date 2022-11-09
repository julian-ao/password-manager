package localpersistence;

import core.Profile;
import core.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * DatabaseTalker is an interface for the database.
 */
public interface DatabaseTalker {

  /**
   * userExists checks if a user with a given username exists.
   * 
   * @param username username of a user
   * @return true if a user with the username exists in the database
   */
  public boolean userExists(String username) throws IOException;

  /**
   * checkPassword checks if the password matches the username in the database.
   * 
   * @param username username to be checked
   * @param password password to be checked against username
   * @return true if the user exists and the password is correct
   */
  public boolean checkPassword(String username, String password) throws IOException;

  /**
   * insertUser stores a new user in the databse.
   * 
   * @param user the user to be stored
   * @return true if the username was unique and the user was stores successfully
   */
  public boolean insertUser(User user) throws IOException;

  /**
   * deleteUser deletes a user from the database.
   * 
   * @param username the username of the user that is to be deleted
   */
  public void deleteUser(String username) throws IOException;

  /**
   * getProfiles returns the profiles of a user.
   * 
   * @param username the username of the user
   * @return a list of profile objects that has been stored by the user
   */
  public ArrayList<Profile> getProfiles(String username) throws IOException;

  /**
   * insertProfile stores a new profile.
   * 
   * @param username the user which the profile should be stored with
   * @param profile  the new profile object that is to be saved
   * @return boolean telling wether the operation was succesful or not
   */
  public boolean insertProfile(String username, Profile profile) throws IOException;

  /**
   * deleteProfile deletes a profile.
   * 
   * @param username the username that owns the profile
   * @param profile  the profile which is to be deleted
   */
  public void deleteProfile(String username, Profile profile) throws IOException;
}