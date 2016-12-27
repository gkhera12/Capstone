package com.example.eightleaves.comedybox.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkhera on 25/12/2016.
 */
public class ComedyResults {
    @SerializedName("results")
    @Expose
    private List<Comedy> results = new ArrayList<>();

    public List<Comedy> getResults() {
        return results;
    }

    public void setResults(List<Comedy> results) {
        this.results = results;
    }
}
