package fyi.jackson.activejournal.service;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import fyi.jackson.activejournal.ActivityMain;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.AppDatabase;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Position;
import fyi.jackson.activejournal.data.entities.Stats;
import fyi.jackson.activejournal.sensor.LocationSensor;
import fyi.jackson.activejournal.sensor.LocationStatistics;
import fyi.jackson.activejournal.util.Formatter;

public class RecordingService extends Service {

    public static final String TAG = RecordingService.class.getSimpleName();
    public static boolean IS_SERVICE_RUNNING = false;

    private static final String NOTIFICATION_CHANNEL_RECORDING = "ActiveJournalRecordingChannel";
    private static final int NOTIFICATION_ID_RECORDING = 658;

    private static final String EVENT_START_RECORDING = "start_recording";
    private static final String EVENT_STOP_RECORDING = "stop_recording";
    private static final String EVENT_NOTIFICATION_CLICKED = "notification_clicked";

    private LocationSensor locationSensor;

    private AppDatabase database;

    private LocationStatistics locationStatistics;

    private long activityId = -1;
    private long legId = 0;

    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setupNotification();

        database = AppDatabase.getDatabase(this);
        locationStatistics = new LocationStatistics(activityId);

        locationSensor = new LocationSensor(this) {
            @Override
            public void onUpdate(Location location) {
                Position newPosition = locationToPosition(location);
                Stats stats = locationStatistics.accumulate(newPosition);

                updateStats(stats);
                insertPosition(newPosition);

                updateNotification(stats);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ServiceConstants.ACTION.START_FOREGROUND:
                Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
                activityId = System.currentTimeMillis();

                logStart();

                locationStatistics = new LocationStatistics(activityId);
                insertStats(locationStatistics.getStatistics());

                locationSensor.start();

                showNotification();
                IS_SERVICE_RUNNING = true;

                break;

            case ServiceConstants.ACTION.STOP_FOREGROUND:
                Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show();

                logStop();

                stopForeground(true);
                IS_SERVICE_RUNNING = false;

                clearStats();

                Activity activity = new Activity();
                activity.setActivityId(activityId);
                // TODO: 9/18/2018 Implement functionality for getting activity type
                activity.setType(Activity.TYPE_OTHER);
                activity.setName(Formatter.generateDefaultActivityName(activityId, activity.getTypeName()));
                insertActivity(activity);

                stopSelf();

                break;

            case ServiceConstants.ACTION.PAUSE_FOREGROUND:
                Toast.makeText(this, "Pausing Service", Toast.LENGTH_SHORT).show();
                // TODO: 9/17/2018 Implement Pause functionality
                break;

            case ServiceConstants.ACTION.RESUME_FOREGROUND:
                Toast.makeText(this, "Resuming Service", Toast.LENGTH_SHORT).show();
                // TODO: 9/17/2018 Implement Resume functionality
                break;
        }
        return START_STICKY;
    }

    private void insertStats(final Stats stats) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.statsDao().insertStats(stats);
            }
        }).start();
    }

    private void updateStats(final Stats stats) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.statsDao().updateStats(stats);
            }
        }).start();
    }

    private void clearStats() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.statsDao().clearStats();
            }
        }).start();
    }

    private void insertPosition(final Position position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.activityDao().insertPosition(position);
            }
        }).start();
    }

    private void insertActivity(final Activity activity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.activityDao().insertActivity(activity);
            }
        }).start();
    }

    private void setupNotification() {
        Intent notificationIntent = new Intent(this, ActivityMain.class);
        notificationIntent.setAction(ServiceConstants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent stopServiceIntent = new Intent(this, RecordingService.class);
        stopServiceIntent.setAction(ServiceConstants.ACTION.STOP_FOREGROUND);
        PendingIntent pendingStopServiceIntent = PendingIntent.getService(this,
                0, stopServiceIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
        notificationManager = NotificationManagerCompat.from(this);
        notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_RECORDING)
                .setTicker(getString(R.string.app_name))
                .setContentTitle(getString(R.string.recording_notification_content_title))
                .setContentText(getString(R.string.recording_notification_content_text))
                .setSmallIcon(R.drawable.ic_stat_timeline)
                .setLargeIcon(icon)
                .setContentIntent(pendingIntent)
                .setOngoing(true);

            notificationBuilder.addAction(
                    R.drawable.ic_stat_stop,
                    getString(R.string.recording_notification_action_stop),
                    pendingStopServiceIntent);
    }

    private void showNotification() {
        startForeground(NOTIFICATION_ID_RECORDING, notificationBuilder.build());
    }

    private void updateNotification(Stats stats) {
        notificationBuilder.setContentText(
                        stats.getPointCount() + " points, " +
                        Formatter.millisToDurationString(stats.getDuration()) + " ms, " +
                        Formatter.distanceToString(stats.getDistance()) + " m, " +
                        Formatter.speedToString(stats.getAverageSpeed()) + " m/s");
        notificationManager.notify(NOTIFICATION_ID_RECORDING, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // The user-visible name of the channel.
        CharSequence name = getString(R.string.channel_name);

        // The user-visible description of the channel.
        String description = getString(R.string.channel_description);

        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_RECORDING, name, importance);

        // Configure the notification channel.
        mChannel.setDescription(description);

        mNotificationManager.createNotificationChannel(mChannel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationSensor.stop();
        clearStats();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case if services are bound (Bound Services).
        return null;
    }

    private Position locationToPosition(Location location) {
        Position position = new Position();
        position.setActivityId(activityId);
        position.setLegId(legId);
        position.setTs(location.getTime());
        position.setLat(location.getLatitude());
        position.setLng(location.getLongitude());
        position.setAcc(location.getAccuracy());
        position.setAlt(location.getAltitude());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            position.setVacc(location.getVerticalAccuracyMeters());
        }
        return position;
    }

    private void logStart() {
        Bundle analyticsData = new Bundle();
        analyticsData.putLong("id", activityId);
        firebaseAnalytics.logEvent(EVENT_START_RECORDING, analyticsData);
    }

    private void logStop() {
        Bundle analyticsData = new Bundle();
        analyticsData.putLong("id", activityId);
        analyticsData.putLong("endTime", System.currentTimeMillis());
        analyticsData.putInt("count", locationStatistics.getStatistics().getPointCount());
        firebaseAnalytics.logEvent(EVENT_STOP_RECORDING, analyticsData);
    }
}
