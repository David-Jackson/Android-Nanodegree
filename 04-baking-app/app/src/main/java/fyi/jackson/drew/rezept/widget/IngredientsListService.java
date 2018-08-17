package fyi.jackson.drew.rezept.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import fyi.jackson.drew.rezept.R;

public class IngredientsListService extends RemoteViewsService {
    
    public static final String TAG = IngredientsListService.class.getSimpleName();
    public static final String EXTRA_INGREDIENTS_LIST = "EXTRA_INGREDIENTS_LIST";
    
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: ");
        String[] ingredients = intent.getStringArrayExtra(EXTRA_INGREDIENTS_LIST);
        return new IngredientsListFactory(getApplicationContext(), ingredients);
    }
}

class IngredientsListFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String TAG = IngredientsListService.class.getSimpleName();
    private String[] ingredients;
    private Context context;

    public IngredientsListFactory(Context context, String[] ingredients) {
        Log.d(TAG, "IngredientsListFactory: ");
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: ");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: " + (ingredients == null ? 0 : ingredients.length));
        return (ingredients == null ? 0 : ingredients.length);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt: " + position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item_ingredient);
        views.setTextViewText(R.id.tv_ingredient, ingredients[position]);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        Log.d(TAG, "getLoadingView: ");
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d(TAG, "getViewTypeCount: ");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId: ");
        return position;
    }

    @Override
    public boolean hasStableIds() {
        Log.d(TAG, "hasStableIds: ");
        return true;
    }


}