package com.example.eightleaves.comedybox.widget;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.eightleaves.comedybox.MainActivity;
import com.example.eightleaves.comedybox.R;


public class ComedyWidgetIntentService  extends IntentService{

    public ComedyWidgetIntentService() {
        super("ComedyWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                ComedyWidgetProvider.class));
        String title = intent.getStringExtra("title");
        boolean isPlaying = intent.getBooleanExtra("isPlaying",false);
        for (int appWidgetId : appWidgetIds) {
            int layoutId = R.layout.comedy_widget;
            RemoteViews views = new RemoteViews(getPackageName(), layoutId);

            // Add the data to the RemoteViews
            // Content Descriptions for RemoteViews were only added in ICS MR1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                setRemoteContentDescription(views, title);
            }
            if(isPlaying){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    views.setImageViewResource(R.id.widget_play,R.drawable.ic_pause);
                }
            }
            views.setTextViewText(R.id.widget_title, title);

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescription(RemoteViews views, String description) {
        views.setContentDescription(R.id.widget_title, description);
    }

}
