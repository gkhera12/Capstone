package com.example.gkhera.myapplication.backend;

/**
 * Created by gkhera on 8/03/16.
 */

import java.util.ArrayList;


public class TrailersResult {

    private Integer id;
    private ArrayList<Trailer> results = new ArrayList<>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The results
     */
    public ArrayList<Trailer> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }

}