package fyi.jackson.activejournal.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.activejournal.ActivityMain;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.AppViewModel;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.data.entities.Position;
import fyi.jackson.activejournal.dummy.Data;
import fyi.jackson.activejournal.dummy.Point;
import fyi.jackson.activejournal.recycler.ActivityListAdapter;
import fyi.jackson.activejournal.recycler.holder.ActivityViewHolder;
import fyi.jackson.activejournal.ui.ItemClickListener;
import fyi.jackson.activejournal.util.ActivityTransitionNames;

public class ActivityListFragment extends Fragment implements ItemClickListener {

    private static final String TAG = ActivityListFragment.class.getSimpleName();

    private Unbinder unbinder;

    private AppViewModel appViewModel;

    @BindView(R.id.rv_activity_list) RecyclerView recyclerView;
    private ActivityListAdapter adapter;

    public ActivityListFragment() {}

    public static ActivityListFragment newInstance() {
        return new ActivityListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        adapter = new ActivityListAdapter(this);
        
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_sample_data:
                bulkInsert();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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

    private void bulkInsert() {
        List<Point> points = Data.getSpinnakerSailing();
        List<Point> points1 = Data.getClingmansDome();
        List<Position> positions = new ArrayList<>();
        List<Position> positions1 = new ArrayList<>();

        long activityId = System.currentTimeMillis();
        long activityId1 = activityId + 10;

        for (Point p : points) {
            Position pos = new Position();
            pos.setActivityId(activityId);
            pos.setLegId(1);
            pos.setLat(p.lat);
            pos.setLng(p.lng);
            pos.setTs(p.ts);
            pos.setAcc(p.acc);
            pos.setAlt(p.alt);
            pos.setVacc(p.vAcc);
            positions.add(pos);
        }
        for (Point p : points1) {
            Position pos = new Position();
            pos.setActivityId(activityId1);
            pos.setLegId(1);
            pos.setLat(p.lat);
            pos.setLng(p.lng);
            pos.setTs(p.ts);
            pos.setAcc(p.acc);
            pos.setAlt(p.alt);
            pos.setVacc(p.vAcc);
            positions1.add(pos);
        }
        appViewModel.insertPositionsList(positions);
        appViewModel.insertPositionsList(positions1);


        Activity activity = new Activity();
        activity.setActivityId(activityId);
        activity.setName("Flying the Spinnaker Solo");
        activity.setType(Activity.TYPE_SAILING);
        Activity activity1 = new Activity();
        activity1.setActivityId(activityId1);
        activity1.setName("Hiking to Clingman's Dome");
        activity1.setType(Activity.TYPE_HIKING);

        appViewModel.insertActivities(activity, activity1);


        String longText = "This is a very long string that explains my recent activity in great detail. It was so great. I had so much fun. Here's some Lorem Ipsum: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas rutrum tincidunt quam, eu hendrerit mauris blandit vitae. Donec eu laoreet nulla. Cras facilisis tempor eros vel eleifend. Curabitur eros mi, volutpat eget urna in, efficitur tempus leo. Duis non efficitur felis, id posuere magna. Nullam ultrices nisi ac nisi venenatis laoreet. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In hac habitasse platea dictumst. Morbi accumsan volutpat dui eget consectetur.";
        Content textContent = new Content();
        textContent.setType(Content.TYPE_TEXT);
        textContent.setPosition(0);
        textContent.setActivityId(activityId);
        textContent.setValue(longText);

        String shortText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas rutrum tincidunt quam, eu hendrerit mauris blandit vitae. Donec eu laoreet nulla. Cras facilisis tempor eros vel eleifend.";
        Content textContent2 = new Content();
        textContent2.setType(Content.TYPE_TEXT);
        textContent2.setPosition(2);
        textContent2.setActivityId(activityId);
        textContent2.setValue(shortText);

//        String imageUri = "/storage/self/primary/DCIM/Camera/IMG_20170715_205901021.jpg";
//        Content imageContent = new Content();
//        imageContent.setType(Content.TYPE_IMAGE);
//        imageContent.setPosition(1);
//        imageContent.setActivityId(activityId);
//        imageContent.setValue(imageUri);

        appViewModel.insertContents(textContent, textContent2);
    }
}
