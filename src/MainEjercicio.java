import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class MainEjercicio {


    private static IvParameterSpec ivPS;
    private static final String alg = "AES/CBC/PKCS5Padding";
    private static final String k = "MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIz";
    private static final String KEY_SALT_BD = "1255a2ed998fa2d6198671eddbf1a2aa"; //Key para la BD
    private static final int INTERACCIONES = 65536;
    private static final int LARGO_KEY = 256;

    private static IvParameterSpec generateIv() { byte [] iv = new byte [16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private static SecretKeySpec generateKey() {
        return new SecretKeySpec(k.getBytes(StandardCharsets.UTF_8), "AES");
    }

    private static byte[] processEncryption(boolean encryption, String tBD) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
       // SecretKeySpec skp = generateKey();
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); //Se genera key Hmac
        KeySpec spec = new PBEKeySpec(KEY_SALT_BD.toCharArray(), k.getBytes(StandardCharsets.UTF_8), INTERACCIONES, LARGO_KEY);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec skp = new SecretKeySpec(Arrays.copyOf(tmp.getEncoded(),16),"AES");

        if (encryption) {
            ivPS = generateIv();
            cipher.init(Cipher.ENCRYPT_MODE,skp, ivPS);
            return Base64.getEncoder().encode(cipher.doFinal(tBD.getBytes(StandardCharsets.UTF_8)));
        } else {
            cipher.init(Cipher.DECRYPT_MODE, skp, ivPS);
            return cipher.doFinal(Base64.getDecoder().decode(tBD.getBytes(StandardCharsets.UTF_8)));
        }
    }
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        System.out.println("Hello world!");

        byte[] r, result;
        String tBD;
        final String tBE = "Using some encryption algorithm";
        r = processEncryption(true, tBE);
        tBD = new String(r, StandardCharsets.UTF_8);
        System.out.println("Encryption process: "+ tBD);

        result = processEncryption(false,tBD);
        System.out.println("Decryption process: "+ new String(result, StandardCharsets.UTF_8) );

    }

}
