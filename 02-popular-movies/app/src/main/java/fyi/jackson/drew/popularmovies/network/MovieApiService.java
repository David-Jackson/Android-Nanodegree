package fyi.jackson.drew.popularmovies.network;

import java.util.List;

import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieList> getPopularMovies();
}
