package fyi.jackson.drew.popularmovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fyi.jackson.drew.popularmovies.model.Genre;
import fyi.jackson.drew.popularmovies.model.Movie;

public class JSONUtils {

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_POPULARITY = "popularity";
    public static final String KEY_VIDEO = "video";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_VOTE_COUNT = "vote_count";
    public static final String KEY_RELEASE_DATE = "release_date";

    public static final String KEY_NAME = "name";

    public static Movie parseMovie(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        return parseMovie(json);
    }

    public static Movie parseMovie(JSONObject json) {
        Movie movie = new Movie();
        
        movie.setId(json.optInt(KEY_ID));
        movie.setTitle(json.optString(KEY_TITLE));
        movie.setOverview(json.optString(KEY_OVERVIEW));
        movie.setPosterPath(json.optString(KEY_POSTER_PATH));
        movie.setPopularity(json.optDouble(KEY_POPULARITY));
        movie.setVideo(json.optBoolean(KEY_VIDEO));
        movie.setVoteAverage(json.optDouble(KEY_VOTE_AVERAGE));
        movie.setVoteCount(json.optInt(KEY_VOTE_COUNT));
        movie.setReleaseDate(json.optString(KEY_RELEASE_DATE));

        return movie;
    }

    public static Genre parseGenre(JSONObject json) {
        return new Genre(
                json.optInt(KEY_ID),
                json.optString(KEY_NAME)
        );
    }

    private static List<Genre> jsonToGenresList(JSONArray json) {
        List<Genre> genres = new ArrayList<>();
        if (json != null) {
            for (int i = 0; i < json.length(); i++) {
                JSONObject genreJson = json.optJSONObject(i);
                if (genreJson == null) continue;
                genres.add(parseGenre(genreJson));
            }
        }
        return genres;
    }
}
