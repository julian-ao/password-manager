package app.database;

public class User {
    private String username;
    private int salt;
    private byte[] hashedPassword;
}
