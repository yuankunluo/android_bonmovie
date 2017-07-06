package com.yuankunluo.bonmovie.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.data.model.UserFavoriteMovie;
import com.yuankunluo.bonmovie.data.tools.DateConverter;

import java.util.List;

/**
 * Created by yuank on 2017-06-21.
 */
@Dao
@TypeConverters(DateConverter.class)
public interface UserFavoriteMovieDao {

    @Query("SELECT * FROM UserFavoriteMovie ORDER BY insert_timestamp DESC")
    LiveData<List<UserFavoriteMovie>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(UserFavoriteMovie... movies);


    @Query("DELETE FROM UserFavoriteMovie WHERE id = :movieId")
    void deleteMovieByMovieId(int movieId);

    @Query("SELECT EXISTS(SELECT 1 FROM UserFavoriteMovie WHERE id = :movieId)")
    LiveData<Boolean> hasMoviesByMovieId(int movieId);

}
