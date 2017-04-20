

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TOTPBackup {

    private static byte[] hexStrToBytes(String hex) {
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
        byte[] result = new byte[bArray.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = bArray[i + 1];
        }
        
        return result;
    }

    private static byte[] hmac(byte[] keyBytes, byte[] text) {
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

    public static String generateTOTP(String key, String time) {
        String result = null;

        while (time.length() < 16) {
            time = "0" + time;
        }
        
        byte[] msg = hexStrToBytes(time);
        byte[] k = hexStrToBytes(key);
        byte[] hash = hmac(k, msg);

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

    /*    public static void main(String[] args) {
    String seed = "3132333435363738393031323334353637383930";
    long T0 = 0;
    long X = 30;
    long testTime = 59L;
    
    String steps = "0";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    df.setTimeZone(TimeZone.getTimeZone("UTC"));
    long T = (testTime - T0) / X;
    steps = Long.toHexString(T).toUpperCase();
    while (steps.length() < 16) {
    steps = "0" + steps;
    }
    String fmtTime = String.format("%1$-11s", testTime);
    String utcTime = df.format(new Date(testTime * 1000));
    System.out.println("|Time(sec)  ||Time(UTC)          ||Steps(X)HEX     || TOTP |");
    System.out.print("|"+fmtTime+"||"+utcTime+"||"+steps+"|");
    System.out.println("|"+generateTOTP(seed, steps)+"|");
    }*/
}
