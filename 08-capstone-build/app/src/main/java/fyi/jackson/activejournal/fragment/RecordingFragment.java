package fyi.jackson.activejournal.fragment;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.animation.EndAnimatorListener;
import fyi.jackson.activejournal.data.AppViewModel;

public class RecordingFragment extends Fragment {

    public static final String TAG = RecordingFragment.class.getSimpleName();

    public static final int STATUS_STANDBY = 0;
    public static final int STATUS_ACTIVE = 1;

    private int status = STATUS_STANDBY;

    private Unbinder unbinder;

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.bottom_sheet) View bottomSheetView;
    @BindView(R.id.iv_pause) ImageView pauseImageButton;
    @BindView(R.id.iv_stop) ImageView stopImageButton;

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

        final AppViewModel viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleStatus();
            }
        });
        pauseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == STATUS_ACTIVE) {
                    pauseRecording();
                } else {
                    resumeRecording();
                }
            }
        });

        stopImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
            }
        });

        bottomSheetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBottomSheet(view);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void pauseRecording() {
        Toast.makeText(getContext(), "Pausing", Toast.LENGTH_SHORT).show();
        setStopButtonVisibility(true);
        pauseImageButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        status = STATUS_STANDBY;
    }

    private void resumeRecording() {
        Toast.makeText(getContext(), "Resuming", Toast.LENGTH_SHORT).show();
        setStopButtonVisibility(false);
        pauseImageButton.setImageResource(R.drawable.ic_pause_black_24dp);
        status = STATUS_ACTIVE;
    }

    private void stopRecording() {
        Toast.makeText(getContext(), "Stopping", Toast.LENGTH_SHORT).show();

        setStopButtonVisibility(false);
        pauseImageButton.setImageResource(R.drawable.ic_pause_black_24dp);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        status = STATUS_STANDBY;
        updateVisibilities();
    }

    private void setStopButtonVisibility(boolean visibile) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition((ViewGroup) bottomSheetView);
        }

        stopImageButton.setVisibility(visibile ? View.VISIBLE : View.INVISIBLE);

        int unset = ConstraintLayout.LayoutParams.UNSET;
        int parentId = ConstraintLayout.LayoutParams.PARENT_ID;

        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) pauseImageButton.getLayoutParams();
        layoutParams.rightToRight = visibile ? unset : parentId;
        layoutParams.rightToLeft = visibile ? R.id.iv_stop : unset;
        pauseImageButton.setLayoutParams(layoutParams);
    }

    @SuppressLint("RestrictedApi")
    private void updateVisibilities() {

        boolean isActive = status == STATUS_ACTIVE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            updateBottomSheetWithReveal();
            updateFabWithReveal();
        } else {
            bottomSheetView.setVisibility(isActive ? View.VISIBLE : View.INVISIBLE);
            fab.setVisibility(isActive ? View.INVISIBLE : View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateBottomSheetWithReveal() {

        boolean isClosing = status == STATUS_STANDBY;

        int cx = fab.getLeft() + ((fab.getRight() - fab.getLeft()) / 2);
        int cy = ((fab.getBottom() - fab.getTop()) / 2) + fab.getTop() - bottomSheetView.getTop();

        float finalRadius = (float) Math.hypot(cx - bottomSheetView.getLeft(), bottomSheetView.getBottom() - cy);
        float startRadius = 0f;

        if (isClosing) {
            float t = startRadius;
            startRadius = finalRadius;
            finalRadius = t;
        }

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

        boolean isClosing = status == STATUS_ACTIVE;

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
        status = (status == STATUS_STANDBY ? STATUS_ACTIVE : STATUS_STANDBY);
        Toast.makeText(getContext(), "Status: " + status, Toast.LENGTH_SHORT).show();
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
