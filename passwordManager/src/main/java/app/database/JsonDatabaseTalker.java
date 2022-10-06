package app.database;

import java.util.ArrayList;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DataBindingException;
import com.fasterxml.jackson.core.exc.StreamReadException;

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
        }finally{

        }
    }

    @Override
    public void insertUser(User user) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            User[] newUsers = new User[users.length + 1];
            for (int i = 0; i < users.length; i++) {
                newUsers[i] = users[i];
            }
            newUsers[users.length] = user;
            mapper.writeValue(jsonFile, newUsers);
        }finally{
            
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

        }finally{
            
        }
        return null;
    }

    @Override
    public void insertProfile(String username, Profile profile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            User[] users = mapper.readValue(jsonFile, User[].class);
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    user.addProfile(profile);
                }
            }
            mapper.writeValue(jsonFile, users);
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
        }finally{
            
        }
    }

    @Override
    public void deleteProfile(String username, Profile profile){

    }
}
