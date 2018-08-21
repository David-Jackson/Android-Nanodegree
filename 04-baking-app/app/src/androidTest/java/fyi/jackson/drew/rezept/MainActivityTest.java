package fyi.jackson.drew.rezept;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Checks to See if the first two items in the recipe list match nutella pie and brownies,
        // and checks if the detail fragment text matches

        String NUTELLA = "Nutella Pie";
        String BROWNIES = "Brownies";

        waitForABit(5);

        onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_recipe_name)).check(matches(withText(NUTELLA)));

        onView(withContentDescription("Navigate up")).perform(click());

        onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.tv_recipe_name)).check(matches(withText(BROWNIES)));
    }

    // I'll admit, this is a hack. But I tried some different IdlerResources which didn't work
    // and all I wanted to do was to make the darn test wait until the recipe network request came
    // back. I'm sure there's a way to do this, but I had reached the limit of my patience.
    private void waitForABit(int limit) {
        for (int i = 0; i < limit; i++) {
            onView(withContentDescription("Navigate up")).perform(click());
        }
    }
}
