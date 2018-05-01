package fyi.jackson.drew.popularmovies.network;

import fyi.jackson.drew.popularmovies.model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieList> getPopularMovies();

    @GET("movie/top_rated")
    Call<MovieList> getTopRatedMovies();
}
