package fyi.jackson.drew.popularmovies.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fyi.jackson.drew.popularmovies.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public ImageView poster, favorite;

    public MovieViewHolder(View v) {
        super(v);
        itemView = v;
        poster = v.findViewById(R.id.iv_poster);
        favorite = v.findViewById(R.id.iv_favorite);
    }
}
