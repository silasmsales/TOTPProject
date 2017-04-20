package app.methods;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author silasmsales
 */
public class HMAC {

    public static byte[] hmac(byte[] keyBytes, byte[] text) {
        try {
            Mac hmac;
            hmac = Mac.getInstance("HmacSHA1");
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (Exception e) {
            return null;
        }
    }

}
