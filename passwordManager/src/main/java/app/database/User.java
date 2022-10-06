package app.database;

public class User {
    private String username;
    private String password;//!!!!MIDTLERTIDIG!!!!!
    private ArrayList<Profile> profiles;
    private int salt;
    private byte[] hashedPassword;


    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.profiles = new ArrayList<Profile>();
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setProfiles(ArrayList<Profile> profiles){
        this.profiles = profiles;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
}
