package fyi.jackson.activejournal.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.activejournal.ActivityMain;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.AppViewModel;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.recycler.ContentListAdapter;
import fyi.jackson.activejournal.recycler.helper.ContentItemTouchHelperCallback;
import fyi.jackson.activejournal.recycler.helper.OnStartDragListener;
import fyi.jackson.activejournal.ui.ContentClickListener;
import fyi.jackson.activejournal.util.ActivityTransitionNames;
import fyi.jackson.activejournal.ui.ContentChangeListener;

public class DetailFragment
        extends Fragment
        implements ContentClickListener {

    public static final String TAG = DetailFragment.class.getSimpleName();

    private static final String EXTRA_ACTIVITY = "EXTRA_ACTIVITY";

    private Unbinder unbinder;

    @BindView(R.id.iv_activity_map) ImageView mainImageView;
    @BindView(R.id.tv_activity_title) TextView titleTextView;
    @BindView(R.id.iv_activity_type) ImageView typeImageView;
    @BindView(R.id.layout_container) ConstraintLayout containerLayout;
    @BindView(R.id.rv_content_list) RecyclerView recyclerView;
    ContentListAdapter adapter;
    ItemTouchHelper itemTouchHelper;

    Activity currentActivity;

    boolean expectingChange = false;

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

        currentActivity = getArguments().getParcelable(EXTRA_ACTIVITY);


        final AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        ContentChangeListener contentChangeListener = new ContentChangeListener() {
            @Override
            public void onChange(List<Content> updatedContents) {
                expectingChange = true;
                appViewModel.updateContents(updatedContents);
            }

            @Override
            public void onChange(Content updatedContent) {
                expectingChange = true;
                appViewModel.updateContents(updatedContent);
            }

            @Override
            public void onInsert(Content newContent) {
                expectingChange = false;
                appViewModel.insertContents(newContent);
            }
        };

        OnStartDragListener onStartDragListener = new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            }
        };

        adapter = new ContentListAdapter(
                currentActivity,
                onStartDragListener,
                contentChangeListener);

        appViewModel.getContentsForActivity(currentActivity.getActivityId())
                .observe(this, new Observer<List<Content>>() {
                    @Override
                    public void onChanged(@Nullable List<Content> contents) {
                        Log.d(TAG, "onChanged: TXTDEBUG Content changed:");
                        for (Content c : contents) {
                            Log.d(TAG, "onChanged: TXTDEBUG : " + c.getUid() + " : " + c.getValue());
                        }
                        if (expectingChange) {
                            expectingChange = false;
                            return;
                        }
                        adapter.setContents(contents);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ActivityMain) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        unbinder = ButterKnife.bind(this, view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.Callback callback = new ContentItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        bindTo(currentActivity);
    }

    @Override
    public void onDestroyView() {
        adapter.onDestroy();
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

        if (activity.getThumbnail() == null) {
            startPostponedEnterTransition();
        } else {
            File f = new File(activity.getThumbnail());

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

    @Override
    public void onClick(Content content, RecyclerView.ViewHolder holder) {

    }
}
