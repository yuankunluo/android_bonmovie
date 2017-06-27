package com.yuankunluo.bonmovie.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by yuank on 2017-06-27.
 */

@Entity
public class MovieDetail {
    @PrimaryKey
    private int id;
    private String homepage;
    private int imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
}
