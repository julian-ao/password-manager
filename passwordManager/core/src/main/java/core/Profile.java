package core;

public class Profile {
    private String url;
    private String email;
    private String profileUsername;
    private String encryptedPassword;


    public Profile(){
        
    }
    public Profile(String url, String email, String profileUsername, String encryptedPassword){
        this.url = url;
        this.email = email;
        this.profileUsername = profileUsername;
        this.encryptedPassword = encryptedPassword;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setProfileUsername(String profileUsername){
        this.profileUsername = profileUsername;
    }

    public void setEncryptedPassword(String encryptedPassword){
        this.encryptedPassword = encryptedPassword;
    }

    public String getUrl(){
        return this.url;
    }

    public String getEmail(){
        return this.email;
    }

    public String getProfileUsername(){
        return this.profileUsername;
    }

    public String getEncryptedPassword(){
        return this.encryptedPassword;
    }

    public String toString(){
        return "url: " + this.url + " email: " + this.email + " profileUsername: " + this.profileUsername + " encryptedPassword: " + this.encryptedPassword;
    }
}
