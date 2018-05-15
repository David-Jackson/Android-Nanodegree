package fyi.jackson.drew.popularmovies.network;

import fyi.jackson.drew.popularmovies.model.DetailedMovie;
import fyi.jackson.drew.popularmovies.model.MovieList;
import fyi.jackson.drew.popularmovies.model.ReviewList;
import fyi.jackson.drew.popularmovies.model.VideoList;
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

    @GET("movie/{id}/reviews")
    Call<ReviewList> getReviews(@Path("id") int movieId);

    @GET("movie/{id}/videos")
    Call<VideoList> getVideos(@Path("id") int movieId);
}
