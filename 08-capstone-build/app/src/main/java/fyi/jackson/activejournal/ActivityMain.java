package fyi.jackson.activejournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fyi.jackson.activejournal.data.AppViewModel;
import fyi.jackson.activejournal.fragment.ActivityListFragment;
import fyi.jackson.activejournal.fragment.RecordingFragment;

public class ActivityMain extends AppCompatActivity {

    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_bottom, ActivityListFragment.newInstance())
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_top, RecordingFragment.newInstance())
                .commit();

        AppViewModel viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        viewModel.getPositionCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                updateSnackbar(integer);
            }
        });
    }

    private void updateSnackbar(Integer value) {
        if (snackbar == null) {
            snackbar = Snackbar.make(
                    findViewById(R.id.frame_bottom),
                    "Beep boop",
                    Snackbar.LENGTH_SHORT);
        }
        snackbar.setText(value + " positions in database.");
        snackbar.show();
    }
}
