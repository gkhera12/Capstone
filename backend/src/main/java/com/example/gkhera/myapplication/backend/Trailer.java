package com.example.gkhera.myapplication.backend;

/**
 * Created by gkhera on 8/03/16.
 */
public class Trailer {
    private int id;
    private int key;
    private String name;
    private String site;

    public Trailer(int id, int key, String name, String site){
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
