package fyi.jackson.drew.rezept.recycler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PagerSnapHelper extends android.support.v7.widget.PagerSnapHelper {

    public static final String TAG = PagerSnapHelper.class.getSimpleName();

    private ViewPager.OnPageChangeListener onPageChangeListener;

    private PagerSnapHelper() {
        super();
    }

    public PagerSnapHelper(ViewPager.OnPageChangeListener onPageChangeListener) {
        super();
        this.onPageChangeListener = onPageChangeListener;
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        return super.calculateDistanceToFinalSnap(layoutManager, targetView);
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        View snapView = super.findSnapView(layoutManager);
        int viewPosition = snapView == null ? 0 : layoutManager.getPosition(snapView);
        if (onPageChangeListener != null) onPageChangeListener.onPageSelected(viewPosition);
        return snapView;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        return super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
    }

    @Override
    protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        return super.createSnapScroller(layoutManager);
    }
}
