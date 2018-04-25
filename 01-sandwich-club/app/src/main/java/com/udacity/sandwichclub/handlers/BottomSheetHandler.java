package com.udacity.sandwichclub.handlers;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udacity.sandwichclub.R;

// This class handles all of the UI for the sandwich detail view as a BottomSheet
public class BottomSheetHandler extends DetailViewHandler {

    View scrim;
    ConstraintLayout bottomSheetViewgroup;
    BottomSheetBehavior bottomSheetBehavior;

    public BottomSheetHandler(Activity activity) {
        super(activity);

        scrim = activity.findViewById(R.id.scrim);
        scrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // when the scrim is clicked, hide the scrim and bottom sheet
                closeBottomSheet();
            }
        });

        bottomSheetViewgroup = activity.findViewById(R.id.bottom_sheet);
        bottomSheetViewgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set onclick so the touch event is not passed to the scrim
            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetViewgroup);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    scrim.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        bottomSheetBehavior.setPeekHeight(
                (int) activity.getResources().getDimension(R.dimen.sandwich_image_height));
        bottomSheetBehavior.setHideable(true);
        closeBottomSheet();
    }

    public void openBottomSheet(int position) {
        if (loadSandwich(position)) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            scrim.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(activity, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isBottomSheetOpen() {
        return bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN;
    }

    public void closeBottomSheet() {
        scrim.setVisibility(View.INVISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
