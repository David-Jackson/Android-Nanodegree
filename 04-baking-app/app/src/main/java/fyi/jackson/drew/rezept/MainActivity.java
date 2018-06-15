package fyi.jackson.drew.rezept;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import fyi.jackson.drew.rezept.fragment.DetailFragment;
import fyi.jackson.drew.rezept.fragment.ListFragment;

public class MainActivity extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener {

    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, ListFragment.newInstance())
                .commit();

        View detailView = findViewById(R.id.detail);
        isTablet = detailView != null;

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        //Handle when activity is recreated like on orientation Change
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
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
