package fyi.jackson.activejournal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Formatter {
    public static String millisToDurationString(long duration) {
        String result = "";
        duration /= 1000;

        long seconds = duration % 60;
        duration -= seconds;
        duration /= 60;
        String secondsStr = (seconds < 10 ? "0": "") + String.valueOf(seconds);

        long minutes = duration % 60;
        duration -= seconds;
        duration /= 60;
        String minutesStr = (minutes < 10 ? "0": "") + String.valueOf(minutes);

        if (duration != 0) {
            String hoursStr = String.valueOf(duration);
            result = hoursStr + ":";
        }

        result += minutesStr + ":" + secondsStr;

        return result;
    }

    public static String distanceToString(double distance) {
        return String.format("%.2f", distance);
    }

    public static String speedToString(double speed) {
        return String.format("%.2f", speed);
    }

    public static String generateDefaultActivityName(long timestamp, String activityType) {
        String dayName = getDayName(timestamp);
        String timeOfDay = getTimeOfDay(timestamp);
        return dayName + " " + timeOfDay + " " + activityType;
    }

    private static String getDayName(long timestamp) {
        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        sdf.setTimeZone(df.getTimeZone());
        String dayString = sdf.format(new Date(timestamp));
        return dayString;
    }

    private static String getTimeOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 5 || hour > 21) {
            return "Night";
        } else if (hour < 12) {
            return "Morning";
        } else if (hour < 17) {
            return "Afternoon";
        } else {
            return "Evening";
        }
    }
}
