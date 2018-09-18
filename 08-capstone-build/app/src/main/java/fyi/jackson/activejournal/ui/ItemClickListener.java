package fyi.jackson.activejournal.ui;

import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.recycler.ActivityViewHolder;

public interface ItemClickListener {
    void onClick(Activity activity, ActivityViewHolder holder);
}
