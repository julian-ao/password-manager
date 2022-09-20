package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.util.Pair;

public class DatabaseTalker {

    File file;

    DatabaseTalker(String csvFile){
        file = new File(csvFile);
    }
    

    private byte[] testHashFunction(byte[] key){//todo (create seperate class for hashing)
        byte[] result = {1, 2, 3, 4};
        return result;
    }


    private byte[] hashWithSalt(byte[] key, int salt){
        byte[] keyPlusSalt = new byte[key.length+4];
        for(int i = 0; i < key.length; i++){
            keyPlusSalt[i] = key[i];
        }

        keyPlusSalt[key.length+0] = (byte)(salt & 0xff000000);
        keyPlusSalt[key.length+1] = (byte)(salt & 0x00ff0000);
        keyPlusSalt[key.length+2] = (byte)(salt & 0x0000ff00);
        keyPlusSalt[key.length+3] = (byte)(salt & 0x000000ff);
        return testHashFunction(keyPlusSalt);
    }

    private int getSalt(String username){//todo
        return 0;
    }

    private byte[] getHashedPassPlusSalt(String username){//todo
        byte[] result = {1, 10, 100, (byte)255};
        return result;
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

    public boolean userExists(String username){//todo
        return getUsers().containsKey(username);
    }

    public void storeUser(String username, String password){
        //todo: open file for writing, write username and password
    }


    public boolean checkPassword(String username, String password){
        if(userExists(username)){
            String correctPassword = getUsers().get(username);
            return getUsers().get(username).equals(password);
        }else{
            return false;
        }
    }
}
