package fyi.jackson.drew.popularmovies.utils;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ScrollControlAppBarLayoutBehavior extends AppBarLayout.Behavior {

    private boolean shouldScroll = false;

    public ScrollControlAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        return shouldScroll;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        if(shouldScroll){
            return super.onTouchEvent(parent, child, ev);
        }else{
            return false;
        }
    }

    public void setScrollBehavior(boolean shouldScroll){
        this.shouldScroll = shouldScroll;
    }

    public boolean isShouldScroll(){
        return shouldScroll;
    }
}
