package fyi.jackson.activejournal;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import java.util.List;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import fyi.jackson.activejournal.data.AppViewModel;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.fragment.ActivityListFragment;
import fyi.jackson.activejournal.fragment.DetailFragment;
import fyi.jackson.activejournal.fragment.RecordingFragment;
import fyi.jackson.activejournal.worker.ThumbnailWorker;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        bottomFrame = findViewById(R.id.frame_bottom_layer);

        fragmentTransaction(R.id.frame_top_layer, RecordingFragment.newInstance());

        Intent incomingIntent = getIntent();
        switch (incomingIntent.getAction()) {
            case ACTION_VIEW:
                jumpToActivityId = incomingIntent.getLongExtra(EXTRA_ACTIVITY_ID, -1);
                jumpToDetailFragment = true;
            default: // android.intent.action.MAIN
                fragmentTransaction(R.id.frame_bottom_layer, ActivityListFragment.newInstance());
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
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE
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
        for (Activity activity : activities) {
            if (activity.getThumbnail() == null) {
                androidx.work.Data activityData = new androidx.work.Data.Builder()
                        .putLong(ThumbnailWorker.KEY_ACTIVITY_ID, activity.getActivityId())
                        .build();

                OneTimeWorkRequest thumbnailRequest =
                        new OneTimeWorkRequest.Builder(ThumbnailWorker.class)
                                .setInputData(activityData)
                                .build();

                WorkManager.getInstance().enqueue(thumbnailRequest);

                return;
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
