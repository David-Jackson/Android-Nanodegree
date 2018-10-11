package fyi.jackson.activejournal.data;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.List;

import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.data.entities.Position;
import fyi.jackson.activejournal.data.entities.Stats;

public class AppViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AppViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(application);
    }

    public LiveData<List<Activity>> getActivities() {
        return appDatabase.activityDao().getLiveAllActivities();
    }

    public LiveData<Integer> getPositionCount() {
        return appDatabase.activityDao().getLivePositionCount();
    }

    public LiveData<List<Position>> getPositionsForActivity(long activityId) {
        return appDatabase.activityDao().getLivePositionsForActivity(activityId);
    }

    public LiveData<List<Content>> getContentsForActivity(long activityId) {
        return appDatabase.activityDao().getLiveContentForActivity(activityId);
    }

    public LiveData<List<Stats>> getStatistics() {
        return appDatabase.statsDao().getLiveStats();
    }


    @SuppressLint("StaticFieldLeak")
    public void updateActivities(Activity... activities) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Activity, Void, Void>() {
            @Override
            protected Void doInBackground(Activity... activities) {
                appDatabase.activityDao().updateActivity(activities);
                return null;
            }
        }.execute(activities);
    }

    @SuppressLint("StaticFieldLeak")
    public void updateContents(final List<Content> contents) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.activityDao().updateContent(contents);
                return null;
            }
        }.execute();
    }
    @SuppressLint("StaticFieldLeak")
    public void updateContents(Content... contents) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Content, Void, Void>() {
            @Override
            protected Void doInBackground(Content... contents) {
                appDatabase.activityDao().updateContent(contents);
                return null;
            }
        }.execute(contents);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertActivities(Activity... activities) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Activity, Void, Void>() {
            @Override
            protected Void doInBackground(Activity... activities) {
                appDatabase.activityDao().insertActivity(activities);
                return null;
            }
        }.execute(activities);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertPositions(Position... positions) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Position, Void, Void>() {
            @Override
            protected Void doInBackground(Position... positions) {
                appDatabase.activityDao().insertPosition(positions);
                return null;
            }
        }.execute(positions);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertPositionsList(final List<Position> positions) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.activityDao().insertPositionList(positions);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertContents(Content... contents) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Content, Void, Void>() {
            @Override
            protected Void doInBackground(Content... contents) {
                appDatabase.activityDao().insertContent(contents);
                return null;
            }
        }.execute(contents);
    }

    @SuppressLint("StaticFieldLeak")
    public void removeAllWithId(final long activityId) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.activityDao().removeActivityWithId(activityId);
                appDatabase.activityDao().removePositionsWithId(activityId);
                appDatabase.activityDao().removeContentsWithId(activityId);
                return null;
            }
        }.execute();
    }

    public void removeContents(Content contents) {
        // AsyncTask won't leak memory when used within the ViewModel
        new AsyncTask<Content, Void, Void>() {
            @Override
            protected Void doInBackground(Content... contents) {
                appDatabase.activityDao().removeContent(contents);
                return null;
            }
        }.execute(contents);
    }
}
