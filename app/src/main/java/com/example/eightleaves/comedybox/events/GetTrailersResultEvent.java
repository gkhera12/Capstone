package com.example.eightleaves.comedybox.events;

import com.example.eightleaves.comedybox.data.models.TrailersResult;

/**
 * Created by gkhera on 31/12/2016.
 */
public class GetTrailersResultEvent {
    public TrailersResult getTrailersResult() {
        return trailersResult;
    }

    public void setTrailersResult(TrailersResult trailersResult) {
        this.trailersResult = trailersResult;
    }

    private TrailersResult trailersResult;
}
