import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class Main {

    private static IvParameterSpec ivPS;
    private static final String alg = "AES/CBC/PKCS5Padding";
    private static final String k = "MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIz";

    private static IvParameterSpec generateIv() { byte [] iv = new byte [16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private static SecretKeySpec generateKey() {
        return new SecretKeySpec(k.getBytes(StandardCharsets.UTF_8), "AES");
    }

    private static byte[] processEncryption(boolean encryption, String tBD) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skp = generateKey();
        Cipher cipher = Cipher.getInstance(alg);
        if (encryption) {
            ivPS = generateIv();
            cipher.init(Cipher.ENCRYPT_MODE, skp, ivPS);
            return Base64.getEncoder().encode(cipher.doFinal(tBD.getBytes(StandardCharsets.UTF_8)));
        } else {
            cipher.init(Cipher.DECRYPT_MODE, skp, ivPS);
            return cipher.doFinal(Base64.getDecoder().decode(tBD.getBytes(StandardCharsets.UTF_8)));
        }
    }
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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