package com.yuankunluo.bonmovie.data.dao;

/**
 * Created by yuank on 2017-06-27.
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yuankunluo.bonmovie.data.model.MovieVideo;

import java.util.List;

@Dao
public interface MovieVideoDao {

    @Query("SELECT * FROM MovieVideo WHERE movie_id = :id")
    LiveData<List<MovieVideo>> getVideosByMovieId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieVideos(MovieVideo... videos);

    @Query("SELECT EXISTS (SELECT 1 FROM MovieVideo WHERE movie_id = :movieId)")
    boolean hasVideosForMovieId(int movieId);

    @Query("DELETE FROM MovieVideo WHERE movie_id =:id")
    void deleteMovieVideosByMovieId(int id);

}
