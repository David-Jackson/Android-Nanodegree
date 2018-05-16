package fyi.jackson.drew.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import fyi.jackson.drew.popularmovies.fragment.MovieListFragment;
import fyi.jackson.drew.popularmovies.ui.ScrollControlAppBarLayoutBehavior;

public class MainActivity extends AppCompatActivity {

    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";

    public FloatingActionButton fab;
    public ImageView appBarImageView;
    public AppBarLayout appBarLayout;
    public CollapsingToolbarLayout toolbarLayout;
    public ImageView trailerButton;
    private ScrollControlAppBarLayoutBehavior appBarLayoutBehavior;
    private Snackbar networkSnackbar;

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
        trailerButton = findViewById(R.id.iv_trailer_button);

        appBarLayoutBehavior = (ScrollControlAppBarLayoutBehavior)
                ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
    }

    public void disableAppBar() {
        appBarLayoutBehavior.setScrollBehavior(false);
    }

    public void enableAppBar() {
        appBarLayoutBehavior.setScrollBehavior(true);
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected && networkSnackbar != null) {
            networkSnackbar.dismiss();
            networkSnackbar = null;
        } else if (!isConnected && networkSnackbar == null) {
            networkSnackbar = Snackbar.make(fab, R.string.no_network, Snackbar.LENGTH_INDEFINITE);
            networkSnackbar.show();
        }

        return isConnected;
    }

}
