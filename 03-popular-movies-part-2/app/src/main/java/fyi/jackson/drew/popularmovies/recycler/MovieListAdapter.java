package fyi.jackson.drew.popularmovies.recycler;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.recycler.holder.MovieViewHolder;
import fyi.jackson.drew.popularmovies.ui.MovieItemClickListener;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;

public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final String TAG = MovieListAdapter.class.getSimpleName();

    private List<Movie> movieList;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;
    private MovieItemClickListener movieItemClickListener;

    public MovieListAdapter(List<Movie> movieList, MovieItemClickListener movieItemClickListener) {
        this.movieList = movieList;
        this.movieItemClickListener = movieItemClickListener;
        spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 8 < 2 ? 3 : 2);
            }
        };
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_holder_movie_normal, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        Movie movie = movieList.get(position);
        holder.bindTo(movie, movieItemClickListener);
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
