package fyi.jackson.drew.popularmovies.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fyi.jackson.drew.popularmovies.MainActivity;
import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;

public class MovieDetailFragment extends Fragment {

    public static final String EXTRA_MOVIE_ITEM = "EXTRA_MOVIE_ITEM";
    public static final String EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME";

    public MovieDetailFragment() {}

    public static MovieDetailFragment newInstance(Movie movie, String transitionName) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MOVIE_ITEM, movie);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(
                    TransitionInflater.from(getContext())
                            .inflateTransition(android.R.transition.move));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        Movie movie = getArguments().getParcelable(EXTRA_MOVIE_ITEM);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

        String posterUrl = MovieUtils.buildPosterUrl(movie.getPosterPath(), MovieUtils.API_POSTER_SIZE_W342);
        String backdropUrl = MovieUtils.buildPosterUrl(movie.getBackdropPath(), MovieUtils.API_POSTER_SIZE_W342);

        TextView taglineTextView = view.findViewById(R.id.tv_tagline);

        ImageView posterImageView = view.findViewById(R.id.iv_poster);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            posterImageView.setTransitionName(transitionName);
        }

        Picasso.get()
                .load(posterUrl)
                .noFade()
                .into(posterImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        startPostponedEnterTransition();
                    }
                });


        MainActivity fragmentActivity = (MainActivity) getActivity();
        fragmentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentActivity.enableAppBar();
        fragmentActivity.appBarLayout.setExpanded(true);
        fragmentActivity.toolbarLayout.setTitle(movie.getTitle());

        Picasso.get()
                .load(backdropUrl)
                .into(fragmentActivity.appBarImageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
