package fyi.jackson.drew.popularmovies.network;

import fyi.jackson.drew.popularmovies.model.DetailedMovie;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieList> getPopularMovies();

    @GET("movie/top_rated")
    Call<MovieList> getTopRatedMovies();

    @GET("movie/{id}")
    Call<DetailedMovie> getMovie(@Path("id") int movieId);
}
