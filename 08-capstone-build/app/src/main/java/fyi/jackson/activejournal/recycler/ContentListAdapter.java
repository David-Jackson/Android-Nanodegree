package fyi.jackson.activejournal.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.recycler.helper.ItemTouchHelperAdapter;
import fyi.jackson.activejournal.recycler.helper.OnStartDragListener;
import fyi.jackson.activejournal.recycler.holder.ContentImageViewHolder;
import fyi.jackson.activejournal.recycler.holder.ContentTextViewHolder;
import fyi.jackson.activejournal.ui.ContentClickListener;

public class ContentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private static final int VIEW_TYPE_TEXT_CONTENT = Content.TYPE_TEXT;
    private static final int VIEW_TYPE_IMAGE_CONTENT = Content.TYPE_IMAGE;

    private List<Content> contents;
    private ContentClickListener clickListener;
    private OnStartDragListener onStartDragListener;

    public ContentListAdapter(ContentClickListener clickListener, OnStartDragListener onStartDragListener) {
        this.clickListener = clickListener;
        this.onStartDragListener = onStartDragListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case VIEW_TYPE_TEXT_CONTENT:
                v = inflater.inflate(R.layout.view_holder_content_text, parent, false);
                viewHolder = new ContentTextViewHolder(v);
                break;
            default: // VIEW_TYPE_IMAGE_CONTENT
                v = inflater.inflate(R.layout.view_holder_content_image, parent, false);
                viewHolder = new ContentImageViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_TEXT_CONTENT:
                ((ContentTextViewHolder) viewHolder).bindTo(contents.get(position), clickListener, onStartDragListener);
                break;
            default: // VIEW_TYPE_IMAGE_CONTENT
                ((ContentImageViewHolder) viewHolder).bindTo(contents.get(position), clickListener, onStartDragListener);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (contents == null ? 0 : contents.size());
    }

    @Override
    public int getItemViewType(int position) {
        return contents.get(position).getType();
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(contents, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
