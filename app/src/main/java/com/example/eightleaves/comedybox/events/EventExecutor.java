package com.example.eightleaves.comedybox.events;

import android.content.Context;

import com.example.eightleaves.comedybox.R;
import com.example.eightleaves.comedybox.data.models.ComedyResults;
import com.example.eightleaves.comedybox.data.models.TrailersResult;
import com.example.eightleaves.comedybox.otto.ComedyBus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by gkhera on 18/03/2016.
 */
public class EventExecutor {
    private ComedyApiMethods methods;
    private String COMEDY_BASE_URL;
    public EventExecutor(Context context){
        ComedyBus.getInstance().register(this);
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        COMEDY_BASE_URL = context.getString(R.string.endpoint);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(COMEDY_BASE_URL)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        //Implementation using Retrofit
        methods = restAdapter.create(ComedyApiMethods.class);
    }


    public void getComedyDataEvent(){
        final GetComedyDataResultEvent resultEvent = new GetComedyDataResultEvent();
        resultEvent.setSortBy("popular");
        methods.getComedyData(new Callback<ComedyResults>() {
            @Override
            public void success(ComedyResults comedyResults, Response response) {
                resultEvent.setComedyResults(comedyResults);
                ComedyBus.getInstance().post(resultEvent);
            }

            @Override
            public void failure(RetrofitError error) {
                ComedyResults results = new ComedyResults();
                resultEvent.setComedyResults(results);
                ComedyBus.getInstance().post(resultEvent);
            }
        });
    }

    @Subscribe
    public void getTrailers(GetTrailersEvent event) {
        final GetTrailersResultEvent resultEvent = new GetTrailersResultEvent();
        methods.getTrailersData(Integer.valueOf(event.getComedyId()), new Callback<TrailersResult>(){
            @Override
            public void success(TrailersResult reviewResults, Response response) {
                resultEvent.setTrailersResult(reviewResults);
                ComedyBus.getInstance().post(resultEvent);
            }

            @Override
            public void failure(RetrofitError error) {
                TrailersResult results = new TrailersResult();
                resultEvent.setTrailersResult(results);
                ComedyBus.getInstance().post(resultEvent);
            }
        });
    }

    public void onDestroy(){
        ComedyBus.getInstance().unregister(this);
    }
}
