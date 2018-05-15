package fyi.jackson.drew.popularmovies.recycler.holder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.model.Review;
import fyi.jackson.drew.popularmovies.utils.MovieUtils;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public TextView content, author;

    public ReviewViewHolder(View v) {
        super(v);
        itemView = v;
        content = v.findViewById(R.id.tv_content);
        author = v.findViewById(R.id.tv_author);
    }

    public void bindTo(final Review review) {
        String contentText = MovieUtils.stripMarkdown(review.getContent());
        content.setText(contentText);

        String authorText = itemView.getResources()
                .getString(R.string.template_review_author, review.getAuthor());
        author.setText(authorText);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
                itemView.getContext().startActivity(browserIntent);
            }
        });
    }
}
