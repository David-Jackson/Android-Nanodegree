package fyi.jackson.activejournal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fyi.jackson.activejournal.fragment.RecordingFragment;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_top, RecordingFragment.newInstance())
                .commit();
    }
}
