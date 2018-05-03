package fyi.jackson.drew.popularmovies;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fyi.jackson.drew.popularmovies.model.DetailedMovie;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.network.MovieApiService;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static fyi.jackson.drew.popularmovies.MainActivity.API_BASE_URL;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        supportPostponeEnterTransition();

        Bundle extras = getIntent().getExtras();
        Movie movie = extras.getParcelable(MainActivity.EXTRA_MOVIE_ITEM);

        ImageView posterImageView = findViewById(R.id.iv_poster);
        setTitle(movie.getTitle());


        String posterPath = movie.getPosterPath();
        String posterUrl = MovieUtils.buildPosterUrl(posterPath, MovieUtils.API_POSTER_SIZE_W342);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = extras.getString(MainActivity.EXTRA_MOVIE_IMAGE_TRANSITION_NAME);
            posterImageView.setTransitionName(imageTransitionName);
        }

        Picasso.get()
                .load(posterUrl)
                .noFade()
                .into(posterImageView, new Callback() {
                    @Override
                    public void onSuccess() {
//                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
//                        supportStartPostponedEnterTransition();
                    }
                });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                AppBarLayout appBarLayout = findViewById(R.id.app_bar);
                appBarLayout.setExpanded(false);
            }
        });


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(MovieUtils.apiKeyInterceptor(this));
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        MovieApiService apiService = retrofit.create(MovieApiService.class);
        apiService.getMovie(movie.getId()).enqueue(new retrofit2.Callback<DetailedMovie>() {
            @Override
            public void onResponse(Call<DetailedMovie> call, Response<DetailedMovie> response) {
                updateUI(response.body());
            }

            @Override
            public void onFailure(Call<DetailedMovie> call, Throwable t) {

            }
        });
    }

    private void updateUI(DetailedMovie movie) {
        TextView tagline = findViewById(R.id.tv_tagline);
        tagline.setText(movie.getTagline());

        ImageView backdrop = findViewById(R.id.app_bar_image);
        Picasso.get()
                .load(MovieUtils.buildPosterUrl(movie.getBackdropPath()))
                .into(backdrop);
    }
}
