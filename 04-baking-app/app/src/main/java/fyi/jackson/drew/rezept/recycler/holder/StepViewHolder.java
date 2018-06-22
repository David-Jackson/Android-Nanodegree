package fyi.jackson.drew.rezept.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Step;

public class StepViewHolder extends RecyclerView.ViewHolder {

    public PlayerView playerView;
    TextView shortDescription;
    TextView description;

    public StepViewHolder(View v) {
        super(v);
        playerView = v.findViewById(R.id.media);
        shortDescription = v.findViewById(R.id.tv_short_desc);
        description = v.findViewById(R.id.tv_desc);
    }

    public void bindTo(Step step) {
        shortDescription.setText(step.getShortDescription());
        description.setText(Html.fromHtml(step.getDescription()));
        playerView.setVisibility(step.getVideoUrl().equals("") ? View.GONE : View.VISIBLE);
    }
}
