package app.database;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDatabaseTalker implements DatabaseTalker{
    // json lagres i ././resources/app/Users.json
    private File jsonFile;// = "src/main/resources/app/Users.json";

    public JsonDatabaseTalker(String jsonFile){
        this.jsonFile = new File(jsonFile);
    }

    @Override
    public boolean userExists(String username) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        try{
            User[] users = mapper.readValue(jsonFile, User[].class);
            for(User user : users){
                if(user.getUsername().equals(username)){
                    return true;
                }
            }
        return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            
        }
    }

    @Override
    public boolean checkPassword(String username, String password) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        try{
            User[] users = mapper.readValue(jsonFile, User[].class);
            for(User user : users){
                if(user.getUsername().equals(username)){
                    if(user.getPassword().equals(password)){
                        return true;
                    }
                }
            }
        return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            
        }
    }

    @Override
    public boolean insertUser(User user) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if(userExists(user.getUsername())){
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
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            return true;
        }
    }

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
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            
        }
    }

    @Override
    public ArrayList<Profile> getProfiles(String username, String password) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {
                        return user.getProfiles();
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            
        }
        return null;
    }

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
            if(isAdded){
                mapper.writeValue(jsonFile, users);
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            
        }
    }

    @Override
    public void deleteProfile(String username, Profile profile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    user.removeProfile(profile);

                }
            }
            mapper.writeValue(jsonFile, users);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            
        }
    }
}
