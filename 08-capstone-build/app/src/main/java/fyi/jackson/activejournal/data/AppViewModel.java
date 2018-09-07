package fyi.jackson.activejournal.data;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Position;

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
}
