package fyi.jackson.drew.popularmovies.ui;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.List;

import fyi.jackson.drew.popularmovies.data.MovieDataCallback;
import fyi.jackson.drew.popularmovies.data.MovieDbHandler;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.network.MovieApiService;
import fyi.jackson.drew.popularmovies.network.MovieCallHandler;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static fyi.jackson.drew.popularmovies.MainActivity.API_BASE_URL;

/*
* This class handles the retrieval of data (either from web requests or local storage)
* and passes that data to a callback
*/
public class MovieDataHandler {

    public static final String TAG = MovieDataHandler.class.getSimpleName();

    public static final int METHOD_DB_POPULAR = 116;
    public static final int METHOD_DB_TOP = 393;
    public static final int METHOD_DB_FAVORITE = 394;
    public static final int METHOD_API_POPULAR = 665;
    public static final int METHOD_API_TOP = 768;
    public int activeDataMethod = METHOD_API_POPULAR;
    int dbMethod = MovieDbHandler.ID_POPULAR_MOVIE_LOADER;

    Context context;
    MovieCallHandler popularCallHandler;
    MovieCallHandler topRatedCallHandler;
    MovieDbHandler dbHandler;
    MovieDataCallback uiCallback;
    MovieDataCallback dbCallback;
    MovieDataCallback webCallback;

    public MovieDataHandler(FragmentActivity fragmentActivity, MovieDataCallback callback) {
        this.context = fragmentActivity;
        this.uiCallback = callback;

        dbCallback = new MovieDataCallback() {
            @Override
            public void onUpdate(List<Movie> movieList) {
                uiCallback.onUpdate(movieList);
            }
        };

        webCallback = new MovieDataCallback() {
            @Override
            public void onUpdate(List<Movie> movieList) {
                dbHandler.bulkInsert(movieList, dbMethod);
            }
        };

        dbHandler = new MovieDbHandler(fragmentActivity, dbCallback);


        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(MovieUtils.apiKeyInterceptor(context));
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        MovieApiService apiService = retrofit.create(MovieApiService.class);

        popularCallHandler = new MovieCallHandler(context, apiService.getPopularMovies(), webCallback);
        topRatedCallHandler = new MovieCallHandler(context, apiService.getTopRatedMovies(), webCallback);
    }

    public void setActiveDataMethod(int newMethod) {
        this.activeDataMethod = newMethod;
        requestActiveData();
    }

    public void requestActiveData() {
        switch (activeDataMethod) {
            case METHOD_DB_POPULAR:
                Log.d(TAG, "requestActiveData: Getting DB Popular");
                dbHandler.get(MovieDbHandler.ID_POPULAR_MOVIE_LOADER);
                break;
            case METHOD_DB_TOP:
                Log.d(TAG, "requestActiveData: Getting DB Top Rated");
                dbHandler.get(MovieDbHandler.ID_TOP_RATED_MOVIE_LOADER);
                break;
            case METHOD_DB_FAVORITE:
                Log.d(TAG, "requestActiveData: Getting DB Favorite");
                dbHandler.get(MovieDbHandler.ID_FAVORITE_MOVIE_LOADER);
                break;
            case METHOD_API_POPULAR:
                Log.d(TAG, "requestActiveData: Getting API Popular");
                dbMethod = MovieDbHandler.ID_POPULAR_MOVIE_LOADER;
                popularCallHandler.request();
                break;
            case METHOD_API_TOP:
                Log.d(TAG, "requestActiveData: Getting API Top Rated");
                dbMethod = MovieDbHandler.ID_TOP_RATED_MOVIE_LOADER;
                topRatedCallHandler.request();
                break;
        }
    }
}