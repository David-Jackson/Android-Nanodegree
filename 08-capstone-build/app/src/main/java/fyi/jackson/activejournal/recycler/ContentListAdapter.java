package fyi.jackson.activejournal.recycler;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.recycler.helper.ItemTouchHelperAdapter;
import fyi.jackson.activejournal.recycler.helper.OnStartDragListener;
import fyi.jackson.activejournal.recycler.holder.ContentEditTextViewHolder;
import fyi.jackson.activejournal.recycler.holder.ContentImageViewHolder;
import fyi.jackson.activejournal.recycler.holder.ContentTextViewHolder;
import fyi.jackson.activejournal.recycler.holder.EmptyListViewHolder;
import fyi.jackson.activejournal.recycler.holder.NewContentViewHolder;
import fyi.jackson.activejournal.recycler.holder.SpacerViewHolder;
import fyi.jackson.activejournal.ui.ContentClickListener;
import fyi.jackson.activejournal.ui.NewContentRowClickListener;
import fyi.jackson.activejournal.util.ContentPositionListener;

public class ContentListAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemTouchHelperAdapter, NewContentRowClickListener {

    public static final String TAG = ContentListAdapter.class.getSimpleName();

    public static final int VIEW_TYPE_TEXT_CONTENT = Content.TYPE_TEXT;
    public static final int VIEW_TYPE_IMAGE_CONTENT = Content.TYPE_IMAGE;
    public static final int VIEW_TYPE_NEW_CONTENT = 418;
    public static final int VIEW_TYPE_EDIT_TEXT_CONTENT = 370;

    private ViewGroup parent;
    private Snackbar editSnackbar;

    private Activity currentActivity;
    private List<Content> contents;
    private ContentClickListener clickListener;
    private OnStartDragListener onStartDragListener;
    private ContentPositionListener contentPositionListener;

    private boolean editMode = false;

    public ContentListAdapter(Activity currentActivity,
                              ContentClickListener clickListener,
                              OnStartDragListener onStartDragListener,
                              ContentPositionListener contentPositionListener) {
        this.currentActivity = currentActivity;
        this.clickListener = clickListener;
        this.onStartDragListener = onStartDragListener;
        this.contentPositionListener = contentPositionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case VIEW_TYPE_TEXT_CONTENT:
                v = inflater.inflate(R.layout.view_holder_content_text, parent, false);
                viewHolder = new ContentTextViewHolder(v);
                break;
            case VIEW_TYPE_IMAGE_CONTENT:
                v = inflater.inflate(R.layout.view_holder_content_image, parent, false);
                viewHolder = new ContentImageViewHolder(v);
                break;
            case VIEW_TYPE_EDIT_TEXT_CONTENT:
                v = inflater.inflate(R.layout.view_holder_content_edit_text, parent, false);
                viewHolder = new ContentEditTextViewHolder(v);
                break;
            default: // VIEW_TYPE_NEW_CONTENT
                v = inflater.inflate(R.layout.view_holder_new_content, parent, false);
                viewHolder = new NewContentViewHolder(v);
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
            case VIEW_TYPE_IMAGE_CONTENT:
                ((ContentImageViewHolder) viewHolder).bindTo(contents.get(position), clickListener, onStartDragListener);
                break;
            case VIEW_TYPE_EDIT_TEXT_CONTENT:
                ((ContentEditTextViewHolder) viewHolder).bindTo(contents.get(position), clickListener, onStartDragListener);
                break;
            case VIEW_TYPE_NEW_CONTENT:
                ((NewContentViewHolder) viewHolder).bindTo(this);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (contents == null ? 0 : contents.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == contents.size()) {
            return VIEW_TYPE_NEW_CONTENT;
        }
        int viewType = contents.get(position).getType();

        if (editMode && viewType == VIEW_TYPE_TEXT_CONTENT) {
            viewType = VIEW_TYPE_EDIT_TEXT_CONTENT;
        }

        return viewType;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(contents, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

        updateContentPositions();
        contentPositionListener.onPositionChanged(contents);

        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    private void updateContentPositions() {
        for (int i = 0; i < contents.size(); i++) {
            contents.get(i).setPosition(i);
        }
    }

    @Override
    public void onClick(int interactionType) {
        switch (interactionType) {
            case TYPE_ADD_TEXT:
                addTextContent();
                enterEditMode();
                focusOnContent(contents.size() - 1);
                break;
            case TYPE_ADD_IMAGE:
                break;
            case TYPE_EDIT_CONTENT:
                toggleEditMode();
                break;
        }
    }

    private void addTextContent() {
        Content newContent = new Content();
        newContent.setPosition(contents.size());
        newContent.setType(Content.TYPE_TEXT);
        newContent.setActivityId(currentActivity.getActivityId());
        contents.add(newContent);
    }

    private void focusOnContent(int position) {
        if (editMode) {
            Content content = contents.get(position);
            if (content != null && content.getType() == Content.TYPE_TEXT) {
                // TODO: 9/21/2018 implement functionality that allows keyoard focus on certain veiw holder
            }
        }
    }

    private void toggleEditMode() {
        if (editMode) {
            exitEditMode();
        } else {
            enterEditMode();
        }
    }

    private void enterEditMode() {
        editMode = true;
        notifyDataSetChanged();
        showSnackbar();
    }

    private void exitEditMode() {
        editMode = false;
        notifyDataSetChanged();
        editSnackbar.dismiss();
    }

    private void showSnackbar() {
        editSnackbar = Snackbar.make(parent, "Editing", Snackbar.LENGTH_INDEFINITE)
                .setAction("Done", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exitEditMode();
                    }
                });
        editSnackbar.show();
    }
}
