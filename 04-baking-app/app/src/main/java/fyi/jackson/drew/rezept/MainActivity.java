package fyi.jackson.drew.rezept;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fyi.jackson.drew.rezept.fragment.DetailFragment;
import fyi.jackson.drew.rezept.fragment.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, ListFragment.newInstance())
                .commit();

        View detailView = findViewById(R.id.detail);
        boolean isTablet = detailView != null;

        if (isTablet) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.detail, DetailFragment.newInstance())
//                    .commit();
        }
    }
}
