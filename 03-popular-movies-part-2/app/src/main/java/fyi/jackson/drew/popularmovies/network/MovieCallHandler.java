package fyi.jackson.drew.popularmovies.network;

import android.util.Log;

import java.util.List;

import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.model.MovieList;
import fyi.jackson.drew.popularmovies.recycler.MovieListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieCallHandler {

    private static final String TAG = MovieCallHandler.class.getSimpleName();

    private int attempts = 0;
    Call<MovieList> call;
    MovieListAdapter adapter;
    MovieList movieList;

    public MovieCallHandler(Call<MovieList> call, MovieListAdapter adapter) {
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
                populateAdapter();
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
}
