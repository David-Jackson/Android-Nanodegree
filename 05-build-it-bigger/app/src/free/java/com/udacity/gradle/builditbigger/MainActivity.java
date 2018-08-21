package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import fyi.jackson.jokes.Joke;
import fyi.jackson.jokes.Joker;


public class MainActivity extends AppCompatActivity {

    private Joker joker;
    private ProgressBar progressBar;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joker = new Joker();

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        MobileAds.initialize(this,
                getString(R.string.ad_app_id));
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_add_unit_id));
        loadNewAd();

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                loadNewAd();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Toast.makeText(MainActivity.this, "Ad Failed to Load: " + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(this, "Interstitial Ad not yet loaded", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.VISIBLE);
        EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(){
            @Override
            protected void onPostExecute(Joke result) {
                progressBar.setVisibility(View.GONE);
                super.onPostExecute(result);
            }
        };
        asyncTask.execute(this);
    }

    private void loadNewAd() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
