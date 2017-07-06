package com.yuankunluo.bonmovie.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.yuankunluo.bonmovie.services.utilities.APIResultsWithMovieIdParsable;
import com.yuankunluo.bonmovie.services.utilities.APIResultsWithPagesParsable;
import com.yuankunluo.bonmovie.services.utilities.APIResultsWithPosterPathParsable;

/**
 * Created by yuank on 2017-06-21.
 */

@Entity
public class UserFavoriteMovie implements APIResultsWithMovieIdParsable, APIResultsWithPosterPathParsable {

    @PrimaryKey
    private int id;
    private String poster_url;
    private long insert_timestamp;


    public UserFavoriteMovie(int id, String poster_url) {
        this.id = id;
        this.poster_url = poster_url;
    }

    @Override
    public void setMovieId(int movieId) {
        id = movieId;
    }

    @Override
    public int getMovieId() {
        return id;
    }

    @Override
    public void setPosterImageUrl(String url) {
        this.poster_url = url;
    }

    @Override
    public String getPosterImageUrl() {
        return this.poster_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public long getInsert_timestamp() {
        return insert_timestamp;
    }

    public void setInsert_timestamp(long insert_timestamp) {
        this.insert_timestamp = insert_timestamp;
    }

    @Override
    public String toString() {
        return "UserFavoriteMovie{" +
                "id=" + id +
                ", poster_url='" + poster_url + '\'' +
                ", insert_timestamp=" + insert_timestamp +
                '}';
    }
}
