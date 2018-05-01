package fyi.jackson.drew.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import fyi.jackson.drew.popularmovies.model.DummyData;
import fyi.jackson.drew.popularmovies.model.MovieList;
import fyi.jackson.drew.popularmovies.network.MovieApiService;
import fyi.jackson.drew.popularmovies.recycler.MovieListAdapter;
import fyi.jackson.drew.popularmovies.utils.MovieCallHandler;
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
    MovieListAdapter adapter;
    MovieApiService apiService;

    MovieCallHandler popularCallHandler;
    MovieCallHandler topRatedCallHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rv_movies);
        adapter = new MovieListAdapter(DummyData.getMovies(), 6);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        setupRetrofit();

        popularCallHandler.populateAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_sort_by_popularity:
                popularCallHandler.populateAdapter();
                break;
            case R.id.menu_sort_by_rating:
                topRatedCallHandler.populateAdapter();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        popularCallHandler = new MovieCallHandler(apiService.getPopularMovies(), adapter);
        topRatedCallHandler = new MovieCallHandler(apiService.getTopRatedMovies(), adapter);
    }
}
