package com.example.eightleaves.comedybox.events;

/**
 * Created by gkhera on 2/01/2017.
 */
public class PlayTrailerEvent {
    String title;
    String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
