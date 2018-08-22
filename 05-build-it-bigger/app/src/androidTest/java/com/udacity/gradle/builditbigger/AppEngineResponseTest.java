package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.backend.myApi.model.Joke;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AppEngineResponseTest {

    public static final String TAG = AppEngineResponseTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testVerifyErrorResponse() throws Throwable {

        final CountDownLatch latch = new CountDownLatch(1);

        EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(){
            @Override
            protected void onPostExecute(Joke result) {
                assertNotNull(result);
                latch.countDown();
            }
        };

        asyncTask.execute(getContext());

        assertTrue(latch.await(30, TimeUnit.SECONDS));
    }
}