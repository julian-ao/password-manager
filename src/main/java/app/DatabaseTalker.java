package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javafx.util.Pair;

public class DatabaseTalker {

    File file;


    //constructor takes the path of the database(currently a csv file)
    DatabaseTalker(String csvFile){
        file = new File(csvFile);
    }
    

    private Map<String, String> getUsers(){
        Map<String,String> users = new HashMap<String,String>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.file));
            while(reader.ready()){
                String[]  line = reader.readLine().split(",");
                users.put(line[0], line[1]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }


    //returns true if the user is stored in the database, false if not
    public boolean userExists(String username){
        return getUsers().containsKey(username);
    }

    //if the user allready exists, inserUser() does nothing and returns false, if it does not exists, the user is added to the database
    public boolean insertUser(String username, String password){
        if(!userExists(username)){
            try{
                Map<String, String> users = getUsers();
                FileOutputStream fos = new FileOutputStream(this.file);
    
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                for(Map.Entry user : users.entrySet()){
                    bw.write(user.getKey() + "," + user.getValue());
                    bw.newLine();
                }
                bw.write(username + "," + password);
                bw.close();
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }else{
            return false;
        }
    }

    //deletes user and returns true if the user existed, if not return false
    public boolean deleteUser(String username){
        if(userExists(username)){
            try{
                Map<String, String> users = getUsers();
                users.remove(username);
                FileOutputStream fos = new FileOutputStream(this.file);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                for(Map.Entry user : users.entrySet()){
                    bw.write(user.getKey() + "," + user.getValue());
                    bw.newLine();
                }
                bw.close();
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }else{
            return false;
        }
    }

    //returns true if the the password matches the username in the database
    public boolean checkPassword(String username, String password){
        if(userExists(username)){
            String correctPassword = getUsers().get(username);
            return getUsers().get(username).equals(password);
        }else{
            return false;
        }
    }
}
