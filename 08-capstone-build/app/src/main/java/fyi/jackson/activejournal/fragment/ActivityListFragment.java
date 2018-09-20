package fyi.jackson.activejournal.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.activejournal.ActivityMain;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.AppViewModel;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.recycler.ActivityListAdapter;
import fyi.jackson.activejournal.recycler.holder.ActivityViewHolder;
import fyi.jackson.activejournal.ui.ItemClickListener;
import fyi.jackson.activejournal.util.ActivityTransitionNames;

public class ActivityListFragment extends Fragment implements ItemClickListener {

    public static final String TAG = ActivityListFragment.class.getSimpleName();

    private Unbinder unbinder;

    @BindView(R.id.rv_activity_list) RecyclerView recyclerView;
    ActivityListAdapter adapter;

    public ActivityListFragment() {}

    public static ActivityListFragment newInstance() {
        return new ActivityListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ActivityListAdapter(this);
        
        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        appViewModel.getActivities().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(@Nullable List<Activity> activities) {
                adapter.setActivities(activities);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ActivityMain) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        unbinder = ButterKnife.bind(this, view);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Activity activity, ActivityViewHolder holder) {
        ActivityTransitionNames transitionNames =
                new ActivityTransitionNames(activity.getActivityId());

        DetailFragment detailFragment = DetailFragment.newInstance(activity);

        getFragmentManager().beginTransaction()
                .addSharedElement(holder.map, transitionNames.map)
                .addSharedElement(holder.name, transitionNames.title)
                .addSharedElement(holder.type, transitionNames.type)
                .addSharedElement(holder.itemView, transitionNames.container)
                .replace(R.id.frame_bottom_layer, detailFragment)
                .addToBackStack(TAG)
                .commit();
    }
}
