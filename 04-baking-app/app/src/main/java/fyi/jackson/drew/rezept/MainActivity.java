package fyi.jackson.drew.rezept;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fyi.jackson.drew.rezept.fragment.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, RecipeListFragment.newInstance())
                .commit();
    }
}
