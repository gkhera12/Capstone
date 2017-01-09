package com.example.eightleaves.comedybox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class DetailActivity extends AppCompatActivity {
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comedy_detail);
        if(savedInstanceState == null)
        {
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_URI, getIntent().getData());
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.comedy_detail_container, fragment)
                    .commit();
        }
        mTracker = ((ComedyApplication)getApplication()).getDefaultTracker();
        sendTrackingEvent();
    }

    private void sendTrackingEvent(){
        mTracker.setScreenName(DetailActivity.class.getName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
