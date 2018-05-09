package fyi.jackson.drew.popularmovies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import fyi.jackson.drew.popularmovies.MainActivity;
import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.network.MovieApiService;
import fyi.jackson.drew.popularmovies.recycler.MovieListAdapter;
import fyi.jackson.drew.popularmovies.network.MovieCallHandler;
import fyi.jackson.drew.popularmovies.ui.MovieItemClickListener;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListFragment extends Fragment implements
        MovieItemClickListener {

    public static final String TAG = MovieListFragment.class.getSimpleName();
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";

    RecyclerView recyclerView;
    MovieListAdapter adapter;
    MovieApiService apiService;

    MovieCallHandler popularCallHandler;
    MovieCallHandler topRatedCallHandler;
    MovieCallHandler activeCallHandler;

    public MovieListFragment() {}

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new MovieListAdapter(null, this);
        setupRetrofit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        List<Movie> initialData =
                (activeCallHandler == null) ? null : activeCallHandler.getMovieArrayList();

        adapter.setMovieList(initialData);
        recyclerView = view.findViewById(R.id.rv_movies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6);
        gridLayoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (initialData == null) activeCallHandler.populateAdapter();

        MainActivity fragmentActivity = (MainActivity) getActivity();
        fragmentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fragmentActivity.toolbarLayout.setTitle(getString(R.string.title_popular_movies));
        fragmentActivity.appBarLayout.setExpanded(false);
        fragmentActivity.disableAppBar();
    }

    private void setupRetrofit() {
        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(MovieUtils.apiKeyInterceptor(getContext()));
        OkHttpClient client = builder.build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(MovieApiService.class);
        popularCallHandler = new MovieCallHandler(apiService.getPopularMovies(), adapter);
        topRatedCallHandler = new MovieCallHandler(apiService.getTopRatedMovies(), adapter);
        activeCallHandler = popularCallHandler;
    }

    @Override
    public void onMovieClicked(int pos, Movie movie, ImageView sharedImageView) {
        String transitionName = ViewCompat.getTransitionName(sharedImageView);

        Fragment movieDetailFragment =
                MovieDetailFragment.newInstance(movie, transitionName);

        getFragmentManager()
                .beginTransaction()
                .addSharedElement(sharedImageView, transitionName)
                .addToBackStack(TAG)
                .replace(R.id.content, movieDetailFragment)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        int activeMenuItemIndex = (activeCallHandler == popularCallHandler) ? 0 : 1;
        menu.getItem(activeMenuItemIndex).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_sort_by_popularity:
                setTitle(R.string.title_popular_movies);
                activeCallHandler = popularCallHandler;
                break;
            case R.id.menu_sort_by_rating:
                setTitle(R.string.title_rated_movies);
                activeCallHandler = topRatedCallHandler;
                break;
        }
        activeCallHandler.populateAdapter();
        return super.onOptionsItemSelected(item);
    }

    public void setTitle(CharSequence title) {
        ((MainActivity) getActivity()).toolbarLayout.setTitle(title);
    }

    public void setTitle(int resId) {
        CharSequence title = getString(resId);
        setTitle(title);
    }
}
