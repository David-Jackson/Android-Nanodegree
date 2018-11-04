package fyi.jackson.activejournal;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.List;

import fyi.jackson.activejournal.data.AppViewModel;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.fragment.ActivityListFragment;
import fyi.jackson.activejournal.fragment.DetailFragment;
import fyi.jackson.activejournal.fragment.RecordingFragment;
import fyi.jackson.activejournal.worker.ThumbnailService;
import pub.devrel.easypermissions.EasyPermissions;

public class ActivityMain extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener {

    public static final String TAG = ActivityMain.class.getSimpleName();

    public static final String ACTION_VIEW = "fyi.jackson.activejournal.ACTION_VIEW_DETAIL";
    public static final String ACTION_LIST = "fyi.jackson.activejournal.ACTION_LIST";
    public static final String EXTRA_ACTIVITY_ID = "fyi.jackson.activejournal.EXTRA_ACTIVITY_ID";

    private static final String USER_PROPERTY_ACTIVITY_COUNT = "activity_count";

    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 9468;

    private FirebaseAnalytics firebaseAnalytics;

    private AppViewModel viewModel;

    Snackbar snackbar;
    private FrameLayout bottomFrame;

    private boolean jumpToDetailFragment = false;
    private long jumpToActivityId = -1;

    // thumbnailRequests is a map of activityIds and timestamps used to keep track of whether an
    // request has been made for a certain activityId.
    private HashMap<Long, Long> thumbnailRequests;
    private long THUMBNAIL_REQUEST_TIMEOUT_MS = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        thumbnailRequests = new HashMap<>();

        bottomFrame = findViewById(R.id.frame_bottom_layer);

        fragmentTransaction(R.id.frame_top_layer, RecordingFragment.newInstance());

        Intent incomingIntent = getIntent();
        switch (incomingIntent.getAction()) {
            case ACTION_VIEW:
                jumpToActivityId = incomingIntent.getLongExtra(EXTRA_ACTIVITY_ID, -1);
                jumpToDetailFragment = true;
            default: // android.intent.action.MAIN
                if (savedInstanceState == null) {
                    fragmentTransaction(R.id.frame_bottom_layer, ActivityListFragment.newInstance());
                }
        }

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        viewModel.getActivities().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(@Nullable List<Activity> activities) {
                if (jumpToDetailFragment && jumpToActivityId != -1) {
                    for (Activity activity : activities) {
                        if (activity.getActivityId() == jumpToActivityId) {
                            fragmentTransaction(R.id.frame_bottom_layer, DetailFragment.newInstance(activity));
                        }
                    }
                    jumpToDetailFragment = false;
                    jumpToActivityId = -1;
                }
                updateThumbnails(activities);
                firebaseAnalytics.setUserProperty(
                        USER_PROPERTY_ACTIVITY_COUNT, String.valueOf(activities.size()));
            }
        });

        checkForPermissions();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
    }

    private void checkForPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        boolean permissionGranted = true;

        for (int i = 0; i < permissions.length; i++) {
            permissionGranted &= ContextCompat.checkSelfPermission(this,
                    permissions[i]) == PackageManager.PERMISSION_GRANTED;
        }


        if (!permissionGranted) {
            ActivityCompat.requestPermissions(this,
                    permissions, PERMISSIONS_REQUEST_FINE_LOCATION);
        }
    }

    private void updateThumbnails(List<Activity> activities) {
        long timeMillis = System.currentTimeMillis();
        for (Activity activity : activities) {

            // Check if thumnbail has been requested before
            Long previousRequestTime = thumbnailRequests.get(activity.getActivityId());
            if (previousRequestTime == null) {
                previousRequestTime = 0l;
            }

            // If we need to request a thumbnail and the request has expired, then request it
            if (activity.getThumbnail() == null &&
                    previousRequestTime <= timeMillis - THUMBNAIL_REQUEST_TIMEOUT_MS) {

                // Save when we sent the reqest so we can ignore updates from other changes
                thumbnailRequests.put(activity.getActivityId(), System.currentTimeMillis());

                Intent loadThumbnailIntent = new Intent(this, ThumbnailService.class);
                loadThumbnailIntent.putExtra(ThumbnailService.EXTRA_ACTIVITY_ID, activity.getActivityId());
                startService(loadThumbnailIntent);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fragmentTransaction(int parentId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(parentId, fragment)
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.frame_bottom_layer);
        firebaseAnalytics.setCurrentScreen(
                this, currentFragment.getClass().getSimpleName(), null);
    }
}
