package com.mellobit.garrymckee.spop.Model;

import java.util.List;

/**
 * Created by garrymckee on 12/03/17.
 */

public class Album {

    private String id;
    private String name;
    private String uri;

    private List<Image> images;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public List<Image> getImages() {
        return images;
    }
}
