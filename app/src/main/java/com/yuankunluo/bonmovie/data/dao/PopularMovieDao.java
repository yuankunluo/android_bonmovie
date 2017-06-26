package com.yuankunluo.bonmovie.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.yuankunluo.bonmovie.data.model.DateConverter;
import com.yuankunluo.bonmovie.data.model.PopularMovie;

import java.util.Date;
import java.util.List;

/**
 * Created by yuank on 2017-06-21.
 */
@Dao
@TypeConverters(DateConverter.class)
public interface PopularMovieDao {

    @Query("SELECT * FROM PopularMovie ORDER BY page ASC, id_in_page ASC")
    LiveData<List<PopularMovie>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPopularMovies(PopularMovie... movies);


    @Query("DELETE FROM PopularMovie WHERE id > 0")
    void deleteAllPopularMovies();

    @Query("SELECT EXISTS(SELECT 1 FROM PopularMovie WHERE page = :page)")
    boolean hasMoviesAtPage(int page);

}
