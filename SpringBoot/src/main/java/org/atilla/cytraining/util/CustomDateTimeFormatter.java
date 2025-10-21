package org.atilla.cytraining.util;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

/**
 * Utils for date formatting.
 */
public class CustomDateTimeFormatter {

    /**
     * Standard french formatting.
     */
    public static DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Format a duration to string.
     *
     * @param duration the duration
     * @return the String representation of the duration, reversible using Duration.parse
     */
    public static String formatDuration(Duration duration) {
        int hours = duration.toHoursPart();
        int minutes = duration.toMinutesPart();

        String hoursStr = hours < 10 ? "0" + hours : String.valueOf(hours);
        String minutesStr = minutes < 10 ? "0" + minutes : String.valueOf(minutes);

        return hoursStr + ":" + minutesStr;
    }
}