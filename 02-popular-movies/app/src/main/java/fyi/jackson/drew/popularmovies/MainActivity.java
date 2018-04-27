package fyi.jackson.drew.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;

import fyi.jackson.drew.popularmovies.model.DummyData;
import fyi.jackson.drew.popularmovies.model.MovieList;
import fyi.jackson.drew.popularmovies.network.MovieApiService;
import fyi.jackson.drew.popularmovies.recycler.MovieListAdapter;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_BASE_URL = "https://api.themoviedb.org/3/";

    RecyclerView recyclerView;
    MovieApiService apiService;

    Call<MovieList> popularMovieCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movies);
        final MovieListAdapter adapter = new MovieListAdapter(DummyData.getMovies(), 2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        setupRetrofit();


        popularMovieCall.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response == null || response.body() == null) onFailure(call, new Throwable());
                Log.d(TAG, "onResponse: Got movies, total of " + response.body().getResults().size());
                adapter.setMovieList(response.body().getResults());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed to get movies");
            }
        });
    }

    private void setupRetrofit() {
        final String apiKey = getString(R.string.api_key);

        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(MovieApiService.class);
        popularMovieCall = apiService.getPopularMovies();
    }
}
