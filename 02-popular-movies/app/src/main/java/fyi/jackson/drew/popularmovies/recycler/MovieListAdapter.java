package fyi.jackson.drew.popularmovies.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.recycler.holder.MovieViewHolder;

public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movie> movieList;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    private static final int TYPE_NORMAL = 1, TYPE_WIDE = 2;

    public MovieListAdapter(List<Movie> movieList) {
        this.movieList = movieList;
        spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 5 == 0 ? 2 : 1;
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        return position % 5 == 0 ? TYPE_WIDE : TYPE_NORMAL;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(
                viewType == TYPE_NORMAL ? R.layout.view_holder_movie : R.layout.view_holder_movie_wide,
                parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

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
