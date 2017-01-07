package com.example.eightleaves.comedybox;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback{
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTracker = ((ComedyApplication)getApplication()).getDefaultTracker();
        sendTrackingEvent();
    }

    @Override
    public void onItemSelected(Uri contentUri) {
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(contentUri);
            startActivity(intent);
    }

    private void sendTrackingEvent(){
        mTracker.setScreenName(MainActivity.class.getName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
