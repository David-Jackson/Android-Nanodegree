package fyi.jackson.drew.popularmovies.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Video;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    View itemView;
    TextView type, title;

    public VideoViewHolder(View v) {
        super(v);
        itemView = v;
        type = v.findViewById(R.id.tv_type);
        title = v.findViewById(R.id.tv_title);
    }

    public void bindTo(final Video video) {
        type.setText(video.getType());
        title.setText(video.getName());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieUtils.launchVideo(v.getContext(), video.getKey());
            }
        });
    }
}
