package com.example.eightleaves.comedybox.events;

import com.example.eightleaves.comedybox.data.models.ComedyResults;

public class GetComedyDataResultEvent {
    public ComedyResults getComedyResults() {
        return comedyResults;
    }

    public void setComedyResults(ComedyResults comedyResults) {
        this.comedyResults = comedyResults;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    private ComedyResults comedyResults;

    private String sortBy;
}
