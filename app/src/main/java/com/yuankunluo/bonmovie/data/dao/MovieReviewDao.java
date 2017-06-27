package com.yuankunluo.bonmovie.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yuankunluo.bonmovie.data.model.MovieReview;

import java.util.List;

/**
 * Created by yuank on 2017-06-27.
 */
@Dao
public interface MovieReviewDao {
    @Query("SELECT * FROM MovieReview WHERE movie_id = :id ORDER BY page ASC, id_in_page ASC")
    LiveData<List<MovieReview>> getMovieReviewsByMovieId(int id);
}
