package fyi.jackson.drew.popularmovies.data;

import java.util.List;

import fyi.jackson.drew.popularmovies.model.Movie;

public interface MovieDataCallback {
    void onUpdate(List<Movie> movieList);
}
