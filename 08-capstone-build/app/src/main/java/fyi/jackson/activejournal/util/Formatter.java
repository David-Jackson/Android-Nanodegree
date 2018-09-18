package fyi.jackson.activejournal.util;

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
}
