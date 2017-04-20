package app;

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

        String steps = "0";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));

        long T = (testTime - INITIAL_TIME) / timeStep;
        steps = Long.toHexString(T).toUpperCase();
        while (steps.length() < 16) {
            steps = "0" + steps;
        }

        String fmtTime = String.format("%1$-11s", testTime);
        String utcTime = dateFormat.format(new Date(testTime * 1000));

        return HOTP.generateHTOP(uniqueID, steps);
    }
}
