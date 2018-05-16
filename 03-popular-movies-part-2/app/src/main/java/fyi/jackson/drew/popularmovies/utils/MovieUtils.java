package fyi.jackson.drew.popularmovies.utils;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fyi.jackson.drew.popularmovies.BuildConfig;
import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.data.MovieContract.MovieEntry;
import fyi.jackson.drew.popularmovies.model.Movie;
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
        final String apiKey = BuildConfig.API_KEY;

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

    public static String formatDateString(String unformatted) {
        try {
            DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = readFormat.parse(unformatted);

            DateFormat displayFormat = new SimpleDateFormat("LLL d, yyyy", Locale.ENGLISH);
            return displayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return unformatted;
        }
    }

    public static List<Movie> cursorToList(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                movies.add(Movie.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public static ContentValues[] listToContentValuesArray(List<Movie> movies) {
        if (movies == null) return new ContentValues[0];
        ContentValues[] contentValues = new ContentValues[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            contentValues[i] = movies.get(i).toContentValues();
        }
        return contentValues;
    }

    public static String stripMarkdown(String string) {
        string = string.replaceAll("[*_>#]|(\\r\\n)+", "");
        return string;
    }

    // App/Web intent code from this StackOverflow answer:
    // https://stackoverflow.com/a/12439378
    public static void launchVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
