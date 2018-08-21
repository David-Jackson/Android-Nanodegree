package fyi.jackson.jokeviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import fyi.jackson.jokes.Joke;

public class JokeViewerActivity extends AppCompatActivity {

    public static final String TAG = JokeViewerActivity.class.getSimpleName();
    public static final String ACTION_DISPLAY_JOKE = "ACTION_DISPLAY_JOKE";
    public static final String EXTRA_JOKE = "EXTRA_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent incomingIntent = getIntent();

        if (incomingIntent == null) {
            scadoodle("No incoming intent set");
            return;
        }

        String action = incomingIntent.getAction();

        if (action == null) {
            scadoodle("No action set");
            return;
        }

        if (!action.equals(ACTION_DISPLAY_JOKE)) {
            scadoodle("Action set, but does not match expected action: " + action);
            return;
        }

        Joke incomingJoke = (Joke) incomingIntent.getSerializableExtra(EXTRA_JOKE);

        if (incomingJoke == null) {
            scadoodle("Joke is null");
            return;
        }

        Log.d(TAG, "onCreate: Joke set to " + incomingJoke.toString());

        setContentView(R.layout.activity_joke_viewer);

        bind(incomingJoke);
    }

    private void scadoodle(String partingWords) {
        Log.e(TAG, "JokeViewerActivity: " + partingWords);
        finish();
    }

    private void bind(Joke joke) {
        ((TextView) findViewById(R.id.tv_setup)).setText(joke.getSetup());
        ((TextView) findViewById(R.id.tv_punchline)).setText(joke.getPunchline());
    }
}
