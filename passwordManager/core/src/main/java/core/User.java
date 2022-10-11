package core;

import java.util.ArrayList;
public class User {
    private String username;
    private String password;//!!!!MIDTLERTIDIG!!!!!
    private ArrayList<Profile> profiles;
    private int salt;
    private byte[] hashedPassword;


    public User(){
        profiles = new ArrayList<Profile>();
    }

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
    public ArrayList<Profile> getProfiles(){
        return this.profiles;
    }

    public void addProfile(Profile profile){
        this.profiles.add(profile);
    }

    public void removeProfile(Profile profile){
        this.profiles.remove(profile);
    }


    public String toString(){
        return "username: " + this.username + " password: " + this.password + " profiles: " + this.profiles.toString();
    }
}
