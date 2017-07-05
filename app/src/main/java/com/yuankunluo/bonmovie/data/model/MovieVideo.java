package com.yuankunluo.bonmovie.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.yuankunluo.bonmovie.services.utilities.APIResultsWithMovieIdParsable;

/**
 * Created by yuank on 2017-06-27.
 */
@Entity
public class MovieVideo implements APIResultsWithMovieIdParsable {


    @PrimaryKey
    public String id;
    public String site;
    public String key;
    public String name;
    public String type;
    public int movie_id;

    @Override
    public void setMovieId(int movieId) {
        movie_id = movieId;
    }

    @Override
    public int getMovieId() {
        return movie_id;
    }
}
