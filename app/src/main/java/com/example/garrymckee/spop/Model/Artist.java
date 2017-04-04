package com.example.garrymckee.spop.Model;

/**
 * Created by garrymckee on 12/03/17.
 */

public class Artist {

    private String id;
    private String name;
    private String uri;
    String[] genres;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String[] getGenres() {
        return genres;
    }
}
