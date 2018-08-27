package com.example.xyzreader.remote;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Config {
    public static final URL BASE_URL;
    private static String TAG = Config.class.toString();

    // This is the original URL provided in the project, with MASSIVE bodies of text
    // that take a while to render
    private static final String DEFAULT_UDACITY_URL = "https://go.udacity.com/xyz-reader-json";

    // This is a URL for the same data provided in the default above, except the bodies of text
    // have been trimmed to only 5000 characters
    private static final String TRIMMED_URL = "https://api.jsonbin.io/b/5b845e403ffac56f4bd59782";

    static {
        URL url = null;
        try {
            url = new URL(TRIMMED_URL);
        } catch (MalformedURLException ignored) {
            // TODO: throw a real error
            Log.e(TAG, "Please check your internet connection.");
        }

        BASE_URL = url;
    }
}
