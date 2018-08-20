package fyi.jackson.drew.rezept;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fyi.jackson.drew.rezept.fragment.ListFragment;

public class MainActivity extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener {

    private boolean isTablet;
    private AnimatedVectorDrawable iconAvd;
    private AnimatedVectorDrawable backAvd;
    private boolean wasInDetailFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, ListFragment.newInstance())
                    .commit();
        }

        View detailView = findViewById(R.id.detail);
        isTablet = detailView != null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iconAvd = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_dining_to_back);
            backAvd = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_back_to_dining);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        //Handle when activity is recreated like on orientation Change
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    @SuppressLint("NewApi")
    private void shouldDisplayHomeUp(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Enable Up button only  if there are entries in the back stack
        boolean canBack = getSupportFragmentManager().getBackStackEntryCount()>0;
        boolean canAvd = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        if (canBack) {
            if (canAvd && !wasInDetailFragment) {
                getSupportActionBar().setHomeAsUpIndicator(iconAvd);
                iconAvd.start();
            } else {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            }
        } else {
            if (canAvd && wasInDetailFragment) {
                getSupportActionBar().setHomeAsUpIndicator(backAvd);
                backAvd.start();
            } else {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_local_dining_white_24dp);
            }
        }
        wasInDetailFragment = canBack;
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    public boolean isTablet() {
        return isTablet;
    }
}
