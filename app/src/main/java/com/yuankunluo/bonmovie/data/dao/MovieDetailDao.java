package com.yuankunluo.bonmovie.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yuankunluo.bonmovie.data.model.MovieDetail;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-27.
 */
@Dao
public interface MovieDetailDao {

    @Query("SELECT * FROM MovieDetail WHERE id =:id")
    public LiveData<MovieDetail> getMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void safeMovieDetail(MovieDetail movieDetail);

    @Query("DELETE FROM MovieDetail WHERE id=:id")
    void deleteMovieById(int id);
}
