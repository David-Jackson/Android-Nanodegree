package fyi.jackson.drew.popularmovies;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        supportPostponeEnterTransition();

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
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }
                });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
