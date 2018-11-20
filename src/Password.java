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
    private static final int KEY_LENGTH = 128; //in Bits
    private static final int ITERATIONS = 10000;

    private byte salt [];
    private SecretKeySpec secretKey;
    private String hashedPass="";

    //used when creating a new account
    public Password(String password){
        Random randomNum = new SecureRandom();
        salt = createSalt(randomNum);
        this.secretKey = createSecretKey(password, salt);
        this.hashedPass = createEncryptedPassword(password,secretKey);

        System.out.println(decryptPassword(hashedPass,secretKey));
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
