package fyi.jackson.drew.popularmovies.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Review;
import fyi.jackson.drew.popularmovies.model.Video;
import fyi.jackson.drew.popularmovies.recycler.holder.VideoViewHolder;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private List<Video> videoList;

    public VideoAdapter(List<Video> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_holder_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.bindTo(video);
    }

    @Override
    public int getItemCount() {
        return (videoList == null) ? 0 : videoList.size();
    }

    public List<Video> getReviewList() {
        return videoList;
    }

    public void setReviewList(List<Video> reviewList) {
        this.videoList = reviewList;
    }
}
