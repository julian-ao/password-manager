package app;

import java.util.Random;

public class DatabaseTalker {
    

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

    private boolean userExists(String username){//todo
        return false;
    }

    public void storeUser(String username, String password){
        
    }


    public boolean checkPassword(String username, String password){
        if(userExists(username)){
            
        }
        return false;
    }
}
