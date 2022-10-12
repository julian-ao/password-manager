package core.database;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

import core.User;
import core.Profile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDatabaseTalker implements DatabaseTalker {
    // json lagres i ././resources/core/Users.json
    private File jsonFile;

    /**
     * Constructor for JsonDatabaseTalker
     * @param jsonFile the path to the json file
     */
    public JsonDatabaseTalker(String jsonFile){
        this.jsonFile = new File(jsonFile);
        if (this.jsonFile.length() == 0) { // If file is empty, create a list of users
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<User> users = new ArrayList<User>();
            try {
                mapper.writeValue(this.jsonFile, users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * userExists checks if a user with a given username exists
     * @param username username of a user
     * @return true if a user with the username exists in the database
     */
    @Override
    public boolean userExists(String username) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }
    }

    /**
     * checkPassword checks if the password matches the username in the json file
     * @param username username of a user
     * @param password password of a user
     * @return true if the user exists and the password is correct, false otherwise
     */
    @Override
    public boolean checkPassword(String username, String password) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }
    }

    /**
     * insertUser stores a new user in the json file
     * @param username the username of the new user
     * @param password the password of the new user
     * @return true if the username was unique and the user was stored successfully
     */
    @Override
    public boolean insertUser(User user) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (userExists(user.getUsername())) {
            return false;
        }
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            User[] newUsers = new User[users.length + 1];
            for (int i = 0; i < users.length; i++) {
                newUsers[i] = users[i];
            }
            newUsers[users.length] = user;
            mapper.writeValue(jsonFile, newUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            return true;
        }
    }

    /**
     * deleteUser deletes a user from the json file
     * @param username the username of the user to be deleted
     * @return true if the user was deleted successfully, false otherwise
     */
    @Override
    public void deleteUser(String username) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            User[] newUsers = new User[users.length - 1];
            int j = 0;
            for (int i = 0; i < users.length; i++) {
                if (!users[i].getUsername().equals(username)) {
                    newUsers[j] = users[i];
                    j++;
                }
            }
            mapper.writeValue(jsonFile, newUsers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * getProfiles gets all profiles of a user
     * @param username the username of the user
     * @return an ArrayList of profiles or null if the user does not exist
     */
    @Override
    public ArrayList<Profile> getProfiles(String username) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return user.getProfiles();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    /**
     * insertProfile adds a new profile for a user
     * @param username the username of the user
     * @param profile the profile to be added to the user
     * @return true if the profile was inserted successfully, false otherwise
     */
    @Override
    public boolean insertProfile(String username, Profile profile) {
        ObjectMapper mapper = new ObjectMapper();
        boolean isAdded = false;
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    user.addProfile(profile);
                    isAdded = true;
                }
            }
            if (isAdded) {
                mapper.writeValue(jsonFile, users);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }
    }

    /**
     * isSameProfile checks if a profile is the same as another profile
     * @param profile1 the first profile
     * @param profile2 the second profile
     * @return true if the values of the profiles are the same, false otherwise
     */
    private boolean isSameProfile(Profile profile1, Profile profile2) {
        if (
                profile1.getEmail().equals(profile2.getEmail()) &&
                profile1.getProfileUsername().equals(profile2.getProfileUsername()) &&
                profile1.getEncryptedPassword().equals(profile2.getEncryptedPassword()) &&
                profile1.getUrl().equals(profile2.getUrl())
        ) {
            return true;
        }
        return false;
    }

    /**
     * deleteProfile deletes a profile from a user
     * @param username the username of the user
     * @param profile the profile to be deleted
     * @return true if the profile was deleted successfully, false otherwise
     */
    @Override
    public void deleteProfile(String username, Profile profile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);

            Profile toBeRemoved = null;
            User toBeRemovedFrom = null;
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    toBeRemovedFrom = user;
                    ArrayList<Profile> profiles = user.getProfiles();
                    for (Profile p : profiles) {
                        if (isSameProfile(p, profile)) {
                            toBeRemoved = p;
                        }
                    }
                }
            }
            if (toBeRemoved != null && toBeRemovedFrom != null) {
                toBeRemovedFrom.removeProfile(toBeRemoved); // vi kommer oss hit
            }
            mapper.writeValue(jsonFile, users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
