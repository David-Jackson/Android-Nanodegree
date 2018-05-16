package fyi.jackson.drew.popularmovies.network;

import android.content.Context;
import android.util.Log;

import java.util.List;

import fyi.jackson.drew.popularmovies.data.MovieDataCallback;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.model.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieCallHandler {

    private static final String TAG = MovieCallHandler.class.getSimpleName();

    private int attempts = 0;
    Context context;
    Call<MovieList> call;
    MovieDataCallback callback;
    MovieList movieList;

    public MovieCallHandler(Context context, Call<MovieList> call, MovieDataCallback callback) {
        this.context = context;
        this.call = call;
        this.callback = callback;
    }

    public void request() {
        if (movieList == null) {
            if (attempts++ > 10) {
                Log.e(TAG, "populateAdapter: Attempts exceeded limit");
                return;
            }
            executeCall();
        } else {
            attempts = 0;
            callback.onUpdate(movieList.getResults());
        }
    }

    private void executeCall() {
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                movieList = response.body();
                request();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.e(TAG, "onFailure: Call Failed to execute");
                callback.onUpdate(null);
            }
        });
    }

    public List<Movie> getMovieArrayList() {
        return (movieList == null) ? null : movieList.getResults();
    }
}
