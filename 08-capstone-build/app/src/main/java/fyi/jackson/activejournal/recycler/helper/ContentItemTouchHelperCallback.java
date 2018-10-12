package fyi.jackson.activejournal.recycler.helper;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import fyi.jackson.activejournal.recycler.ContentListAdapter;
import fyi.jackson.activejournal.recycler.holder.ContentImageViewHolder;
import fyi.jackson.activejournal.recycler.holder.ContentTextViewHolder;

public class ContentItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "TaskItemTouchHelperCallback";

    private static final float ALPHA_FULL = 1.0f;
    private final ItemTouchHelperAdapter adapter;

    public ContentItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (!(viewHolder instanceof ContentImageViewHolder) &&
                !(viewHolder instanceof ContentTextViewHolder)) return 0;

        // Set movement flags based on the layout manager
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        boolean allowSwap = true;

        switch (target.getItemViewType()) {
            case ContentListAdapter.VIEW_TYPE_NEW_CONTENT:
                allowSwap = false;
                break;
        }

        if (allowSwap) {
            if (source.getAdapterPosition() < target.getAdapterPosition()) {
                for (int i = source.getAdapterPosition(); i < target.getAdapterPosition(); i++) {
                    adapter.onItemMove(i, i + 1);
                }
            } else {
                for (int i = source.getAdapterPosition(); i > target.getAdapterPosition(); i--) {
                    adapter.onItemMove(i, i - 1);
                }
            }
        }
        return allowSwap;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // Notify the adapter of the dismissal
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Fade out the view as it is swiped out of the parent's bounds
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//            if (viewHolder instanceof TaskViewHolder) {
//                // Let the view holder know that this item is being moved or dragged
//                TaskViewHolder taskViewHolder = (TaskViewHolder) viewHolder;
//                taskViewHolder.onItemSelected();
//            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(ALPHA_FULL);

//        if (viewHolder instanceof TaskViewHolder) {
//            // Tell the view holder it's time to restore the idle state
//            TaskViewHolder taskViewHolder = (TaskViewHolder) viewHolder;
//            taskViewHolder.onItemSelected();
//        }
    }
}
