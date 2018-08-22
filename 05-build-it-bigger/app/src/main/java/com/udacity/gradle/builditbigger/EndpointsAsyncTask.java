package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.backend.myApi.model.Joke;

import java.io.IOException;

import fyi.jackson.jokeviewer.JokeViewerActivity;

class EndpointsAsyncTask extends AsyncTask<Context, Void, Joke> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected Joke doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellJoke().execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Joke result) {
        Intent intent = new Intent(context, JokeViewerActivity.class);
        intent.setAction(JokeViewerActivity.ACTION_DISPLAY_JOKE);

        // convert backend model joke to Android Library readable Joke
        fyi.jackson.jokes.Joke joke = (result == null) ? null :
                new fyi.jackson.jokes.Joke(result.getSetup(), result.getPunchline());
        intent.putExtra(JokeViewerActivity.EXTRA_JOKE, joke);

        context.startActivity(intent);
    }
}