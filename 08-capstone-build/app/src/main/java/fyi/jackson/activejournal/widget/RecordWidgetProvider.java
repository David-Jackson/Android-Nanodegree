package fyi.jackson.activejournal.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import fyi.jackson.activejournal.R;

public class RecordWidgetProvider extends AppWidgetProvider {
    public static final String TAG = RecordWidgetProvider.class.getSimpleName();

    public static final String START_ACTION = "fyi.jackson.activejournal.widget.START_ACTION";

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
        if (intent.getAction().equals(START_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            Toast.makeText(context, "Recording Widget Clicked: " + appWidgetId, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {
            Intent intent = new Intent(context, RecordWidgetProvider.class);
            intent.setAction(START_ACTION);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_record_layout);
            rv.setOnClickPendingIntent(R.id.fab, clickPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
