package com.example.eightleaves.comedybox.events;

import com.example.eightleaves.comedybox.data.models.ComedyResults;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by gkhera on 26/12/2016.
 */

public interface ComedyApiMethods {
    @GET("/")
    void getComedyData(Callback<ComedyResults> cb);
}
