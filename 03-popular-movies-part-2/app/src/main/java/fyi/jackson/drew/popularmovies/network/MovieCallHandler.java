package fyi.jackson.drew.popularmovies.network;

import android.content.Context;
import android.util.Log;

import java.util.List;

import fyi.jackson.drew.popularmovies.data.MovieContract;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.model.MovieList;
import fyi.jackson.drew.popularmovies.recycler.MovieListAdapter;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieCallHandler {

    private static final String TAG = MovieCallHandler.class.getSimpleName();

    private int attempts = 0;
    Context context;
    Call<MovieList> call;
    MovieListAdapter adapter;
    MovieList movieList;

    public MovieCallHandler(Context context, Call<MovieList> call, MovieListAdapter adapter) {
        this.context = context;
        this.call = call;
        this.adapter = adapter;
    }

    public void populateAdapter() {
        if (movieList == null) {
            if (attempts++ > 10) {
                Log.e(TAG, "populateAdapter: Attempts exceeded limit");
                return;
            }
            executeCall();
        } else {
            adapter.setMovieList(movieList.getResults());
            adapter.notifyDataSetChanged();
        }
    }

    private void executeCall() {
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                movieList = response.body();
//                populateAdapter();
                storeResults();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.e(TAG, "onFailure: Call Failed to execute");
            }
        });
    }

    public List<Movie> getMovieArrayList() {
        return (movieList == null) ? null : movieList.getResults();
    }

    private void storeResults() {
        if (movieList == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                context.getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI,
                        MovieUtils.listToContentValuesArray(movieList.getResults()));
            }
        }).start();
    }
}
