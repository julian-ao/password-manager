package core;

import java.util.ArrayList;

import core.database.DatabaseTalker;
import core.database.JsonDatabaseTalker;
import core.userbuilder.PasswordValidation;
import core.userbuilder.UserBuilder;
import core.userbuilder.UsernameValidation;

public class UserSession {
    private User user;
    private ArrayList<Profile> profiles;
    private static DatabaseTalker databaseTalker = new JsonDatabaseTalker("src/main/resources/ui/Users.json");
    private static UserSession onlyInstance = new UserSession(databaseTalker);
    private UserBuilder userBuilder;

    private UserSession(DatabaseTalker databaseTalker) {
        this.databaseTalker = databaseTalker;
    }

    public static UserSession getInstance() {
        return onlyInstance;
    }

    public boolean login(String username, String password) {
        if (databaseTalker.checkPassword(username, password)) {
            this.user = new User(username, password);
            this.profiles = databaseTalker.getProfiles(username);
            return true;
        } else
            return false;
    }

    public ArrayList<Profile> getProfiles() {
        return this.databaseTalker.getProfiles(user.getUsername());
    }

    public boolean registerUser(String username, String password) {
        return this.databaseTalker.insertUser(new User(username, password));
    }

    public String getUsername() {
        return user.getUsername();
    }

    /*
     * logOut sets the user to null
     */
    public void logOut() {
        this.user = null;
    }

    public String userValidator(String username, String password, String passwordRepeat) {
        UserBuilder userBuilder = new UserBuilder(databaseTalker);
        userBuilder.setUsername(username);
        userBuilder.setPassword(password);

        UsernameValidation usernameValidation = userBuilder.setUsername(username);
        PasswordValidation passwordValidation = userBuilder.setPassword(password);

        // if nothing wrong with username and password
        if (usernameValidation == UsernameValidation.OK && passwordValidation == PasswordValidation.OK) {

            // if password inputs match
            if (password.equals(passwordRepeat)) {
                System.out.println("registering user");
                if (this.registerUser(username, password)) {
                    return "OK";
                } else {
                    return "Unexpected";
                }
            } else {
                return "Passwords do not match";
            }
        } else {

            if (usernameValidation == UsernameValidation.alreadyTaken) {
                return "Username already taken";
            } else if (usernameValidation != UsernameValidation.OK) {
                return "Username must be between 3 and 30 characters long and contain only letters and numbers";
            } else {
                return "Password must be between 6 and 30 characters long and contain at least one lowercase letter, one uppercase letter, one number and one special character";
            }
        }
    }
}
