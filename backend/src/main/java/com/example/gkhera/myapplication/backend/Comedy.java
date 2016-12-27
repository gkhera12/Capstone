package com.example.gkhera.myapplication.backend;


/**
 * Created by gkhera on 25/12/2016.
 */
public class Comedy {
    private String poster_path;
    private String overview;
    private String release_date;
    private Integer id;
    private String artist;

    /**
     * @return The poster_path
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     * @param poster_path The poster_path
     */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    /**
     * @return The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return The release_date
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     * @param release_date The release_date
     */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist The artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }
}
