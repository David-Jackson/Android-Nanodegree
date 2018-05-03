package fyi.jackson.drew.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;

import fyi.jackson.drew.popularmovies.fragment.MovieListFragment;
import fyi.jackson.drew.popularmovies.ui.ScrollControlAppBarLayoutBehavior;

public class MainActivity extends AppCompatActivity {

    public ImageView appBarImageView;
    public AppBarLayout appBarLayout;
    public CollapsingToolbarLayout toolbarLayout;
    private ScrollControlAppBarLayoutBehavior appBarLayoutBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarLayout = findViewById(R.id.toolbar_layout);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, MovieListFragment.newInstance())
                .commit();

        appBarLayout = findViewById(R.id.app_bar);
        appBarImageView = findViewById(R.id.app_bar_image);

        appBarLayoutBehavior = (ScrollControlAppBarLayoutBehavior)
                ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setChecked(true);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        item.setChecked(true);
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.menu_sort_by_popularity:
//                setTitle(R.string.title_popular_movies);
//                popularCallHandler.populateAdapter();
//                break;
//            case R.id.menu_sort_by_rating:
//                setTitle(R.string.title_rated_movies);
//                topRatedCallHandler.populateAdapter();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void disableAppBar() {
        appBarLayoutBehavior.setScrollBehavior(false);
    }

    public void enableAppBar() {
        appBarLayoutBehavior.setScrollBehavior(true);
    }
}
