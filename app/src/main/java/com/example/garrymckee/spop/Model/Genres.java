package com.example.garrymckee.spop.Model;

import java.util.List;

/**
 * Created by garrymckee on 12/03/17.
 */

public class Genres {

    List<String> genres;

    public List<String> getGenres(){
        return this.genres;
    }

    @Override
    public String toString() {
        String genreList = "";
        for (String genre: genres
             ) {
            genreList += genre + ", ";
        }
        return genreList;
    }
}
