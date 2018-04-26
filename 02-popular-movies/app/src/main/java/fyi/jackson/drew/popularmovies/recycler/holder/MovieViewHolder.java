package fyi.jackson.drew.popularmovies.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fyi.jackson.drew.popularmovies.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public ImageView poster;
    public TextView title;

    public MovieViewHolder(View v) {
        super(v);
        itemView = v;
        poster = v.findViewById(R.id.iv_poster);
        title = v.findViewById(R.id.tv_title);
    }
}
