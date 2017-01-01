package com.example.eightleaves.comedybox.events;

import com.example.eightleaves.comedybox.data.models.ComedyResults;
import com.example.eightleaves.comedybox.data.models.TrailersResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * Created by gkhera on 26/12/2016.
 */

public interface ComedyApiMethods {
    @GET("/comedyresults")
    void getComedyData(Callback<ComedyResults> cb);
    @GET("/trailersresult/{id}")
    void getTrailersData(@Path("id") int comedyId, Callback<TrailersResult> cb);
}
