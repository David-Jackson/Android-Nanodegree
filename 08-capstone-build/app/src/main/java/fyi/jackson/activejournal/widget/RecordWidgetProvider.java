package fyi.jackson.activejournal.widget;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.AppDatabase;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.service.RecordingService;
import fyi.jackson.activejournal.service.ServiceConstants;

import static android.content.Context.ACTIVITY_SERVICE;

public class RecordWidgetProvider extends AppWidgetProvider {
    public static final String TAG = RecordWidgetProvider.class.getSimpleName();

    public static final String ACTION_START = "fyi.jackson.activejournal.widget.ACTION_START";
    public static final String ACTION_UPDATE = "fyi.jackson.activejournal.widget.ACTION_UPDATE";
    public static final String EXTRA_ACTIVITY_TYPE = "fyi.jackson.activejournal.widget.EXTRA_ACTIVITY_TYPE";

    private static boolean userHasBeenWarned = false;

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_START)) {
            boolean serviceRunning = isServiceRunning(context, RecordingService.class.getName());
            if (serviceRunning) {
                askToStopRecording(context);
            } else {
                startRecording(context);
            }
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Activity lastActivity = AppDatabase.getDatabase(context).activityDao().getLastActivity();

        for (int i = 0; i < appWidgetIds.length; ++i) {
            updateWidget(context, appWidgetManager, appWidgetIds[i], lastActivity);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Activity lastActivity) {
        int defaultRes = R.drawable.ic_location_on_black_24dp;
        int fabIconRes = (lastActivity == null ? defaultRes : lastActivity.getTypeResId());

        PendingIntent clickPendingIntent = generateClickPendingIntent(context, appWidgetId, lastActivity);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_record_layout);
        rv.setOnClickPendingIntent(R.id.fab, clickPendingIntent);
        rv.setImageViewResource(R.id.fab_icon, fabIconRes);
        rv.setContentDescription(R.id.fab, context.getString(R.string.access_widget_recording_start));

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private PendingIntent generateClickPendingIntent(Context context, int appWidgetId, Activity activity) {
        int activityType = (activity == null ? Activity.TYPE_OTHER : activity.getType());

        Intent intent = new Intent(context, RecordWidgetProvider.class);
        intent.setAction(ACTION_START);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(EXTRA_ACTIVITY_TYPE, activityType);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private boolean isServiceRunning(Context context, String serviceName){
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = i.next();
            if(runningServiceInfo.service.getClassName().equals(serviceName)){
                return true;
            }
        }
        return false;
    }

    private void startRecording(Context context) {
        Intent service = new Intent(context, RecordingService.class);
        service.setAction(ServiceConstants.ACTION.START_FOREGROUND);
        context.startService(service);
    }

    private void askToStopRecording(Context context) {
        if (userHasBeenWarned) {
            Intent service = new Intent(context, RecordingService.class);
            service.setAction(ServiceConstants.ACTION.STOP_FOREGROUND);
            context.startService(service);
        } else {
            Toast.makeText(context, "Recording currently active. Press again to stop recording.", Toast.LENGTH_LONG).show();
        }
        userHasBeenWarned = !userHasBeenWarned;
    }
}
