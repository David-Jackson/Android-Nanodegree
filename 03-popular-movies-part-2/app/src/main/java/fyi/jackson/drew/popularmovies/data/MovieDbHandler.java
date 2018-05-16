package fyi.jackson.drew.popularmovies.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.List;

import fyi.jackson.drew.popularmovies.MainActivity;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;

public class MovieDbHandler implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MovieDbHandler.class.getSimpleName();

    public static final int ID_POPULAR_MOVIE_LOADER = 473;
    public static final int ID_TOP_RATED_MOVIE_LOADER = 874;
    public static final int ID_FAVORITE_MOVIE_LOADER = 875;

    FragmentActivity fragmentActivity;
    LoaderManager loaderManager;
    MovieDataCallback callback;

    public MovieDbHandler(FragmentActivity fragmentActivity, MovieDataCallback callback) {
        this.loaderManager = fragmentActivity.getSupportLoaderManager();
        this.fragmentActivity = fragmentActivity;
        this.callback = callback;
    }

    public void get(int method) {
        loaderManager.destroyLoader(method);
        loaderManager.initLoader(method, null, this);
    }

    public void bulkInsert(final List<Movie> movieList, final int method) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fragmentActivity.getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI,
                        MovieUtils.listToContentValuesArray(movieList));
                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        get(method);
                    }
                });
            }
        }).start();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri uri;
        String sortOrder, selection = null;

        switch (id) {
            case ID_POPULAR_MOVIE_LOADER:
                Log.d(TAG, "onCreateLoader: Setting Popular parameters");
                uri = MovieContract.MovieEntry.CONTENT_URI;
                sortOrder = MovieContract.MovieEntry.COLUMN_POPULARITY + " DESC";
                break;
            case ID_TOP_RATED_MOVIE_LOADER:
                Log.d(TAG, "onCreateLoader: Setting Top Rated parameters");
                uri = MovieContract.MovieEntry.CONTENT_URI;
                sortOrder = MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " DESC";
                break;
            case ID_FAVORITE_MOVIE_LOADER:
                Log.d(TAG, "onCreateLoader: Setting Favorite parameters");
                uri = MovieContract.MovieEntry.CONTENT_URI;
                sortOrder = MovieContract.MovieEntry.COLUMN_TITLE + " DESC";
                selection = MovieContract.MovieEntry.COLUMN_FAVORITE;
                break;
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }

        return new CursorLoader(fragmentActivity,
                uri,
                null,
                selection,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        List<Movie> movies = MovieUtils.cursorToList(data);
        Log.d(TAG, "onLoadFinished: Movies Loaded: " + movies.size() + " | " + data.getCount());
        callback.onUpdate(movies);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: Resetting");
    }

}
