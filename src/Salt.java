import java.security.SecureRandom;

public class Salt {

    private static final int SALT_LARGO=32;
    private static final int SALT_SET_CARACTER_INICIAL=35; // numeral '#'
    private static final int SALT_SET_CARACTER_FINAL=122; // numeral 'z'
    private static final int CARACTER_DOBLE_COMILLA=34;
    private static final int CARACTER_COMILLA_SIMPLE=39;
    private static final int CARACTER_SLASH=47;
    private static final int CARACTER_BACK_SLASH=92;
    private static final int CARACTER_APOSTROFE=96;



    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        StringBuilder salt = random.ints(SALT_SET_CARACTER_INICIAL, SALT_SET_CARACTER_FINAL + 1)
                .filter(i -> i != CARACTER_DOBLE_COMILLA && i != CARACTER_COMILLA_SIMPLE &&
                        i != CARACTER_SLASH && i != CARACTER_BACK_SLASH &&
                        i != CARACTER_APOSTROFE)
                .limit(SALT_LARGO)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
        return salt.toString();
    }

}
