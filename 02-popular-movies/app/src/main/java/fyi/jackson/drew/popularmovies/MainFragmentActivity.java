package fyi.jackson.drew.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import fyi.jackson.drew.popularmovies.fragment.MovieListFragment;

public class MainFragmentActivity extends AppCompatActivity {

    public ImageView appBarImageView;
    public AppBarLayout appBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, MovieListFragment.newInstance())
                .commit();

        appBarLayout = findViewById(R.id.app_bar);
        appBarImageView = findViewById(R.id.app_bar_image);

        setTitle("Test Title");
    }
}
