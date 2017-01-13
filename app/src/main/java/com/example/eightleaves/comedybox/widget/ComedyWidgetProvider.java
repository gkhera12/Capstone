package com.example.eightleaves.comedybox.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.eightleaves.comedybox.DetailFragment;


/**
 * Created by gkhera on 6/01/2017.
 */

public class ComedyWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, ComedyWidgetIntentService.class));
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        context.startService(new Intent(context, ComedyWidgetIntentService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(DetailFragment.ACTION_DATA_UPDATED.equals(intent.getAction())){
            Intent widgetIntent = new Intent(context, ComedyWidgetIntentService.class);
            widgetIntent.putExtra("title",intent.getStringExtra("title"));
            widgetIntent.putExtra("isPlaying",intent.getBooleanExtra("isPlaying",false));
            context.startService(widgetIntent);
        }
    }
}
