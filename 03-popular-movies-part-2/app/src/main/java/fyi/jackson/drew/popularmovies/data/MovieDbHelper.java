package fyi.jackson.drew.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fyi.jackson.drew.popularmovies.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    public static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_ID            + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_TITLE         + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW      + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER        + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_BACKDROP      + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POPULARITY    + " REAL NOT NULL, " +
                MovieEntry.COLUMN_VIDEO         + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_VOTE_AVERAGE  + " REAL NOT NULL, " +
                MovieEntry.COLUMN_VOTE_COUNT    + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE  + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_FAVORITE      + " INTEGER NOT NULL, " +
                "UNIQUE (" + MovieEntry.COLUMN_ID + ") ON CONFLICT IGNORE);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
