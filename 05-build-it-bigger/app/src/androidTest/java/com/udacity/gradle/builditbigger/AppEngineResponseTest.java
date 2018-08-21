package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fyi.jackson.jokes.Joke;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class AppEngineResponseTest {

    public static final String TAG = AppEngineResponseTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testVerifyErrorResponse() {
        EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(){
            @Override
            protected void onPostExecute(Joke result) {
                assertNotNull(result);
            }
        };

        asyncTask.execute(getContext());
    }
}