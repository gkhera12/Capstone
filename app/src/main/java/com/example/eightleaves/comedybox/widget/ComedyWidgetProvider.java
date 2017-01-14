package com.example.eightleaves.comedybox.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.example.eightleaves.comedybox.DetailActivity;
import com.example.eightleaves.comedybox.DetailFragment;
import com.example.eightleaves.comedybox.MainActivity;
import com.example.eightleaves.comedybox.R;


/**
 * Created by gkhera on 6/01/2017.
 */

public class ComedyWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.comedy_widget);

            // Create an Intent to launch MainActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);


            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
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
