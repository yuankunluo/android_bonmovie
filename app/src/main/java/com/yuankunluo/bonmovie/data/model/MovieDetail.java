package com.yuankunluo.bonmovie.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.yuankunluo.bonmovie.utilities.APIResultsWithPosterPathParsable;

/**
 * Created by yuank on 2017-06-27.
 */

@Entity
public class MovieDetail implements APIResultsWithPosterPathParsable {
    @PrimaryKey
    public int id;
    public int imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public String release_date;
    public int runtime;
    public String status;
    public double vote_average;
    public int vote_count;
    public String poster_url;

    @Override
    public void setPosterImageUrl(String url) {
        poster_url = url;
    }

    @Override
    public String getPosterImageUrl() {
        return poster_url;
    }

    @Override
    public String toString() {
        return "MovieDetail{" +
                "id=" + id +
                ", imdb_id=" + imdb_id +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", runtime=" + runtime +
                ", status='" + status + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", poster_url='" + poster_url + '\'' +
                '}';
    }
}
