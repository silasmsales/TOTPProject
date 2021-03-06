package app.methods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author silasmsales
 */
public class TOTP {

    private static final long INITIAL_TIME = 0;

    public static String generateTOTP(String uniqueID, long testTime, long timeStep) {

        String steps;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));

        long T = (testTime - INITIAL_TIME) / timeStep;
        
        steps = Long.toHexString(T).toUpperCase();
        while (steps.length() < 16) {
            steps = "0" + steps;
        }

        return HOTP.generateHTOP(uniqueID, steps);
    }
}
