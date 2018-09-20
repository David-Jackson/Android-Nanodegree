package fyi.jackson.activejournal.ui;

import android.support.v7.widget.RecyclerView;

import fyi.jackson.activejournal.data.entities.Content;

public interface ContentClickListener {
    void onClick(Content content, RecyclerView.ViewHolder holder);
}
