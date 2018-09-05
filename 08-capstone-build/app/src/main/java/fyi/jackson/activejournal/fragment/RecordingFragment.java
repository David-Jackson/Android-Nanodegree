package fyi.jackson.activejournal.fragment;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.animation.EndAnimatorListener;

public class RecordingFragment extends Fragment {

    public static final String TAG = RecordingFragment.class.getSimpleName();

    public static final int STATUS_STANDBY = 0;
    public static final int STATUS_ACTIVE = 1;

    private int status = STATUS_STANDBY;

    private Unbinder unbinder;

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.bottom_sheet) View bottomSheetView;

    private BottomSheetBehavior bottomSheetBehavior;

    public RecordingFragment() {}

    public static RecordingFragment newInstance() {
        return new RecordingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recording, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleStatus();
            }
        });
        bottomSheetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleStatus();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void updateVisibilities() {

        boolean isActive = status == STATUS_ACTIVE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            updateBottomSheetWithReveal();
            updateFabWithReveal();
        } else {
            bottomSheetView.setVisibility(status == STATUS_ACTIVE ? View.VISIBLE : View.INVISIBLE);
            fab.setVisibility(status == STATUS_STANDBY ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateBottomSheetWithReveal() {

        boolean isClosing = status == STATUS_ACTIVE;

        int cx = fab.getLeft() + ((fab.getRight() - fab.getLeft()) / 2);
        int cy = ((fab.getBottom() - fab.getTop()) / 2) + fab.getTop() - bottomSheetView.getTop();

        float finalRadius = (float) Math.hypot(cx - bottomSheetView.getLeft(), bottomSheetView.getBottom() - cy);
        float startRadius = 0f;

        if (isClosing) {
            float t = startRadius;
            startRadius = finalRadius;
            finalRadius = t;
        }

        Log.d(TAG, "updateBottomSheetWithReveal: " + startRadius + " " + finalRadius);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(bottomSheetView, cx, cy, startRadius, finalRadius);

        if (isClosing) {
            anim.addListener(new EndAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    bottomSheetView.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            bottomSheetView.setVisibility(View.VISIBLE);
        }

        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    private void updateFabWithReveal() {

        boolean isClosing = status == STATUS_STANDBY;

        int cx = (fab.getRight() - fab.getLeft()) / 2;
        int cy = (fab.getBottom() - fab.getTop()) / 2;

        float finalRadius = fab.getWidth() / 2f;
        float startRadius = 0f;

        if (isClosing) {
            float t = startRadius;
            startRadius = finalRadius;
            finalRadius = t;
        }

        Animator anim =
                ViewAnimationUtils.createCircularReveal(fab, cx, cy, startRadius, finalRadius);

        if (isClosing) {
            anim.addListener(new EndAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    fab.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            fab.setVisibility(View.VISIBLE);
        }

        anim.start();
    }

    public void toggleBottomSheet(View view) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void statusUpdated() {
        updateVisibilities();
    }

    public void toggleStatus() {
        status += 1;
        status %= 2;
        statusUpdated();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        statusUpdated();
    }
}
