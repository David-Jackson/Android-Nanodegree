package fyi.jackson.activejournal.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import fyi.jackson.activejournal.ActivityMain;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;

public class DisplayWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_DETAIL = "fyi.jackson.activejournal.widget.ACTION_DETAIL";
    public static final String ACTION_LIST = "fyi.jackson.activejournal.widget.ACTION_LIST";
    public static final String EXTRA_ACTIVITY_ID = "fyi.jackson.activejournal.widget.EXTRA_ACTIVITY_ID";

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
        switch (intent.getAction()) {
            case ACTION_DETAIL:
                long activityId = intent.getLongExtra(EXTRA_ACTIVITY_ID, -1);
                if (activityId != -1) {
                    Intent startDetailFragmentIntent = new Intent(context, ActivityMain.class);
                    startDetailFragmentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startDetailFragmentIntent.setAction(ActivityMain.ACTION_VIEW);
                    startDetailFragmentIntent.putExtra(ActivityMain.EXTRA_ACTIVITY_ID, activityId);
                    context.startActivity(startDetailFragmentIntent);
                }
                break;
            case ACTION_LIST:
                Intent startListFragmentIntent = new Intent(context, ActivityMain.class);
                startListFragmentIntent.setAction(ActivityMain.ACTION_LIST);
                context.startActivity(startListFragmentIntent);
                break;
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {
            Intent intent = new Intent(context, DisplayWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_display_layout);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.stack_view, intent);
            rv.setEmptyView(R.id.stack_view, R.id.empty_view);

            rv.setImageViewResource(R.id.iv_activity_type, Activity.getRandomTypeResId());
            rv.setOnClickPendingIntent(R.id.empty_view, getOpenAppPendingIntent(context));

            rv.setPendingIntentTemplate(R.id.stack_view, getTemplatePendingIntent(context));

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private PendingIntent getTemplatePendingIntent(Context context) {
        Intent detailIntent = new Intent(context, DisplayWidgetProvider.class);
        detailIntent.setAction(DisplayWidgetProvider.ACTION_DETAIL);
        return PendingIntent.getBroadcast(context, 0, detailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getOpenAppPendingIntent(Context context) {
        Intent openAppIntent = new Intent(context, DisplayWidgetProvider.class);
        openAppIntent.setAction(DisplayWidgetProvider.ACTION_LIST);
        return PendingIntent.getBroadcast(context, 0, openAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
