import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

public class Password {
    private static final int KEY_LENGTH = 128; //in Bits
    private static final int ITERATIONS = 10000;

    private byte salt [];
    private SecretKeySpec secretKey;
    private String hashedPass="";

    //used when creating a new account
    public Password(String password){
        Random randomNum = new SecureRandom();
        this.salt = createSalt(randomNum);
        this.hashedPass = createSecretKey(password, salt);
    }

    //used when loading an existing account
    public Password(String salt, String hashedPass){

    }


    private SecretKeySpec createSecretKey(String password, byte [] salt){
        char [] passwordInChar = password.toCharArray();
        SecretKeyFactory factory = null;
        SecretKey key = null;
        try{
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512"); //specifies the encryption algorithm to use
        }
        catch (NoSuchAlgorithmException e){
            System.out.println("There is a problem with the encryption algorithm tyring to be used.");
        }

        /**Password based encryption*/
        PBEKeySpec keySpec = new PBEKeySpec(passwordInChar, salt, ITERATIONS, KEY_LENGTH);
        /**At the line below produces a key, but it is not AES (symmetric)*/
        try{
            key = factory.generateSecret(keySpec);

        }catch (InvalidKeySpecException e){
            System.out.println("There is a problem with the keySpec being used");
        }

        return new SecretKeySpec(key.getEncoded(), "AES");
    }

    /**Creates a random number*/
    private byte[] createSalt(Random randomNum) {
        salt = new byte[64];
        randomNum.nextBytes(salt);
        return salt;
    }

    public String getSalt(){
        return new String(salt);
    }

    public String getHashedPassword(){
        return hashedPass;
    }
}
