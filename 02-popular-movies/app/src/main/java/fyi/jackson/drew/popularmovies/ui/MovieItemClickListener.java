package fyi.jackson.drew.popularmovies.ui;

import android.widget.ImageView;

import fyi.jackson.drew.popularmovies.model.Movie;

public interface MovieItemClickListener {
    void onMovieClicked(int pos, Movie movie, ImageView sharedImageView);
}
