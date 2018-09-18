package fyi.jackson.activejournal.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.util.ActivityTransitionNames;

public class DetailFragment extends Fragment {

    public static final String TAG = DetailFragment.class.getSimpleName();

    private static final String EXTRA_ACTIVITY = "EXTRA_ACTIVITY";

    private Unbinder unbinder;


    @BindView(R.id.iv_activity_map) ImageView mainImageView;
    @BindView(R.id.tv_activity_title) TextView titleTextView;
    @BindView(R.id.iv_activity_type) ImageView typeImageView;
    @BindView(R.id.layout_container) ConstraintLayout containerLayout;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(Activity activity) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ACTIVITY, activity);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(
                    TransitionInflater.from(getContext())
                            .inflateTransition(android.R.transition.move));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        Activity activity = getArguments().getParcelable(EXTRA_ACTIVITY);
        bindTo(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void bindTo(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityTransitionNames transitionNames =
                    new ActivityTransitionNames(activity.getActivityId());
            mainImageView.setTransitionName(transitionNames.map);
            titleTextView.setTransitionName(transitionNames.title);
            typeImageView.setTransitionName(transitionNames.type);
            containerLayout.setTransitionName(transitionNames.container);
        }

        titleTextView.setText(activity.getName());
        typeImageView.setImageResource(activity.getTypeResId());

        File f = new File(
                getContext().getDir("thumbnails", Context.MODE_PRIVATE),
                activity.getThumbnail());

        Picasso.get()
                .load(f)
                .into(mainImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        startPostponedEnterTransition();
                    }
                });
    }
}
