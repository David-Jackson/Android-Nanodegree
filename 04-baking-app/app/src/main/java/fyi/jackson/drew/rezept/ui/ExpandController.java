package fyi.jackson.drew.rezept.ui;

import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fyi.jackson.drew.rezept.R;

public class ExpandController {

    boolean visible = false;
    View clickView;
    ImageView imageView;
    View[] toggleViews;

    public ExpandController(View clickView, ImageView imageView, View... toggleViews) {
        this.clickView = clickView;
        this.imageView = imageView;
        this.toggleViews = toggleViews;

        this.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                visible = !visible;
                int visibility = visible ? View.VISIBLE : View.GONE;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    AutoTransition transition = new AutoTransition();
                    transition.setOrdering(AutoTransition.ORDERING_TOGETHER);
                    TransitionManager.beginDelayedTransition(
                            (ViewGroup) clickedView.getParent(), transition);
                }

                for (View v : ExpandController.this.toggleViews) {
                    v.setVisibility(visibility);
                }

                setImage();
            }
        });
    }

    private void setImage() {
        imageView.setImageResource(visible ?
                R.drawable.ic_expand_less_black_24dp : R.drawable.ic_expand_more_black_24dp);
    }
}
