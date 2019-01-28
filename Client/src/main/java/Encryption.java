import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

class Encryption {

    private static PBEParameterSpec r = null;
    private static byte[] salt = null;

    public static byte[] getSalt(){ return salt; }

    public static void setSalt(byte[] in){
        salt = in;
    }

    // text could be id or encrypted id
    public static byte[] crypto(int mode, String pass, byte[]  text) throws BadPaddingException {
        if(mode == Cipher.ENCRYPT_MODE || salt == null){
            salt = new byte[16];
            SecureRandom secRandom = new SecureRandom();
            secRandom.nextBytes(salt);
        }
        int iterCount = 10000;
        String algo = "PBEWithHmacSHA512AndAES_128";
        IvParameterSpec ivParamSpec = new IvParameterSpec(salt);
        PBEParameterSpec  pbeParamSpec = new PBEParameterSpec(salt, iterCount,ivParamSpec);

        PBEKeySpec keySpec = new PBEKeySpec(pass.toCharArray());

        try {
            SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
            SecretKey secretKey = kf.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(mode, secretKey, pbeParamSpec);
            return cipher.doFinal(text);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeySpecException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
