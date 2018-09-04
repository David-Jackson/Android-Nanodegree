package fyi.jackson.activejournal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.activejournal.R;

public class RecordingFragment extends Fragment {

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
        bottomSheetView.setVisibility(status == STATUS_ACTIVE ? View.VISIBLE : View.GONE);
        fab.setVisibility(status == STATUS_STANDBY ? View.VISIBLE : View.GONE);
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
