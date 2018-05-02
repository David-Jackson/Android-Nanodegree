package fyi.jackson.drew.popularmovies.utils;

import android.graphics.Color;

public class ColorUtils {

    /**
     * Determine text color (White or Dark Grey) given a background color
     *
     * @param backgroundColor Color integer of background to base text color on.
     *
     * @return Color integer
     */
    public static int getTextColor(int backgroundColor) {
        // This method of breaking out color components is sourced from:
        // https://developer.android.com/reference/android/graphics/Color
        int R = (backgroundColor >> 16) & 0xff;
        int G = (backgroundColor >>  8) & 0xff;
        int B = (backgroundColor      ) & 0xff;
        // This little piece of logic is from this StackOverflow post:
        // https://stackoverflow.com/a/3943023
        return ((R * 0.299 + G * 0.587 + B * 0.114) > 186) ? Color.DKGRAY : Color.WHITE;
    }
}
