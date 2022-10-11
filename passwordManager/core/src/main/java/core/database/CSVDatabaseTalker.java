package core.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import core.Profile;
import core.User;



public class CSVDatabaseTalker implements DatabaseTalker{

    private File file;


    //constructor takes the path of the database(currently a csv file)
    public CSVDatabaseTalker(String csvFile){
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


    public ArrayList<Profile> getProfiles(String username){
        return null;
    }



    //returns true if the user is stored in the database, false if not
    public boolean userExists(String username){
        return getUsers().containsKey(username);
    }

    //if the user allready exists, insertUser() does nothing and returns false, if it does not exists, the user is added to the database
    public boolean insertUser(User user){
        if(!userExists(user.getUsername())){
            try{
                Map<String, String> users = getUsers();
                FileOutputStream fos = new FileOutputStream(this.file);
     
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                for(Map.Entry u : users.entrySet()){
                    bw.write(u.getKey() + "," + u.getValue());
                    bw.newLine();
                }
                bw.write(user.getUsername() + "," + user.getPassword());
                bw.close();
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            
        }else{
            return false;
        }
    }

    //deletes user and returns true if the user existed, if not return false
    public void deleteUser(String username){
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
                
            }
            
        }else{
            
        }
    }

    //returns true if the the password matches the username in the database
    public boolean checkPassword(String username, String password){
        if(userExists(username)){
            return getUsers().get(username).equals(password);
        }else{
            return false;
        }
    }


    @Override
    public boolean insertProfile(String username, Profile profile) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void deleteProfile(String username, Profile profile) {
        // TODO Auto-generated method stub
        
    }
}
