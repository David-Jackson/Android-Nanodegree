package fyi.jackson.drew.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

import fyi.jackson.drew.popularmovies.model.Movie;

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "fyi.jackson.drew.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_ID = "movieId";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER = "posterPath";
        public static final String COLUMN_BACKDROP = "backdropPath";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_VOTE_COUNT = "voteCount";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_FAVORITE = "favorite";

        public static Uri buildMovieUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }

        public static String getSqlWhereClauseForMovie(Movie movie) {
            return COLUMN_ID + " = " + movie.getId();
        }
    }
}
