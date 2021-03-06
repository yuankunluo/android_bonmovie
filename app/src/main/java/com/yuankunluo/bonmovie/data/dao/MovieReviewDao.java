package com.yuankunluo.bonmovie.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yuankunluo.bonmovie.data.model.MovieReview;

import java.util.List;

/**
 * Created by yuank on 2017-06-27.
 */
@Dao
public interface MovieReviewDao {
    @Query("SELECT * FROM MovieReview WHERE movie_id = :id AND page = :page ORDER BY page ASC, id_in_page ASC")
    LiveData<List<MovieReview>> getMovieReviewsByMovieIdAndPage(int id, int page);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReviews(MovieReview... reviews);

    @Query("DELETE FROM MovieReview WHERE movie_id = :id")
    void deleteReviewsByMovieId(int id);

    @Query("SELECT EXISTS(SELECT 1 FROM MovieReview WHERE movie_id = :movieId AND page = :page)")
    boolean hasReiewsAtPageByMovieId(int movieId, int page);
}
