import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.Random;

public class Password {
    private static final int KEY_LENGTH = 256; //in Bits
    private static final int ITERATIONS = 10000;
    private static final int SIZE_OF_SALT = 64;

    private byte salt [];
    private byte hashCode [];
    private SecretKeySpec secretKey;
    private String hashedPass="";

    //used when creating a new account
    public Password(String password){
        salt = createSalt(SIZE_OF_SALT);

        /**To be used with AES encryption*/
//        this.secretKey = createSecretKey(password, salt);
//        this.hashedPass = createEncryptedPassword(password,secretKey);
//
//        System.out.println(decryptPassword(hashedPass,secretKey));

        /**Salted-Hash*/
        hashCode = hashPassword(password,salt);

        for (byte i: hashCode){
            System.out.print(i);
        }
        System.out.println();

    }

    /**Hashes a password. Returns null if the password could not be hashed*/
    private byte[] hashPassword(String password, byte [] salt) {
        char [] passwordToChar = password.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(passwordToChar, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = null;
        try{
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is an error with the encryption algorithm being used to hash the password");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }



    //used when loading an existing account
    public Password(String salt, String hashedPass){

    }

    private String createEncryptedPassword(String password, SecretKeySpec secretKey) {
        Cipher pbeCipher;
        byte [] cipherText = null;
        byte [] iv = null;
        try {
            /**Specifies the transformation/operation to be performed on the input*/
            pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            AlgorithmParameters parameters = pbeCipher.getParameters();
            IvParameterSpec paramSpec = parameters.getParameterSpec(IvParameterSpec.class);
            cipherText = pbeCipher.doFinal(password.getBytes("UTF-8"));
            iv = paramSpec.getIV();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(iv) + ";;" + Base64.getEncoder().encodeToString(cipherText);
    }

    private String decryptPassword(String s, SecretKeySpec secretKey){
        String iv = s.split(";;")[0];
        String hashedPass = s.split(";;")[1];
        Cipher pbeCipher = null;
        String answer = null;
        try {
            pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");;
            pbeCipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(Base64.getDecoder().decode(iv)));
            answer = new String(pbeCipher.doFinal(Base64.getDecoder().decode(hashedPass)), "UTF-8");
        }catch (InvalidAlgorithmParameterException e){
            System.out.println("An invalid key is being used to decrypt the password");
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return answer;
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


    /**Creates a random number (salt)*/
    private byte[] createSalt(int SIZE_OF_SALT) {
        Random randomNum = new SecureRandom();
        salt = new byte[SIZE_OF_SALT];
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
