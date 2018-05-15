package fyi.jackson.drew.popularmovies.recycler.holder;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.ui.MovieItemClickListener;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public ImageView poster, favorite;

    public MovieViewHolder(View v) {
        super(v);
        itemView = v;
        poster = v.findViewById(R.id.iv_poster);
        favorite = v.findViewById(R.id.iv_favorite);
    }

    public void bindTo(final Movie movie, final MovieItemClickListener clickListener) {
        String posterUrl = MovieUtils.buildPosterUrl(movie.getPosterPath(), MovieUtils.API_POSTER_SIZE_W342);

        Picasso.get()
                .load(posterUrl)
                .placeholder(R.drawable.ic_poster_placeholder)
                .error(R.mipmap.test_poster)
                .into(poster);

        ViewCompat.setTransitionName(poster, movie.getTitle());

        favorite.setImageResource(
                movie.isFavorite() ?
                        R.drawable.ic_favorite_black_24dp :
                        R.drawable.ic_favorite_border_black_24dp);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onMovieClicked(getAdapterPosition(), movie, poster);
            }
        });
    }
}
