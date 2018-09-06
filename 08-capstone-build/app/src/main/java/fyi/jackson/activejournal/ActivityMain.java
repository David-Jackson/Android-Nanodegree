package fyi.jackson.activejournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import fyi.jackson.activejournal.data.AppViewModel;
import fyi.jackson.activejournal.fragment.ActivityListFragment;
import fyi.jackson.activejournal.fragment.ImportActivityFragment;
import fyi.jackson.activejournal.fragment.RecordingFragment;

public class ActivityMain extends AppCompatActivity {

    Snackbar snackbar;
    FrameLayout bottomFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomFrame = findViewById(R.id.frame_bottom_layer);

        fragmentTransaction(R.id.frame_top_layer, RecordingFragment.newInstance());
        fragmentTransaction(R.id.frame_bottom_layer, ActivityListFragment.newInstance());

        AppViewModel viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        viewModel.getPositionCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                updateSnackbar(integer);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Snackbar.make(bottomFrame, item.getTitle() + " selected", Snackbar.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.action_import:
                fragmentTransaction(R.id.frame_bottom_layer, ImportActivityFragment.newInstance());
                break;
            case R.id.action_settings:

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void fragmentTransaction(int parentId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(parentId, fragment)
                .commit();
    }

    private void updateSnackbar(Integer value) {
        if (snackbar == null) {
            snackbar = Snackbar.make(
                    bottomFrame,
                    "Beep boop",
                    Snackbar.LENGTH_SHORT);
        }
        snackbar.setText(value + " positions in database.");
        snackbar.show();
    }
}
