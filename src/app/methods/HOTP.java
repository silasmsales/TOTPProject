package app.methods;

import java.math.BigInteger;

/**
 *
 * @author silasmsales
 */
public class HOTP {

    public static String generateHTOP(String key, String counter) {
        String result = null;

        while (counter.length() < 16) {
            counter = "0" + counter;
        }

        byte[] msg = hexStrToBytes(counter);
        byte[] k = hexStrToBytes(key);
        byte[] hash = HMAC.hmac(k, msg);
        int offset = hash[hash.length - 1] & 0xf;
        int binary
                = ((hash[offset] & 0x7f) << 24)
                | ((hash[offset + 1] & 0xff) << 16)
                | ((hash[offset + 2] & 0xff) << 8)
                | (hash[offset + 3] & 0xff);
        int otp = binary % 1000000;

        result = Integer.toString(otp);

        while (result.length() < 6) {
            result = "0" + result;
        }

        return result;
    }

    private static byte[] hexStrToBytes(String hex) {
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
        byte[] result = new byte[bArray.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = bArray[i + 1];
        }
        return result;
    }


}
