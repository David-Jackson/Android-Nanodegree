package fyi.jackson.drew.popularmovies.utils;

import android.content.Context;

import java.io.IOException;

import fyi.jackson.drew.popularmovies.R;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class MovieUtils {

    public static final String API_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String API_POSTER_SIZE_ORIGINAL = "original";
    public static final String API_POSTER_SIZE_W92 = "w92";
    public static final String API_POSTER_SIZE_W154 = "w154";
    public static final String API_POSTER_SIZE_W185 = "w185";
    public static final String API_POSTER_SIZE_W342 = "w342";
    public static final String API_POSTER_SIZE_W500 = "w500";
    public static final String API_POSTER_SIZE_W780 = "w780";

    public static String buildPosterUrl(String suffix) {
        return buildPosterUrl(suffix, API_POSTER_SIZE_ORIGINAL);
    }

    public static String buildPosterUrl(String suffix, String size) {
        return API_POSTER_BASE_URL + size + suffix;
    }

    public static String buildPosterUrl(String suffix, int minWidth) {
        String size;
        if (minWidth <= 92) {
            size = API_POSTER_SIZE_W92;
        } else if (minWidth <= 154) {
            size = API_POSTER_SIZE_W154;
        } else if (minWidth <= 185) {
            size = API_POSTER_SIZE_W185;
        } else if (minWidth <= 342) {
            size = API_POSTER_SIZE_W342;
        } else if (minWidth <= 500) {
            size = API_POSTER_SIZE_W500;
        } else if (minWidth <= 780) {
            size = API_POSTER_SIZE_W780;
        } else {
            size = API_POSTER_SIZE_ORIGINAL;
        }
        return buildPosterUrl(suffix, size);
    }

    public static Interceptor apiKeyInterceptor(Context context) {
        final String apiKey = context.getString(R.string.api_key);

        // Define the interceptor, add authentication headers for API key
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

}
