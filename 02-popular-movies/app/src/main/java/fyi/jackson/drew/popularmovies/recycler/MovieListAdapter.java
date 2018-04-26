package fyi.jackson.drew.popularmovies.recycler;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.recycler.holder.MovieViewHolder;

public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final String TAG = MovieListAdapter.class.getSimpleName();

    private List<Movie> movieList;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    private static final int TYPE_NORMAL = 1, TYPE_WIDE_RIGHT = 2, TYPE_WIDE_LEFT = 3;

    public MovieListAdapter(List<Movie> movieList, final int spanCount) {
        this.movieList = movieList;
        spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return getItemViewType(position) == TYPE_NORMAL ? 1 : spanCount;
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        boolean left = position % 10 == 0;
        boolean right = position % 5 == 0;
        if (left) {
            return TYPE_WIDE_LEFT;
        } else if (right) {
            return TYPE_WIDE_RIGHT;
        } else {
            return TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int viewId;
        switch (viewType) {
            case TYPE_WIDE_LEFT:
                viewId = R.layout.view_holder_movie_wide_left;
                break;
            case TYPE_WIDE_RIGHT:
                viewId = R.layout.view_holder_movie_wide_right;
                break;
            default:
                viewId = R.layout.view_holder_movie_normal;
        }
        View v = inflater.inflate(viewId, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        // TODO: 4/26/2018 Use Palette to generate dynamic background colors
    }

    @Override
    public int getItemCount() {
        return (movieList == null ? 0 : movieList.size());
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return spanSizeLookup;
    }

    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        this.spanSizeLookup = spanSizeLookup;
    }
}
