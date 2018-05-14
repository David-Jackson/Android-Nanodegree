package fyi.jackson.drew.popularmovies;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

import fyi.jackson.drew.popularmovies.data.MovieContract;
import fyi.jackson.drew.popularmovies.fragment.MovieListFragment;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.ui.ScrollControlAppBarLayoutBehavior;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int ID_POPULAR_MOVIE_LOADER = 473;
    private static final int ID_TOP_RATED_MOVIE_LOADER = 874;

    public FloatingActionButton fab;
    public ImageView appBarImageView;
    public AppBarLayout appBarLayout;
    public CollapsingToolbarLayout toolbarLayout;
    private ScrollControlAppBarLayoutBehavior appBarLayoutBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, MovieListFragment.newInstance())
                .commit();

        fab = findViewById(R.id.fab);
        appBarLayout = findViewById(R.id.app_bar);
        appBarImageView = findViewById(R.id.app_bar_image);

        appBarLayoutBehavior = (ScrollControlAppBarLayoutBehavior)
                ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
    }

    public void disableAppBar() {
        appBarLayoutBehavior.setScrollBehavior(false);
    }

    public void enableAppBar() {
        appBarLayoutBehavior.setScrollBehavior(true);
    }

}
